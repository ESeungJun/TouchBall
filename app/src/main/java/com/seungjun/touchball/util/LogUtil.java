package com.seungjun.touchball.util;

import android.util.Log;

/**
 * Created by SeungJun on 2017-05-19.
 */

public class LogUtil {

    private final static boolean isUseLog = true;


    public static void d(String tag, String msg){

        if(isUseLog)
            Log.d(tag, msg);

    }

}
