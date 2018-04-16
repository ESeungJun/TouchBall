package com.seungjun.touchball;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.seungjun.touchball.dialog.CustomDialog;
import com.seungjun.touchball.dialog.CustomSelectDialog;
import com.seungjun.touchball.game.MainGame;
import com.seungjun.touchball.rank.RankActivity;
import com.seungjun.touchball.util.LogUtil;
import com.seungjun.touchball.util.SettingMode;
import com.seungjun.touchball.value.ShareData;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button btnStart;
    private Button btnExit;
    private Button btnSetting;
    private Button btnRank;

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
        btnRank = (Button)findViewById(R.id.main_Rank_btn);

        mModeText = (TextView)findViewById(R.id.main_text_mode);

        mMainBackActivity = (LinearLayout)findViewById(R.id.main_back_activity);

        ShareData.setLevel("easy"); // 첨엔 무조건 이지

        mModeText.setText(String.format("\u003C %s \u003E", ShareData.getLevel()));

        btnStart.setOnClickListener(this);
        btnExit.setOnClickListener(this);
        btnSetting.setOnClickListener(this);
        btnRank.setOnClickListener(this);
    }

    /**
     * 랭킹 액티비티 출력 함수
     */
    public void onDialog(){
        Intent popupIntent = new Intent(this, RankActivity.class);

        PendingIntent pie= PendingIntent.getActivity(this, 0, popupIntent, PendingIntent.FLAG_ONE_SHOT);
        try {
            pie.send();

        } catch (PendingIntent.CanceledException e) {
            LogUtil.d(this.getClass().getSimpleName(), e.getMessage());
        }
    }

    @Override
    public void onBackPressed() {

        final CustomDialog mCustomDialog = new CustomDialog(this, 0);

        mCustomDialog.setTitle(getResources().getString(R.string.exit_popup_title));
        mCustomDialog.setContent(getResources().getString(R.string.exit_popup_context));
        mCustomDialog.setOkText(getResources().getString(R.string.exit_popup_ok_text));
        mCustomDialog.setOk(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCustomDialog.dismiss();
                ActivityCompat.finishAffinity(MainActivity.this);
            }
        });

        mCustomDialog.setCancelText(getResources().getString(R.string.exit_popup_cancel_text));
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

                mCustomDialog.setTitle(getResources().getString(R.string.exit_popup_title));
                mCustomDialog.setContent(getResources().getString(R.string.exit_popup_context));
                mCustomDialog.setOkText(getResources().getString(R.string.exit_popup_ok_text));
                mCustomDialog.setOk(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCustomDialog.dismiss();
                        ActivityCompat.finishAffinity(MainActivity.this);
                    }
                });

                mCustomDialog.setCancelText(getResources().getString(R.string.exit_popup_cancel_text));
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
                mCustomSelectDialog.setCancelText(getResources().getString(R.string.setting_popup_cancel_text));
                mCustomSelectDialog.setCancel(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCustomSelectDialog.dismiss();
                    }
                });

                mCustomSelectDialog.setOkText(getResources().getString(R.string.setting_popup_ok_text));
                mCustomSelectDialog.setOk(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ShareData.setLevel(mCustomSelectDialog.onLevel());

                        mModeText.setText(String.format("\u003C %s \u003E", ShareData.getLevel()));

                        mMainBackActivity.setBackgroundColor(mSettingMode.getBackColor(ShareData.getLevel()));

                        mCustomSelectDialog.dismiss();
                    }
                });

                mCustomSelectDialog.show();

                break;


            case R.id.main_Rank_btn:
                onDialog();
                break;
        }
    }
}
