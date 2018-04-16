package com.seungjun.touchball.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.seungjun.touchball.R;
import com.seungjun.touchball.util.SettingMode;
import com.seungjun.touchball.value.ShareData;

/**
 * Created by SeungJun on 2016-03-23.
 */
public class CustomSelectDialog extends Dialog implements View.OnClickListener {

    private LinearLayout mDialogBack;
    private TextView mTitleView;
    private RadioButton mEasyRadio;
    private RadioButton mNormalRadio;
    private RadioButton mHardRadio;
    private RadioButton mHellRadio;

    private Button mLeftButton;
    private Button mRightButton;

    private String mSetLevel;

    private Context mContext;

    private SettingMode mSettingMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                             WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 커스텀 셀렉터 다이얼로그 생성자
     * @param context 띄울 액티비티
     * @param type 1이면 버튼 1개만 그외는 2개
     */
    public CustomSelectDialog(Context context, int type) {
        // Dialog 배경을 투명 처리 해준다.
        super(context, android.R.style.Theme_Translucent_NoTitleBar);

        mContext = context;

        mSettingMode = new SettingMode(mContext);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.custom_select_dialog);

        mDialogBack = (LinearLayout)findViewById(R.id.tv_back);
        mTitleView = (TextView) findViewById(R.id.tv_title);

        mEasyRadio = (RadioButton)findViewById(R.id.setting_easy);
        mNormalRadio = (RadioButton)findViewById(R.id.setting_normal);
        mHardRadio = (RadioButton)findViewById(R.id.setting_hard);
        mHellRadio = (RadioButton)findViewById(R.id.setting_hell);

        mEasyRadio.setOnClickListener(this);
        mNormalRadio.setOnClickListener(this);
        mHardRadio.setOnClickListener(this);
        mHellRadio.setOnClickListener(this);

        mEasyRadio.setChecked(true);

        mLeftButton = (Button) findViewById(R.id.bt_left);
        mRightButton = (Button) findViewById(R.id.bt_right);

        if(type == 1){
            mRightButton.setVisibility(View.GONE);
        }

    }

    public void setTitle(String title){
        mTitleView.setText(title);
    }
    public void setTitle(int title){
        mTitleView.setText(title);
    }

    public void setCancelText(String text){
        mLeftButton.setText(text);
    }

    public void setCancelText(int text){
        mLeftButton.setText(text);
    }

    public void setOkText(String text){
        mRightButton.setText(text);
    }

    public void setOkText(int text){
        mRightButton.setText(text);
    }

    /**
     * 취소 버튼 클릭 리스너
     */

    public void setCancel(View.OnClickListener cancelListener) {
        mLeftButton.setOnClickListener(cancelListener);
    }

    /**
     * 확인 버튼 클릭 리스너
     */
    public void setOk(View.OnClickListener okListener) {
        mRightButton.setOnClickListener(okListener);
    }

    /**
     * 레벨을 설정
     * @return 설정된 레벨
     */
    public String onLevel(){

        if(mSetLevel == null || mSetLevel.equals(""))
            mSetLevel = "easy";

        return mSetLevel;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.setting_easy:
                mSetLevel = "easy";

                break;

            case R.id.setting_normal:
                mSetLevel = "normal";
                break;

            case R.id.setting_hard:
                mSetLevel = "hard";
                break;

            case R.id.setting_hell:
                mSetLevel = "hell";
                break;
        }

        mDialogBack.setBackground(mSettingMode.getBackDrawable(mSetLevel));
        mLeftButton.setBackground(mSettingMode.getBackSelectSettingDrawable(mSetLevel));
        mRightButton.setBackground(mSettingMode.getBackSelectSettingDrawable(mSetLevel));
        mTitleView.setBackgroundColor(mSettingMode.getBackColor(mSetLevel));

    }

}
