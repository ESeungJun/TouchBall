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
    public int getBackColor(String level){

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
    public Drawable getBackDrawable(String level){

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
     * 난이도에 따른 버튼 select Drawable
     * @param level 난이도
     */
    public Drawable getBackSelectDrawable(String level){

        if(level.equals("easy")){
            return mContext.getResources().getDrawable(R.drawable.select_easy_over_btn);

        }else if(level.equals("normal")){
            return mContext.getResources().getDrawable(R.drawable.select_normal_over_btn);

        }else if(level.equals("hard")){
            return mContext.getResources().getDrawable(R.drawable.select_hard_over_btn);

        }else if(level.equals("hell")){
            return mContext.getResources().getDrawable(R.drawable.select_hell_over_btn);
        }

        return mContext.getResources().getDrawable(R.drawable.select_easy_over_btn);
    }

    /**
     * 난이도에 따른 설정 버튼 Drawable
     * @param level 난이도
     */
    public Drawable getBackSelectSettingDrawable(String level){

        if(level.equals("easy")){
            return mContext.getResources().getDrawable(R.drawable.select_easy_setting_btn);

        }else if(level.equals("normal")){
            return mContext.getResources().getDrawable(R.drawable.select_normal_setting_btn);

        }else if(level.equals("hard")){
            return mContext.getResources().getDrawable(R.drawable.select_hard_setting_btn);

        }else if(level.equals("hell")){
            return mContext.getResources().getDrawable(R.drawable.select_hell_setting_btn);
        }

        return mContext.getResources().getDrawable(R.drawable.select_easy_setting_btn);
    }


    /**
     * 난이도에 따른 볼 최대값
     * @param level 난이도
     * @return 해당 난이도 최대값
     */
    public String getLevelText(String level){

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
    public int getLevelSleepValue(String level){

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
    public int getLevelBonusValue(String level, int bonusTouchCount){

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
    public int getLevelIntervalTime(String level){

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

    /**
     * 난이도 및 아이템에 따른 탭 값
     * @param level 난이도
     * @param item 아이템 종류
     * @return 해당 난이도의 아이템 탭 값
     */
    public int getLevelItemValue(String level, String item){

        if(item.equals("remove")){

            if(level.equals("easy")){
                return FinalValue.ITEM_REMOVE_EASY_TAP_VALUE;

            }else if(level.equals("normal")){
                return FinalValue.ITEM_REMOVE_NORMAL_TAP_VALUE;

            }else if(level.equals("hard")){
                return FinalValue.ITEM_REMOVE_HARD_TAP_VALUE;

            }else if(level.equals("hell")){
                return FinalValue.ITEM_REMOVE_HELL_TAP_VALUE;
            }

            return FinalValue.ITEM_REMOVE_EASY_TAP_VALUE;

        }else {

            if(level.equals("easy")){
                return FinalValue.ITEM_TIME_EASY_TAP_VALUE;

            }else if(level.equals("normal")){
                return FinalValue.ITEM_TIME_NORMAL_TAP_VALUE;

            }else if(level.equals("hard")){
                return FinalValue.ITEM_TIME_HARD_TAP_VALUE;

            }else if(level.equals("hell")){
                return FinalValue.ITEM_TIME_HELL_TAP_VALUE;
            }

            return FinalValue.ITEM_TIME_EASY_TAP_VALUE;
        }


    }
}
