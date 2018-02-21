package com.seungjun.touchball.game;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.seungjun.touchball.R;
import com.seungjun.touchball.dialog.AlertDialogActivity;
import com.seungjun.touchball.dialog.AlertDialogPauseActivity;
import com.seungjun.touchball.util.SettingMode;
import com.seungjun.touchball.value.FinalValue;
import com.seungjun.touchball.value.ShareData;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 본격적인 게임을 표시해줄 클래스
 * Created by SeungJun on 2016-05-17.
 */
public class MainGame extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainGame";

    private static final int NEW_START = 1;
    private static final int PAUSE = 2;
    private static final int PAUSE_START = 3;

    private int addTime = 0; // 공이 추가되는 주기
    private static int bonusGage = 0; // 보너스 게이지
    private int bonusTouchCount = 0;

    public long timeLag = 0;

    private String mStringTime= ""; //시간을 표시해줄 string
    private String mLevelString = ""; // 레벨을 저장할 스트링

    private static boolean isTenSec = false; // 10초가 지날때마다 true
    public static boolean flag = true; // 쓰레드 플래그
    private boolean isTimeStop = false; // 보너스 타임중이냐 아니냐
    private boolean isPause = false; // 일시정지 중이더냐

    private TextView mGameTime;
    private TextView mCountBall;
    private TextView mMaxCountBall;
    private ImageView mBonusImage;
    private Button mPauseBtn;
    private ProgressBar mBonusGageBar;

    private LinearLayout mTextArea;

    private static Timer mPlaytime;

    private static CircleCanvasView mCircleCanvasView; // 원을 그려줄 뷰
    private RandomDrawableThread mRandomDrawableThread; // 원을 그리는 명령을 내릴 쓰레드
    private BonusGageThread mBonusGageThread;
    private SettingMode mSettingMode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        overridePendingTransition(0, 0);

        ShareData.init(this);

        initView();

        mLevelString = ShareData.getLevel();

        mSettingMode = new SettingMode(MainGame.this);

        mMaxCountBall.setText(mSettingMode.setLevelText(mLevelString));
        mTextArea.setBackgroundColor(mSettingMode.setBackColor(mLevelString));
        addTime = mSettingMode.setLevelIntervalTime(mLevelString);

        mRandomDrawableThread = new RandomDrawableThread(MainGame.this);

        showStartCount(NEW_START);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            switch (requestCode){
                case NEW_START:
                    ShareData.setIsGameOver(false);
                    //들어가서 시작해준다
                    mPlaytime = new Timer();
                    mPlaytime.schedule(new UpTimeTask(), 100, 50);
                    mRandomDrawableThread.start();
                    ShareData.setStartTime(System.currentTimeMillis());

                    break;

                case PAUSE:
                    ShareData.setIsGameOver(false);
                    showStartCount(PAUSE_START);
                    break;

                case PAUSE_START:
                    isPause = false;
                    ShareData.setStartTime(System.currentTimeMillis() - timeLag); // 일시정지 후 시작할때는 현재시간에서 경과시간을 빼준다
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(mPlaytime != null){
            mPlaytime.cancel();
            mPlaytime.purge();
            mPlaytime = null;
        }

        if(flag){
            flag = false;
        }

        if(mRandomDrawableThread != null){
            mRandomDrawableThread.interrupt();
            mRandomDrawableThread = null;
        }

        ShareData.setIsGameOver(false);

    }

    @Override
    protected void onResume() {
        super.onResume();

    }


    @Override
    public void onBackPressed() {
        isPause = true;
        Intent intent = new Intent(MainGame.this, AlertDialogPauseActivity.class);
        startActivityForResult(intent, PAUSE);

    }

    /**
     * 뷰 아이템들을 초기화 하기 위한 함수
     */
    private void initView(){
        mCountBall = (TextView)findViewById(R.id.count_ball);
        mGameTime = (TextView) findViewById(R.id.game_timer);
        mMaxCountBall = (TextView) findViewById(R.id.max_count_ball);
        mPauseBtn = (Button)findViewById(R.id.pause_btn);

        mCircleCanvasView = (CircleCanvasView)findViewById(R.id.circle_Area);

        mBonusGageBar = (ProgressBar)findViewById(R.id.bonus_gage);
        mBonusImage = (ImageView)findViewById(R.id.bonus_image);

        mTextArea = (LinearLayout)findViewById(R.id.textArea);

        mBonusImage.setOnClickListener(this);

        mPauseBtn.setOnClickListener(this);

        mCircleCanvasView.setMainGame(this);
//        mCircleCanvasView.setVisibility(View.VISIBLE);

        flag = true;
        bonusGage  = 0;

    }

    /**
     * 타이머 텍스트 설정
     * @param timeFormat 타이머 포맷 텍스트
     */
    public void setTimerTextView(String timeFormat){

        mGameTime.setText(timeFormat);

        ShareData.setUserTime(timeFormat);
    }

    /**
     * 사용자의 볼 갯수가 얼마나 나왔는 지 표시해주는 함수
     * @param ballCount 표시할 개수
     */
    public void setBallCount(int ballCount){
        mCountBall.setText(String.format("%02d", ballCount));
    }

    /**
     * 보너스 게이지바를 채워줄 함수
     * @param gage 채울 값
     */
    public void setBonusGage(int gage){
        bonusGage += gage;

        if(bonusGage >= 100){//게이지 다차면 보너스 시작
            mBonusGageBar.setProgress(100);
            Log.d(TAG, "게이지 다 찼다");
            isTimeStop = true;

            bonusTouchCount = 0;

            mCircleCanvasView.setClickable(false);
            mCircleCanvasView.removeAllBall();
            mCircleCanvasView.setVisibility(View.GONE);

            mBonusImage.setVisibility(View.VISIBLE);

            mBonusGageThread = new BonusGageThread();
            mBonusGageThread.start();
        }else{ // 안차면 채우기
            Log.d(TAG, "Gage >>> " + bonusGage);
            mBonusGageBar.setProgress(bonusGage);
        }
    }

    /**
     * 게임 종료시 호출되는 함수
     */
    public void finishGame(){
//        mGameTimer.cancel();

        ShareData.setIsGameOver(true);

        timeLag = 0;

        onDialog();
        mBonusGageBar.setProgress(0);
        mCircleCanvasView.setClickable(false);
//        mCircleCanvasView.setVisibility(View.INVISIBLE);

        if(mPlaytime != null){
            mPlaytime.cancel();
            mPlaytime.purge();
            mPlaytime = null;
        }

        if(flag){
            flag = false;
        }

        if(mRandomDrawableThread != null){
            mRandomDrawableThread.interrupt();
            mRandomDrawableThread = null;
        }
    }


    /**
     * 게임이 끝나면 종료 메뉴 다이얼로그 출력 함수
     */
    public void onDialog(){
        Intent popupIntent = new Intent(this, AlertDialogActivity.class);

        PendingIntent pie= PendingIntent.getActivity(this, 0, popupIntent, PendingIntent.FLAG_ONE_SHOT);
        try {
            pie.send();

        } catch (PendingIntent.CanceledException e) {
            Log.d(this.getClass().getSimpleName(), e.getMessage());
        }
    }

    /**
     * 게임 시작전 준비시간을 가지기 위한 시작 카운트 클래스 호출
     * @param code 1이면 맨첨 시작 3이면 pause
     */
    private void showStartCount(int code){
        Intent intent = new Intent(MainGame.this, CountStartGame.class);
        startActivityForResult(intent, code);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pause_btn:

                isPause = true;
                Intent intent = new Intent(MainGame.this, AlertDialogPauseActivity.class);
                startActivityForResult(intent, PAUSE);

                //일단 보류
                break;

            case R.id.bonus_image:

                bonusTouchCount += 1;

                break;
        }
    }

    /**
    * 게임화면으로 들어갈때 카운트다운을 해줄 카운트다운 타이머
    */
    public class UpTimeTask extends TimerTask {

        private long currentTime;
        int i = 9;

        private Runnable runnable = new Runnable() {
            @Override
            public void run() {
                setTimerTextView(mStringTime);
            }
        };

        @Override
        public void run() {

            if(!isPause){ // 일시정지 중이 아니면 돌고 일시정지 중이면 암것도 안하거
                currentTime = System.currentTimeMillis();

                long mills = Math.abs(currentTime - (long) ShareData.getStartTime()) ;

                timeLag = mills;

                int seconds = (int) (mills / 1000);
                int minutes = seconds / 60;
                int hour = minutes / 60;

                seconds = seconds % 60; // 안그럼 60초에서 1분으로 넘어가지않고 100에서 넘어감

                mills = (mills/10) % 100; //100의 자리와 10의 자리만 출력

                if(seconds >= 9 && seconds % i == 0){ // 여기서 런이 돌아가고 그림그리는 쓰레드에서 판단하는 데 약 1초가 소요되므로 9에서 판단
                    Log.d(TAG, "Sec % 10 >>> " + seconds%9);
                    i += 10;

                    if(i > 60){
                        i = 9;
                    }
                    if(!isTimeStop){ //보너스 시간이 아니면 공 주기를 감소시키고
                        isTenSec = true;
                    }else{ // 보너스 중이면 감소시키지 않는다
                        isTenSec = false;
                    }

                }

                mStringTime = String.format("%02d : %02d'%02d", minutes, seconds, mills);

                mGameTime.post(runnable);
            }

        }

    }


    /**
     * 핸들러 메세지를 받아서 원을 그린다
     */
    private Handler drawHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case FinalValue.DRAW: // 그리거라
                    mCircleCanvasView.setIsTime(true);
                    mCircleCanvasView.invalidate();
                    break;

                case FinalValue.BONUS: // 보너스 끝낫다
                    isTimeStop = false;

                    mCircleCanvasView.setVisibility(View.VISIBLE);
                    mCircleCanvasView.setClickable(true);

                    mBonusImage.setVisibility(View.GONE);

                    Log.d(TAG, "Bonus Count >>> " + bonusTouchCount);

                    int bonusTime = mSettingMode.setLevelBonusValue(mLevelString, bonusTouchCount);

                    addTime = addTime + bonusTime;

                    Log.d(TAG, "Add time + bonus >>> " +addTime);
                    break;
            }
        }
    };


    /**
     * 해당 스레드의 지연시간 뒤에 원을 그리는 쓰레드
     */
    private class RandomDrawableThread extends Thread {
        private Context context;

        public RandomDrawableThread(Context context){
            this.context = context;
        }

        @Override
        public void run() {

            while (flag) {
                if(!isPause){ // 일시정지 중이 아니면 돌고 일시정지 중이면 암것도 안하거

                    try {

                        if (isTenSec) {
                            if (addTime < 0) {
                                addTime = 1;
                            } else if (addTime <= 1000 && addTime > 500) {
                                addTime -= 300;
                            } else if (addTime <= 500 && addTime > 100) {
                                addTime -= 100;
                            } else if (addTime <= 100 && addTime > 50) {
                                addTime -= 20;
                            } else if (addTime <= 10 && addTime > 0) {
                                addTime -= 1;
                            } else {
                                addTime -= 500;
                                if(addTime < 0){
                                    addTime = 1;
                                }
                            }

                            Log.d(TAG, "Speed up >>> " + addTime);

                            isTenSec = false;

                            Thread.sleep(addTime);

                        } else {
                            Thread.sleep(addTime);
                        }

                        drawHandler.sendMessage(Message.obtain(drawHandler, FinalValue.DRAW));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }else{
                    drawHandler.removeMessages(FinalValue.DRAW);
                }

                //3초 지연하다가 원을 지운다
//                drawHandler.postDelayed(runnable, deleteTime);
            }

            if(!flag){ // 만약 스레드가 끝났으면 핸들러의 호출을 멈춘다
                drawHandler.removeMessages(FinalValue.DRAW);

                if(mRandomDrawableThread != null){
                    mRandomDrawableThread.interrupt();
                    mRandomDrawableThread = null;
                }

                flag = true;
            }

        }
    }

    /**
     * 게이지가 다 차면 0.1초마다 하나씩 줄이면서
     * 게이지를 감소시킬 쓰레드
     */
    private class BonusGageThread extends Thread{

        @Override
        public void run() {
            Log.d(TAG, "GO!!");
            while(bonusGage > 0){

                if(!isPause){ // 일시정지 중이 아니면 돌고 일시정지 중이면 암것도 안하거
                    if(mRandomDrawableThread != null){
                        flag = false;
                    }

                    --bonusGage;
                    Log.d(TAG, "Gage >>> " + bonusGage);

                    mBonusGageBar.setProgress(bonusGage);

                    try{

                        int sleepTime = mSettingMode.setLevelSleepValue(mLevelString);

                        mBonusGageThread.sleep(sleepTime);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }

            if(bonusGage <= 0){

                mBonusGageBar.setProgress(0);

                if(mBonusGageThread != null){
                    mBonusGageThread.interrupt();
                    mBonusGageThread = null;
                }

                if(mRandomDrawableThread == null){
                    mRandomDrawableThread = new RandomDrawableThread(MainGame.this);
                    mRandomDrawableThread.start();
                }

                drawHandler.sendEmptyMessage(FinalValue.BONUS);
            }
        }
    }


}
