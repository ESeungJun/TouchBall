package com.seungjun.touchball.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.seungjun.touchball.value.FinalValue;
import com.seungjun.touchball.value.ShareData;

import java.util.ArrayList;
import java.util.Random;

/**
 * circle Area에 랜덤한 위치에 랜덤한 색상을 가진
 * 원을 그려주는 클래스
 * MainGame에서 실행되는 Thread 객체 지연시간에 맞게 끔 그려준다
 * Created by SeungJun on 2016-05-17.
 */
public class CircleCanvasView extends View {

    private static final String TAG = "CircleCanvasView";

    private Paint paint;

    private MainGame mainGame;

    private Random random = new Random();

    private ArrayList<ValueCircle> pointArray;
    private ArrayList<Paint> paintArray;

    private ValueCircle valueCircle;

    private boolean isTime = false; // 타이머 완료 여부 (시간이 다되면 원을 그리기 위해 )
    private boolean isGameOver = false; // 게임 오버 여부 판단

    private int getIndex = 0; //터치한 곳의 위치

    private int levelMaxCount = 0; // 해당 레벨의 최대 갯수
    private int levelBonusValue = 0;

    public CircleCanvasView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        pointArray = new ArrayList<ValueCircle>();
        paintArray = new ArrayList<Paint>();

