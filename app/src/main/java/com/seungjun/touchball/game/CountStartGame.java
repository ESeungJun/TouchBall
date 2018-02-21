package com.seungjun.touchball.game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.widget.TextView;

import com.seungjun.touchball.R;


/**
 * 게임 시작 전 준비시간을 카운트다운 하는 클래스
 *
 * Created by SeungJun on 2016-05-17.
 */
public class CountStartGame extends Activity {

    private static final int END_TIME = 100;

    private TextView startCountText;

    // GO! 메세지 출력을 위한 핸들러 객체
    private Handler endTimeHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){
                case END_TIME:
                    startCountText.setText("Go!");

                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_countstartgame);

        startCountText = (TextView)findViewById(R.id.start_count);

        // 게임 시작 타운트다운을 위한 객체
        CountDownTimer countDownTimer = new CountDownTimer(4000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                int sec = (int) ((millisUntilFinished / 1000));

                startCountText.setText(String.format("%d", sec));


                if(sec == 1){ //1초에 진입하면 1초뒤에 GO! 메세지 출력
                    endTimeHandler.sendEmptyMessageDelayed(END_TIME, 1000);
                }

            }

            @Override
            public void onFinish() {
                Intent intent = getIntent();
                setResult(RESULT_OK, intent);
                finish();
            }
        };

        countDownTimer.start();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }

    @Override
    public void finish() {
        super.finish();

        overridePendingTransition(0, 0);

    }

}
