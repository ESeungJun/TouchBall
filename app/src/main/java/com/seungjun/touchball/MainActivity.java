package com.seungjun.touchball;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.seungjun.touchball.dialog.CustomDialog;
import com.seungjun.touchball.dialog.CustomSelectDialog;
import com.seungjun.touchball.game.MainGame;
import com.seungjun.touchball.util.SettingMode;
import com.seungjun.touchball.value.ShareData;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button btnStart;
    private Button btnExit;
    private Button btnSetting;

    private TextView mModeText;

    private LinearLayout mMainBackActivity;

    private CustomSelectDialog mCustomSelectDialog;
    private SettingMode mSettingMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        overridePendingTransition(0, 0);

        ShareData.init(this);

        initView();

        mSettingMode = new SettingMode(MainActivity.this);


    }

    /**
     * Main activity 초기화 함수
     */
    private void initView(){

        btnStart = (Button)findViewById(R.id.main_Start_btn);
        btnExit = (Button)findViewById(R.id.main_Exit_btn);
        btnSetting = (Button)findViewById(R.id.main_Setting_btn);

        mModeText = (TextView)findViewById(R.id.main_text_mode);

        mMainBackActivity = (LinearLayout)findViewById(R.id.main_back_activity);

        ShareData.setLevel("easy"); // 첨엔 무조건 이지

        mModeText.setText(String.format("\u003C %s \u003E", ShareData.getLevel()));

        btnStart.setOnClickListener(this);
        btnExit.setOnClickListener(this);
        btnSetting.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {

        final CustomDialog mCustomDialog = new CustomDialog(this, 0);

        mCustomDialog.setTitle("나가기");
        mCustomDialog.setContent("종료 하시겠습니까?");
        mCustomDialog.setOkText("종료");
        mCustomDialog.setOk(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCustomDialog.dismiss();
                ActivityCompat.finishAffinity(MainActivity.this);
            }
        });

        mCustomDialog.setCancelText("취소");
        mCustomDialog.setCancel(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCustomDialog.dismiss();
            }
        });

        mCustomDialog.show();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.main_Start_btn: // 시작 버튼

                Intent intent = new Intent(MainActivity.this, MainGame.class);
                startActivity(intent);
                break;

            case R.id.main_Exit_btn: // 종료 버튼

                final CustomDialog mCustomDialog = new CustomDialog(this, 0);

                mCustomDialog.setTitle("나가기");
                mCustomDialog.setContent("종료 하시겠습니까?");
                mCustomDialog.setOkText("종료");
                mCustomDialog.setOk(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCustomDialog.dismiss();
                        ActivityCompat.finishAffinity(MainActivity.this);
                    }
                });

                mCustomDialog.setCancelText("취소");
                mCustomDialog.setCancel(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCustomDialog.dismiss();
                    }
                });

                mCustomDialog.show();

                break;

            case R.id.main_Setting_btn:

                mCustomSelectDialog = new CustomSelectDialog(MainActivity.this, 0);
                mCustomSelectDialog.setCancelText("취소");
                mCustomSelectDialog.setCancel(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCustomSelectDialog.dismiss();
                    }
                });

                mCustomSelectDialog.setOkText("설정");
                mCustomSelectDialog.setOk(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ShareData.setLevel(mCustomSelectDialog.onLevel());

                        mModeText.setText(String.format("\u003C %s \u003E", ShareData.getLevel()));

                        mMainBackActivity.setBackgroundColor(mSettingMode.setBackColor(ShareData.getLevel()));

                        mCustomSelectDialog.dismiss();
                    }
                });

                mCustomSelectDialog.show();

                break;
        }
    }
}