        switch (ShareData.getLevel()) {

            case "easy":
                levelMaxCount = FinalValue.EASY_MAX_COUNT_BALL;
                levelBonusValue = FinalValue.EASY_GAGE_VALUE;
                break;

            case "normal":
                levelMaxCount = FinalValue.NORMAL_MAX_COUNT_BALL;
                levelBonusValue = FinalValue.NORMAL_GAGE_VALUE;
                break;

            case "hard":
                levelMaxCount = FinalValue.HARD_MAX_COUNT_BALL;
                levelBonusValue = FinalValue.HARD_GAGE_VALUE;
                break;

            case "hell":
                levelMaxCount = FinalValue.HELL_MAX_COUNT_BALL;
                levelBonusValue = FinalValue.HELL_GAGE_VALUE;
                break;
        }
        Log.d(TAG, "Level Count >>> " + levelMaxCount);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (isTime) { // 추가하는 부분이면서 타이머가 다 된거면

            int colorR = random.nextInt(255);
            int colorG = random.nextInt(255);
            int colorB = random.nextInt(255);

            int x = random.nextInt(canvas.getWidth());
            int y = random.nextInt(canvas.getHeight());

            int radius = 0; // 너무 작으면 좀 그러니까 X40

            if(ShareData.getLevel().equals("hard")){
                radius = random.nextInt(8) * 40;
                if(radius == 120 || radius == 80 || radius == 40 || radius == 0){
                    radius = 160;
                }
            }else if(ShareData.getLevel().equals("normal")){
                radius = random.nextInt(6) * 40;
                if(radius == 40 || radius == 0){
                    radius = 80;
                }
            }else{
                radius = random.nextInt(5) * 40;
                if (radius == 0) { // 만약에 0이 들어가서 0이 되버리면
                    radius = 40; // 가장 작은 크기로
                }
            }

            valueCircle = new ValueCircle(x, y, radius);

            paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(Color.rgb(colorR, colorG, colorB));

            paintArray.add(paint); // 각 원이 그려질 좌표를 저장시킨다
            pointArray.add(valueCircle); // 각 원에 칠해질 페인트 객체를 저장시킨다

            if(!ShareData.getIsGameOver()){ // 게임이 아직 진행중이면서
                mainGame.setBallCount(paintArray.size());

                //최대 갯수로 지정한 볼의 카운터를 안 넘으면
                if (levelMaxCount != pointArray.size() && levelMaxCount != paintArray.size()) {

                    for (int i = 0; i < pointArray.size(); i++) { // 전체 저장되어져있는 리스트를 전부 다 그린다

                        valueCircle = pointArray.get(i);
                        paint = paintArray.get(i);
                        canvas.drawCircle(valueCircle.x, valueCircle.y, valueCircle.radius, paint);
                    }

                } else { //지정한 갯수를 넘어 섯으면 중단시킨다.
                    Log.d(TAG, "Game Finish");
                    isGameOver = true;
                    mainGame.setBallCount(levelMaxCount);
                    mainGame.finishGame();

                }
            }
        } else { // 시간은 안됬는데 사용자가 터치해서 지운거면
            isTime = false;

            for (int i = 0; i < pointArray.size(); i++) { // 삭제된 크기만큼 전체 다시 그리기
                valueCircle = pointArray.get(i);
                paint = paintArray.get(i);

                canvas.drawCircle(valueCircle.x, valueCircle.y, valueCircle.radius, paint);
            }
        }


    }

    /**
     * maingame 클래스 객체 얻어오기
     * new 하니까 변수도 다 새로생성되보려서 ..
     * @param mainGame
     */
    public void setMainGame(MainGame mainGame){
        this.mainGame = mainGame;
    }


    /**
     * 터치한 좌표가 몇번째 생성된 원인지 판단해주는 함수
     *
     * @param eventX 사용자가 터치한 X
     * @param eventY 사용자가 터치한 Y
     * @return 해당 원의 인덱스
     */
    private int checkCircle(int eventX, int eventY) {

        int checkIndex = -1;
        ValueCircle checkValue;

        for (int i = 0; i < pointArray.size(); i++) {
            checkValue = pointArray.get(i);

            // 원의 중심에서 사용자가 누른 좌표의 값이 반지름보다 작으면 원 안으로 판단
            if (Math.abs(checkValue.x - eventX) < checkValue.radius) {
                if (Math.abs(checkValue.y - eventY) < checkValue.radius) {
                    return i;
                }
            }
        }
        return checkIndex;
    }


    /**
     * 타이머가 완료되었을 때를 판단하는 setter
     *
     * @param isTime 완료여부
     */
    public void setIsTime(boolean isTime) {
        this.isTime = isTime;
    }

    /**
     * 보너스 게이지 시간에 모든 볼을 다 지우고
     * 보너스 볼 하나만 띄워주는 함수
     */
    public void removeAllBall(){

        pointArray.clear();
        paintArray.clear();

        mainGame.setBallCount(pointArray.size());

        Log.d(TAG, "Array Size  Point >> " + pointArray.size() + " PAint >>" + paintArray.size());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        if (!isGameOver) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    //터치한 곳의 인덱스 판단
                    getIndex = checkCircle((int) event.getX(), (int) event.getY());
                    break;

                case MotionEvent.ACTION_UP:
                    isTime = false;

                    if (getIndex == -1) { // 이상한 곳이면
                        return false; // 아무것도 안하고
                    } else { // 제데로된 원 안이면
                        if (pointArray.size() > 0) { //무언가 원이 리스트에 들어가있으면
                            valueCircle = pointArray.get(getIndex);

                            /**
                             * 반지름의 크기에 따라 원을 클릭해야 하는 횟수가 다르며
                             * 해당 횟수를 완료해서 가장 작아져야 원을 삭제한다.
                             */
                            if (valueCircle.radius == 320) {
                                valueCircle.radius = valueCircle.radius - 40;
                                pointArray.set(getIndex, valueCircle);
                                invalidate();

                                return false;
                            }else if (valueCircle.radius == 280) {
                                valueCircle.radius = valueCircle.radius - 40;
                                pointArray.set(getIndex, valueCircle);
                                invalidate();

                                return false;
                            }else  if (valueCircle.radius == 240) {
                                valueCircle.radius = valueCircle.radius - 40;
                                pointArray.set(getIndex, valueCircle);
                                invalidate();

                                return false;
                            }else if (valueCircle.radius == 200) {
                                valueCircle.radius = valueCircle.radius - 40;
                                pointArray.set(getIndex, valueCircle);
                                invalidate();

                                return false;
                            } else if (valueCircle.radius == 160) {
                                valueCircle.radius = valueCircle.radius - 40;
                                pointArray.set(getIndex, valueCircle);
                                invalidate();

                                return false;
                            } else if (valueCircle.radius == 120) {
                                valueCircle.radius = valueCircle.radius - 40;
                                pointArray.set(getIndex, valueCircle);
                                invalidate();

                                return false;
                            } else if (valueCircle.radius == 80) {
                                valueCircle.radius = valueCircle.radius - 40;
                                pointArray.set(getIndex, valueCircle);
                                invalidate();

                                return false;
                            } else if (valueCircle.radius == 40) {
                                pointArray.remove(getIndex); // 해당 인덱스 원을 지운다
                                paintArray.remove(getIndex);
                                mainGame.setBallCount(pointArray.size());
                                mainGame.setBonusGage(levelBonusValue);
                                invalidate();
                            }
                        }

                    }
                    break;
            }
        }

        return true; // 반환이 true 면 더이상 터치 이벤트를 발생시키지 않는다
    }


    /**
     * 원에 대한 정보를 가지고있는 클래스
     */
    class ValueCircle {

        int x = 0;
        int y = 0;
        int radius = 0;

        public ValueCircle(int x, int y, int radius) {
            this.x = x;
            this.y = y;
            this.radius = radius;
        }

    }
}
