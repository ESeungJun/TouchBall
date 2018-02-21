package com.seungjun.touchball.util;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;

/**
 * 카운트를 업해주는 타이머
 * Created by SeungJun on 2016-05-18.
 */
public abstract class CountUpTimer {

    private long mCountUpInterval; // 시간 주기

    private long mStartTime;

    private boolean mCancelled = false; // 취소여부 판단


    /**
     * 카운트 업 시킬 시간 주기를 입력한다
     * @param countUpInterval 인터벌 주기
     */
    public CountUpTimer(long countUpInterval){
        mCountUpInterval = countUpInterval;
    }

    /**
     * 타이머를 종료하고자 할때 호출
     */
    public synchronized final void cancel(){
        mCancelled = true;

    }

    /**
     * 타이머를 시작한다
     * @return CountUpTimer
     */
    public synchronized final CountUpTimer start(){
        mCancelled = false;

        mStartTime = SystemClock.elapsedRealtime();

        mHandler.sendMessage(mHandler.obtainMessage(MSG));

        return this;
    }

    /**
     * 입력한 주기마다 호출하는 함수
     * @param timeElapse 흐른 시간
     */
    public abstract void onTick(long timeElapse);

    private static final int MSG = 1;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            synchronized (CountUpTimer.this){
                if(mCancelled){
                    return;
                }
                //시작시간과 현재시간의 차이를 구해서
                final long timeElapse = Math.abs(mStartTime - SystemClock.elapsedRealtime());

                //함수의 인자로 전달해준다
                onTick(timeElapse);

                // 마지막 시작 시간을 구한다음에
                long lastTickStart = SystemClock.elapsedRealtime();

                // 마지막 시작시간 + 인터벌 주기 - 현재 시간
                long delay = lastTickStart + mCountUpInterval - SystemClock.elapsedRealtime();

                //그게 만약에 0보다 크면 딜레이에서 현재 주기를 빼주면 내가 대기해야하는 인터벌 딜레이 주기가 나온다.
                // 그 주기만큼 핸들러를 반복실행해준다
                while (delay > 0) delay -= mCountUpInterval;

                sendMessageDelayed(obtainMessage(MSG),delay);
            }
        }
    };
}
