package com.seungjun.touchball.value;

/**
 * Created by SeungJun on 2016-05-18.
 */
public class FinalValue {

    //================볼 최대 갯수=====================
    public static final int EASY_MAX_COUNT_BALL = 30;
    public static final int NORMAL_MAX_COUNT_BALL = 20;
    public static final int HARD_MAX_COUNT_BALL = 10;
    public static final int HELL_MAX_COUNT_BALL = 100;

    //==============보너스 게이지 증가량=================
    public static final int EASY_GAGE_VALUE = 3;
    public static final int NORMAL_GAGE_VALUE = 5;
    public static final int HARD_GAGE_VALUE = 10;
    public static final int HELL_GAGE_VALUE = 5;

    //==============보너스 게이지 감소주기=================
    public static final int EASY_SLEEP_VALUE = 70;
    public static final int NORMAL_SLEEP_VALUE = 40;
    public static final int HARD_SLEEP_VALUE = 25;
    public static final int HELL_SLEEP_VALUE = 10;

    //=================볼 생성 주기====================
    public static final int EASY_INTERVAL_VALUE = 1500;
    public static final int NORMAL_INTERVAL_VALUE = 900;
    public static final int HARD_INTERVAL_VALUE = 600;
    public static final int HELL_INTERVAL_VALUE = 20;

    //============핸들러 코드============
    public static final int DRAW = 101;
    public static final int BONUS = 102;
    public static final int COUNT = 103;

    //=================아이쳄 터치 값 ==================
    public static final int ITEM_REMOVE_EASY_TAP_VALUE = 250;
    public static final int ITEM_REMOVE_NORMAL_TAP_VALUE = 350;
    public static final int ITEM_REMOVE_HARD_TAP_VALUE = 700;
    public static final int ITEM_REMOVE_HELL_TAP_VALUE = 1000;

    public static final int ITEM_TIME_EASY_TAP_VALUE = 100;
    public static final int ITEM_TIME_NORMAL_TAP_VALUE = 200;
    public static final int ITEM_TIME_HARD_TAP_VALUE = 300;
    public static final int ITEM_TIME_HELL_TAP_VALUE = 700;

    public static final int MAX_TAP_VALUE = 10000;
}
