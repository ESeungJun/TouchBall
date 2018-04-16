package com.seungjun.touchball.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.seungjun.touchball.R;
import com.seungjun.touchball.util.SettingMode;
import com.seungjun.touchball.value.ShareData;


/**
 * Created by SeungJun on 2016-03-23.
 */
public class CustomDialog extends Dialog {

    private LinearLayout mDialogBack;
    private TextView mTitleView;
    private TextView mContentView;
    private Button mLeftButton;
    private Button mRightButton;
    private Context mContext;

    private SettingMode mSettingMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                             WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public CustomDialog(Context context, int type) {
        // Dialog 배경을 투명 처리 해준다.
        super(context, android.R.style.Theme_Translucent_NoTitleBar);

        mContext = context;

        mSettingMode = new SettingMode(mContext);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.custom_dialog);

        mDialogBack = (LinearLayout)findViewById(R.id.tv_back);
        mTitleView = (TextView) findViewById(R.id.tv_title);
        mContentView = (TextView) findViewById(R.id.tv_content);
        mLeftButton = (Button) findViewById(R.id.bt_left);
        mRightButton = (Button) findViewById(R.id.bt_right);

        mContentView.setMovementMethod(new ScrollingMovementMethod());

        if(type == 1){
            mRightButton.setVisibility(View.GONE);
        }


        mDialogBack.setBackground(mSettingMode.getBackDrawable(ShareData.getLevel()));
        mLeftButton.setBackground(mSettingMode.getBackSelectSettingDrawable(ShareData.getLevel()));
        mRightButton.setBackground(mSettingMode.getBackSelectSettingDrawable(ShareData.getLevel()));
        mTitleView.setBackgroundColor(mSettingMode.getBackColor(ShareData.getLevel()));

    }

    public void setTitle(String title){
        mTitleView.setText(title);
    }
    public void setTitle(int title){
        mTitleView.setText(title);
    }

    public void setContent(String content){
        mContentView.setText(content);
    }

    public void setContent(int content){
        mContentView.setText(content);
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


}
