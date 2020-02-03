package com.app.esightmain.backbuttontesting;

import android.app.Instrumentation;
import android.content.Context;
import android.graphics.PointF;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class LiveCustomLayout extends ConstraintLayout {
    private GestureDetector mDetector;
    private Instrumentation inst = new Instrumentation();
    private final ExecutorService mService = Executors.newSingleThreadExecutor();
    private ControllerInterface.LiveListener listener;
    private AtomicInteger currContextState = null;
    private boolean longTouch = false;
    private boolean longPress = false;

    private static final String TAG = "LiveCustomLayout";

    private final int scrollDistanceThreshold = 10, longPressThreshold = 500, velocityThreshold = 0, INITIAL_DISTANCE_THRESHOLD = 20;
    private final int MIN_DISTANCE_LONGPRESS = 150;
    private long downTime = 0;
    private int slideState = 0;

    private float downX, downY =0;

    public LiveCustomLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        mDetector = new GestureDetector(getContext(), new MyGestureListener());
        mDetector.setIsLongpressEnabled(false);


    }

    public void setLiveListener(ControllerInterface.LiveListener l){
        listener = l;
    }

    public void setContextState(AtomicInteger currentState){
        currContextState = currentState;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override
    public boolean onTouchEvent(final MotionEvent event) {

        if((event.getAction() == MotionEvent.ACTION_UP)){

            longTouch = false;
            slideState = 0;

        }

        return mDetector.onTouchEvent(event);
    }

    
    class MyGestureListener implements GestureDetector.OnGestureListener {

        @Override
        public boolean onDown(MotionEvent event) {
            // don't return false here or else none of the other
            // gestures will work
            Log.i(TAG, "onDown: ");
            slideState = 0;
            longTouch = false;

            downTime = SystemClock.uptimeMillis();
            return true;
        }

        @Override
        public void onShowPress(MotionEvent motionEvent) {

        }


        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            mService.submit(new Runnable() {
                @Override
                public void run() {
                    inst.sendKeyDownUpSync(KeyEvent.KEYCODE_ENTER);
                    Log.i(TAG, "run: single tap confirmed");
                }
            });

            return true;
        }



        @Override
        public void onLongPress(MotionEvent e){

            Log.i(TAG, "onLongPress: onLongPress");
            }



        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                final float distanceX, final float distanceY) {


            float deltaX = e2.getX() - e1.getX();
            float deltaY = e2.getY() - e1.getY();
            Log.i(TAG, "onScroll: deltaX = " + deltaX);
            Log.i(TAG, "onScroll: deltaY = " + deltaY);
            Log.i(TAG, "onScroll: slideState" + slideState);


            if((Math.abs(deltaX) < MIN_DISTANCE_LONGPRESS) && (Math.abs(deltaY) < MIN_DISTANCE_LONGPRESS) && (slideState != 2 && slideState != 3)) {
                if(Math.abs(distanceX) < 20 && Math.abs(distanceY) < 20){
                    slideState  = 1;
                    if (e2.getEventTime() - downTime > longPressThreshold && !longTouch) {
                        if(listener != null) listener.onCenterLongEvent();
                        longTouch = true;
                        return true;

                    }
                    return true;
                }

            }
            int state = 0;
            if (currContextState != null) {
                state = currContextState.get();
            }

            if (state != 1 && slideState != 1 && (Math.abs(deltaX) > INITIAL_DISTANCE_THRESHOLD || Math.abs(deltaY) > INITIAL_DISTANCE_THRESHOLD)) {

                    if (Math.abs(e2.getY() - e1.getY()) > Math.abs(e2.getX() - e1.getX()) && (slideState == 0 || slideState == 2)) {

                        slideState = 2;
                        if (distanceY > scrollDistanceThreshold) { //up
                            if (listener != null) listener.onSwipeUpEvent();

                        } else if (distanceY < -scrollDistanceThreshold) { //down
                            if (listener != null) listener.onSwipeDownEvent();
                        }

                    } else if(slideState == 0 || slideState == 3) {
                        slideState = 3;
                        if (distanceX > scrollDistanceThreshold) { // left
                            if (listener != null) listener.onSwipeLeftEvent();


                        } else if (distanceX < -scrollDistanceThreshold) { //right
                            if (listener != null) listener.onSwipeRightEvent();
                        }
                    }

                }
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            int state = 0;
            if(currContextState != null){
                state = currContextState.get();
            }

            if(state == 1){
                PointF delta = new PointF(e2.getX()-e1.getX(), e2.getY()-e1.getY());
                if(Math.abs(delta.y) > Math.abs(delta.x)) {
                    if (velocityY > velocityThreshold) {
                        if(listener != null) listener.onSwipeUpEvent();
                    } else {
                        if(listener != null) listener.onSwipeDownEvent();

                    }
                } else {
                    if(velocityX > velocityThreshold) {
                        if(listener != null) listener.onSwipeRightEvent();
                    } else {
                        if(listener != null) listener.onSwipeLeftEvent();
                    }
                }
            }
            return true;
        }
    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int keyCode = event.getKeyCode();
        Log.i("LiveCustomLayout","" + event.getKeyCode() + "," + event.getAction() + ","+event.getRepeatCount());
        Log.i(TAG, "dispatchKeyEvent: " + event);


             if (event.getAction() == KeyEvent.ACTION_UP){
                switch (keyCode){

                    case KeyEvent.KEYCODE_F2:
                        if(listener != null) listener.onFinderUpEvent();
                        break;
                    case KeyEvent.KEYCODE_MENU:

                        if(listener != null && !longPress) listener.onContextEvent();
                        break;
                    case KeyEvent.KEYCODE_ENTER:
                        if(listener != null && !longPress) listener.onCenterEvent();
                        break;
                    case KeyEvent.KEYCODE_PAIRING:
                        if(listener!= null && !longPress) listener.onHmdContextEvent();
                        break;


                }
                longPress = false;
            }



            else if(event.getAction() == KeyEvent.ACTION_DOWN && event.isLongPress()){
                switch (keyCode){

                    case KeyEvent.KEYCODE_MENU:
                        if(listener != null) listener.onFunctionEvent();
                        Log.i(TAG, "dispatchKeyEvent: KEYCODE MENU LONGPRESS");
                        longPress = true;
                        break;
                    case KeyEvent.KEYCODE_ENTER:
                        if(listener != null) listener.onCenterLongEvent();
                        Log.i(TAG, "dispatchKeyEvent: Center long press" + event.toString());
                        longPress = true;
                        break;
                    case KeyEvent.KEYCODE_PAIRING:
                        if(listener != null) listener.onHmdFunctionEvent();
                        longPress = true;
                        Log.i(TAG, "dispatchKeyEvent: KEYCODE PAIRING LONGPRESS");
                        break;

                }
                return true;
            }


        else {
            switch(keyCode){
                case KeyEvent.KEYCODE_DPAD_UP:
                    if(listener != null) listener.onUpEvent();
                    break;
                case KeyEvent.KEYCODE_DPAD_DOWN:
                    if(listener != null) listener.onDownEvent();
                    break;
                case KeyEvent.KEYCODE_DPAD_LEFT:
                    if(listener != null) listener.onLeftEvent();
                    break;
                case KeyEvent.KEYCODE_DPAD_RIGHT:
                    if(listener != null) listener.onRightEvent();
                    break;
                case KeyEvent.KEYCODE_BACK:
                    if(listener!= null) listener.onBackEvent();
                    break;

                case KeyEvent.KEYCODE_F2:
                    if(listener != null) listener.onFinderDownEvent();
                    break;
                case KeyEvent.KEYCODE_F3:
                    if(listener != null) listener.onLauncherEvent();
                    break;
                case KeyEvent.KEYCODE_F1:
                    if(listener != null) listener.onResetEvent();
                    Log.i(TAG, "dispatchKeyEvent: onResetEvent");
                    break;

                case KeyEvent.KEYCODE_VOLUME_UP:
                    if(listener != null) listener.onVolumeUpEvent();
                    break;
                case KeyEvent.KEYCODE_VOLUME_DOWN:
                    if(listener != null) listener.onVolumeDownEvent();
                    break;
            }

        }


        return true;
    }
}
