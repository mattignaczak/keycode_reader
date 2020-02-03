package com.app.esightmain.backbuttontesting;

import android.app.Instrumentation;
import android.content.Context;
import android.graphics.PointF;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MenuCustomLayout extends ConstraintLayout {
    private GestureDetector mDetector;
    private Instrumentation inst = new Instrumentation();
    private final ExecutorService mService = Executors.newSingleThreadExecutor();
    private ControllerInterface.MenuListener listener;
    private boolean longPress = false;
    private boolean longTouch = false;

    private static final String TAG = "MenuCustomLayout";


    public MenuCustomLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        mDetector = new GestureDetector(getContext(), new MyGestureListener());
    }

    public void setMenuListener(ControllerInterface.MenuListener l){
        listener = l;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDetector.onTouchEvent(event);
        if(longPress && (event.getAction() == MotionEvent.ACTION_UP)){
            mService.submit(new Runnable() {
                @Override
                public void run() {
                    KeyEvent event = new KeyEvent(downTime, SystemClock.uptimeMillis(), KeyEvent.ACTION_UP, KeyEvent.KEYCODE_ENTER, 0,0,0,0, KeyEvent.FLAG_CANCELED_LONG_PRESS);
                    inst.sendKeySync(event);
                }
            });
            longPress = false;
        }
        return true;
    }

    private long downTime = 0;
    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent event) {
            // don't return false here or else none of the other
            // gestures will work
            downTime = SystemClock.uptimeMillis();
            mService.submit(new Runnable() {
                @Override
                public void run() {
                    KeyEvent event = new KeyEvent(downTime, SystemClock.uptimeMillis(), KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER, 0);
                    inst.sendKeySync(event);
                }
            });
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            mService.submit(new Runnable() {
                @Override
                public void run() {
                    KeyEvent event = new KeyEvent(downTime, SystemClock.uptimeMillis(), KeyEvent.ACTION_UP, KeyEvent.KEYCODE_ENTER, 0);
                    inst.sendKeySync(event);
                }
            });

            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            mService.submit(new Runnable() {
                @Override
                public void run() {
                    KeyEvent event = new KeyEvent(downTime, SystemClock.uptimeMillis(), KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER, 1);
                    inst.sendKeySync(event);
                }
            });
            longPress = true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            mService.submit(new Runnable() {
                @Override
                public void run() {
                    KeyEvent event = new KeyEvent(downTime, SystemClock.uptimeMillis(), KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER, 0,0,0,0, KeyEvent.FLAG_CANCELED);
                    inst.sendKeySync(event);
                }
            });
            return true;
        }



    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY){
        mService.submit(new Runnable() {
            @Override
            public void run() {
                KeyEvent event = new KeyEvent(downTime, SystemClock.uptimeMillis(), KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER, 0,0,0,0, KeyEvent.FLAG_CANCELED);
                inst.sendKeySync(event);
            }
        });

        PointF delta = new PointF(event2.getX()-event1.getX(), event2.getY()-event1.getY());
        if(Math.abs(delta.y) > Math.abs(delta.x)) {
            if (delta.y > 0) {
                mService.submit(new Runnable() {
                    @Override
                    public void run() {
                        inst.sendKeyDownUpSync(KeyEvent.KEYCODE_DPAD_DOWN);
                    }
                });
            } else {
                mService.submit(new Runnable() {
                    @Override
                    public void run() {
                        inst.sendKeyDownUpSync(KeyEvent.KEYCODE_DPAD_UP);
                    }
                });
            }
        } else {
            if(delta.x > 0) {
                mService.submit(new Runnable() {
                    @Override
                    public void run() {
                        inst.sendKeyDownUpSync(KeyEvent.KEYCODE_DPAD_RIGHT);
                    }
                });
            } else {
                mService.submit(new Runnable() {
                    @Override
                    public void run() {
                        inst.sendKeyDownUpSync(KeyEvent.KEYCODE_DPAD_LEFT);
                    }
                });
            }
        }
        return true;
    }
}

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int keyCode = event.getKeyCode();

        if(event.getAction() == KeyEvent.ACTION_UP){

            switch (keyCode){
                case KeyEvent.KEYCODE_MENU:
                    if(listener!= null) listener.onMenuClose();
                    break;
                case KeyEvent.KEYCODE_PAIRING:
                    if(listener!= null) listener.onMenuClose();
            }

            longPress = false;

        }else if(event.getAction() == KeyEvent.ACTION_DOWN && event.isLongPress()){

            //make sure for each case, that it sets longpress to true

        }else{
            // event.getAction() == KeyEvent.ACTION_DOWN allows us to hold down a button and still receive events

            switch(keyCode){
                case KeyEvent.KEYCODE_VOLUME_UP:
                    if(listener != null) listener.onVolumeUpEvent();
                    break;
                case KeyEvent.KEYCODE_VOLUME_DOWN:
                    if(listener!= null) listener.onVolumeDownEvent();
                    break;

            }

        }

//
        return super.dispatchKeyEvent(event);
    }
}

