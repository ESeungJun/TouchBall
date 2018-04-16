package com.seungjun.touchball.value;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * 공유하고 저장할 자원들의 모임
 * Created by SeungJun on 2016-03-09.
 */
public class ShareData {
    private static final String TAG = "ShareData";

    private static final String KEY_USER_TIME = "key_user_time";
    private static final String KEY_START_TIME = "key_start_time";
    private static final String KEY_SET_LEVEL = "key_set_level";
    private static final String KEY_GAME_OVER = "key_game_over";

    private static SharedPreferences mPreferences;	 //Preference 객체
    private static SharedPreferences.Editor mEditor; //Preference에 값을 수정하는 editor 객체

    /**
     * 공유 자원 초기화 시키기
     * @param con
     */
    public static void init(Context con) {
        if(mPreferences == null)
            mPreferences = PreferenceManager.getDefaultSharedPreferences(con);
        if(mPreferences != null) mEditor = mPreferences.edit();
    }

    /**
     * 유저의 시간 시록을 저장한다
     * @param userTime 유저 시간 기록
     */
    public static void setUserTime(String userTime){
        if(mEditor != null){
            mEditor.putString(KEY_USER_TIME, userTime);
            mEditor.commit();
//            LogUtil.d(TAG, "Set User Time >>>> " + userTime);
        }
    }


    /**
     * 저장시킨 유저의 시간 기록을 반환한다
     * @return 유저 시간 기록
     */
    public static String getUserTime(){
        String userTime = "";

        if(mPreferences != null){
            userTime = mPreferences.getString(KEY_USER_TIME, userTime);
            return userTime;
        }
        return userTime;
    }


    /**
     * 게임 시작 버튼을 누른 시간을 저장한다
     * @param startTime
     */
    public static void setStartTime(long startTime){
        if(mEditor != null){
            mEditor.putLong(KEY_START_TIME, startTime);
            mEditor.commit();
//            LogUtil.d(TAG, "Set Start Time >>>> " + startTime);
        }
    }


    /**
     * 게임 시작 버튼을 누른 시간을 반환한다
     * @return
     */
    public static long getStartTime(){
        long startTime = 0;

        if(mPreferences != null){
            startTime = mPreferences.getLong(KEY_START_TIME, startTime);
//            LogUtil.d(TAG, "Get Start Time >>>> " + startTime);
            return startTime;
        }
//        LogUtil.d(TAG, "Get Null Start Time >>>> " + startTime);
        return startTime;
    }


    /**
     * 사용자가 선택한 레벨을 저장한다
     * @param level
     */
    public static void setLevel(String level){
        if(mEditor != null){
            mEditor.putString(KEY_SET_LEVEL, level);
            mEditor.commit();
        }
    }


    /**
     * 사용자가 선택한 레벨을 반환하다
     * @return
     */
    public static String getLevel(){
        String level = "easy";

        if(mPreferences != null){
            level = mPreferences.getString(KEY_SET_LEVEL, level);
            return level;
        }
        return level;
    }


    public static void setIsGameOver(boolean gameOver){
        if(mEditor != null){
            mEditor.putBoolean(KEY_GAME_OVER, gameOver);
            mEditor.commit();
        }
    }


    public static boolean getIsGameOver(){
        boolean gameOver = false;

        if(mPreferences != null){
            gameOver = mPreferences.getBoolean(KEY_GAME_OVER, gameOver);
            return gameOver;
        }
        return gameOver;
    }
}

