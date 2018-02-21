package com.seungjun.touchball.dialog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.seungjun.touchball.MainActivity;
import com.seungjun.touchball.R;
import com.seungjun.touchball.game.MainGame;
import com.seungjun.touchball.util.SettingMode;
import com.seungjun.touchball.value.FinalValue;
import com.seungjun.touchball.value.ShareData;


/**
 * Created by user on 2015-09-10.
 */
public class AlertDialogActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "AlertDialogActivity";

    private RelativeLayout resultMenu;
    private TextView resultUserTime;
    private TextView resultContext;
    private TextView resultTitle;
    private Button returnMenuBtn;
    private Button restartGameBtn;

    private SettingMode mSettingMode;
    private ButtonThread  mButtonThread;
    private String userTime = "";
    private String level = "";

    private int count = 3;

    private boolean flag = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이블 없애기
        setContentView(R.layout.alertdialog_game_over);
        overridePendingTransition(0, 0);

        initView();

        level = ShareData.getLevel();

        mSettingMode = new SettingMode(AlertDialogActivity.this);

        resultMenu.setBackground(mSettingMode.setBackDrawable(level));
        resultTitle.setBackgroundColor(mSettingMode.setBackColor(level));

        mButtonThread = new ButtonThread();
        mButtonThread.start();

        userTime = ShareData.getUserTime();

        resultUserTime.setText(userTime);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) { // 영역 이외 터치 맊아버렷
        return false;
    }

    /**
     * 초 기 화
     */
    private void initView(){
        resultMenu = (RelativeLayout)findViewById(R.id.over_menu_area);
        resultUserTime = (TextView)findViewById(R.id.result_user_time);
        resultContext = (TextView)findViewById(R.id.result_context);
        resultTitle = (TextView)findViewById(R.id.textTitle);
        restartGameBtn = (Button)findViewById(R.id.restart_game);
        returnMenuBtn = (Button)findViewById(R.id.return_menu);

        restartGameBtn.setOnClickListener(this);
        returnMenuBtn.setOnClickListener(this);

        restartGameBtn.setEnabled(false);
        returnMenuBtn.setEnabled(false);
    }

    @Override
    public void onBackPressed() { //뒤로가기 막아버렷
//        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(mButtonThread != null){
            mButtonThread.interrupt();
            mButtonThread = null;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.return_menu: // 초기 메뉴로 가는 버튼을 누르면
                ShareData.setIsGameOver(false);
                Intent remIntent = new Intent(AlertDialogActivity.this, MainActivity.class);
                remIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(remIntent);

                finish();
                break;


            case R.id.restart_game: // 다시 시작 버튼을 누르면
                ShareData.setIsGameOver(false);
                Intent regIntent = new Intent(AlertDialogActivity.this, MainGame.class);
                regIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(regIntent);

                finish();
                break;
        }
    }

    /**
     * 3초 뒤 버튼을 활성화 시켜줄 핸들러
     */
    private Handler textHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case FinalValue.COUNT:
                    if(count != 0){
                        resultContext.setText(String.valueOf(count) + getResources().getString(R.string.result_context_count));
                        count--;
                    }else{
                        resultContext.setText(getResources().getString(R.string.result_context));

                        restartGameBtn.setEnabled(true);
                        returnMenuBtn.setEnabled(true);

                        flag = false;
                    }

                    break;
            }
        }
    };

    /**
     * 버튼 막누르다가 메뉴 눌러지는 것을 방지하기위한
     * 버튼 활성화 딜레이 쓰레드
     */
    private class ButtonThread extends Thread{

        @Override
        public void run() {

            while(flag){
                try{
                    mButtonThread.sleep(1000);
                    textHandler.sendEmptyMessage(FinalValue.COUNT);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

