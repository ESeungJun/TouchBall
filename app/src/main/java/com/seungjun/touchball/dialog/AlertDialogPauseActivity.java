package com.seungjun.touchball.dialog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.seungjun.touchball.MainActivity;
import com.seungjun.touchball.R;
import com.seungjun.touchball.util.LogUtil;
import com.seungjun.touchball.util.SettingMode;
import com.seungjun.touchball.value.ShareData;

/**
 * Created by SeungJun on 2016-05-20.
 */
public class AlertDialogPauseActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "AlPauseActivity";

    private RelativeLayout resultMenu;
//    private TextView resultContext;
//    private TextView resultTitle;
    private Button returnMenuBtn;
    private Button restartGameBtn;

    private SettingMode mSettingMode;
    private String level = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d(TAG, "onCreate");
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이블 없애기
        setContentView(R.layout.alertdialog_pause);
        overridePendingTransition(0, 0);

        initView();

        level = ShareData.getLevel();

        mSettingMode = new SettingMode(AlertDialogPauseActivity.this);

        resultMenu.setBackground(mSettingMode.getBackDrawable(level));

        restartGameBtn.setBackground(mSettingMode.getBackSelectDrawable(level));
        returnMenuBtn.setBackground(mSettingMode.getBackSelectDrawable(level));
//        resultTitle.setBackgroundColor(mSettingMode.setBackColor(level));

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) { // 영역 이외 터치 맊아버렷
        return false;
    }

    /**
     * 초 기 화
     */
    private void initView(){
        resultMenu = (RelativeLayout)findViewById(R.id.pause_menu_area);
//        resultContext = (TextView)findViewById(R.id.result_context);
//        resultTitle = (TextView)findViewById(R.id.pause_textTitle);
        restartGameBtn = (Button)findViewById(R.id.restart_pause_game);
        returnMenuBtn = (Button)findViewById(R.id.return_pause_menu);

        restartGameBtn.setOnClickListener(this);
        returnMenuBtn.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() { //뒤로가기 막아버렷
//        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.return_pause_menu: // 초기 메뉴로 가는 버튼을 누르면
                ShareData.setIsGameOver(false);
                Intent remIntent = new Intent(AlertDialogPauseActivity.this, MainActivity.class);
                remIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(remIntent);

                finish();
                break;

            case R.id.restart_pause_game: // 다시 시작 버튼을 누르면
                ShareData.setIsGameOver(false);
                Intent intent = getIntent();
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }


}