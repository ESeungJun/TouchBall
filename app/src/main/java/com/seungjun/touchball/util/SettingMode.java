package com.seungjun.touchball.util;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.seungjun.touchball.R;
import com.seungjun.touchball.value.FinalValue;

/**
 * Created by SeungJun on 2016-05-19.
 */
public class SettingMode {

    Context mContext;

    public SettingMode(Context context){
        mContext = context;
    }


    /**
     * 난이도에 따른 컬러설정
     * @param level 난이도
     */
    public int setBackColor(String level){

        if(level.equals("easy")){
            return mContext.getResources().getColor(R.color.easy_background);

        }else if(level.equals("normal")){
            return mContext.getResources().getColor(R.color.normal_background);

        }else if(level.equals("hard")){
            return mContext.getResources().getColor(R.color.hard_background);

        }else if(level.equals("hell")){
            return mContext.getResources().getColor(R.color.hell_background);
        }

        return mContext.getResources().getColor(R.color.easy_background);
    }

    /**
     * 난이도에 따른 Drawable
     * @param level 난이도
     */
    public Drawable setBackDrawable(String level){

        if(level.equals("easy")){
            return mContext.getResources().getDrawable(R.drawable.boder_easy);

        }else if(level.equals("normal")){
            return mContext.getResources().getDrawable(R.drawable.boder_normal);

        }else if(level.equals("hard")){
            return mContext.getResources().getDrawable(R.drawable.boder_hard);

        }else if(level.equals("hell")){
            return mContext.getResources().getDrawable(R.drawable.boder_hell);
        }

        return mContext.getResources().getDrawable(R.drawable.boder_easy);
    }

    /**
     * 난이도에 따른 볼 최대값
     * @param level 난이도
     * @return 해당 난이도 최대값
     */
    public String setLevelText(String level){

        if(level.equals("easy")){
            return String.valueOf(FinalValue.EASY_MAX_COUNT_BALL);

        }else if(level.equals("normal")){
            return String.valueOf(FinalValue.NORMAL_MAX_COUNT_BALL);

        }else if(level.equals("hard")){
            return String.valueOf(FinalValue.HARD_MAX_COUNT_BALL);

        }else if(level.equals("hell")){
            return String.valueOf(FinalValue.HELL_MAX_COUNT_BALL);
        }

        return String.valueOf(FinalValue.EASY_MAX_COUNT_BALL);
    }


    /**
     * 난이도에 따른 쓰레드 대기시간
     * @param level 난이도
     * @return 쓰레드 대기 시간
     */
    public int setLevelSleepValue(String level){

        if(level.equals("easy")){
            return FinalValue.EASY_SLEEP_VALUE;

        }else if(level.equals("normal")){
            return FinalValue.NORMAL_SLEEP_VALUE;

        }else if(level.equals("hard")){
            return FinalValue.HARD_SLEEP_VALUE;

        }else if(level.equals("hell")){
            return FinalValue.HELL_SLEEP_VALUE;
        }

        return FinalValue.EASY_SLEEP_VALUE;

    }

    /**
     * 난이도에 따른 보너스 시간 계산값
     * @param level 난이도
     * @param bonusTouchCount 클릭 횟수
     * @return 계산 값
     */
    public int setLevelBonusValue(String level, int bonusTouchCount){

        if(level.equals("easy")){
            return bonusTouchCount * 10;

        }else if(level.equals("normal")){
            return bonusTouchCount * 8;

        }else if(level.equals("hard")){
            return bonusTouchCount * 4;

        }else if(level.equals("hell")){
            return bonusTouchCount;
        }

        return bonusTouchCount * 10;

    }


    /**
     * 난이도에 따른 볼 생성 주기
     * @param level 난이도
     * @return 볼 생성 주기
     */
    public int setLevelIntervalTime(String level){

        if(level.equals("easy")){
            return FinalValue.EASY_INTERVAL_VALUE;

        }else if(level.equals("normal")){
            return FinalValue.NORMAL_INTERVAL_VALUE;

        }else if(level.equals("hard")){
            return FinalValue.HARD_INTERVAL_VALUE;

        }else if(level.equals("hell")){
            return FinalValue.HELL_INTERVAL_VALUE;
        }

        return FinalValue.EASY_INTERVAL_VALUE;

    }

}
