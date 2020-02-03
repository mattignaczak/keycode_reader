//package com.app.esightmain.backbuttontesting;
//
//import android.content.Context;
//import android.util.AttributeSet;
//import android.util.Log;
//import android.view.KeyEvent;
//import android.view.MotionEvent;
//import android.view.VelocityTracker;
//
//import androidx.appcompat.widget.LinearLayoutCompat;
//
//public class FocusableLayout extends LinearLayoutCompat {
//    final static String TAG = "ControllerInterface";
//    boolean longPressTrigger = false;
//    boolean longPressCenter = false;
//    boolean longPressBack = false;
//    private VelocityTracker velocityTracker = null;
//    private static float init_x = 0;
//    private static float init_y = 0;
//    private final float threshold = 100;
//
//    ControllerInterface controllerInterface;
//
//    public FocusableLayout(Context context) {
//        super(context);
//    }
//
//    public FocusableLayout(Context context, AttributeSet attrs) {
//        super(context, attrs);
//    }
//
//    public void setControllerInterface(ControllerInterface controllerInterface) {
//        this.controllerInterface = controllerInterface;
//    }
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if(keyCode == KeyEvent.KEYCODE_BACK ||
//                keyCode == KeyEvent.KEYCODE_ENTER ||
//                keyCode == KeyEvent.KEYCODE_BUTTON_3 ) {
//            event.startTracking();
//        }
//        return true;
//    }
//    @Override
//    public boolean onKeyUp(int keyCode, KeyEvent event) {
//        Log.i(TAG, "onKeyUp:" + event);
//        boolean ret = true;
//        switch (keyCode) {
//            case KeyEvent.KEYCODE_PAIRING:
//                Log.i(TAG, "onKeyUp: hit keycode pairing");
//                controllerInterface.u2Pressed();
//                break;
//            case KeyEvent.KEYCODE_BACK:
//                if(longPressBack){
//                    controllerInterface.u2LongPressStopped();
//                    longPressBack = false;
//                }else {
//                    controllerInterface.u2Pressed();
//                    Log.i(TAG, "onKeyUp: u2 pressed");
//                }
//                break;
//            case KeyEvent.KEYCODE_NUMPAD_COMMA:
//                controllerInterface.u1Pressed();
//                break;
//            case KeyEvent.KEYCODE_F1:
//                if(longPressTrigger) {
//                    controllerInterface.u3LongPressStopped();
//                    longPressTrigger = false;
//                }else {
//                    controllerInterface.u3Pressed();
//                }
//                break;
//            case KeyEvent.KEYCODE_VOLUME_UP:
//                controllerInterface.u4Pressed();
//                break;
//            case KeyEvent.KEYCODE_VOLUME_DOWN:
//                controllerInterface.u5Pressed();
//                break;
//            case KeyEvent.KEYCODE_ENTER:
//                if(longPressCenter){
//                    controllerInterface.u6CenterLongPressStopped();
//                    longPressCenter = false;
//                }else {
//                    controllerInterface.u6CenterPressed();
//                }
//                break;
//            case KeyEvent.KEYCODE_DPAD_DOWN:
//                controllerInterface.u6DownPressed();
//                break;
//            case KeyEvent.KEYCODE_DPAD_UP:
//                controllerInterface.u6UpPressed();
//                break;
//            case KeyEvent.KEYCODE_DPAD_RIGHT:
//                controllerInterface.u6RightPressed();
//                break;
//            case KeyEvent.KEYCODE_DPAD_LEFT:
//                controllerInterface.u6LeftPressed();
//                break;
//            case KeyEvent.KEYCODE_HELP:
//                controllerInterface.u4LongPressStarted();
//                break;
//            case KeyEvent.KEYCODE_MENU:
//                controllerInterface.u5LongPressStarted();
//                break;
//
//            default:
//                ret = true;
//                break;
//
//        }
//        return ret;
//    }
//
//
//
//    @Override
//    public boolean onKeyLongPress(int keyCode, KeyEvent event){
//        switch (keyCode) {
//
//            case KeyEvent.KEYCODE_BUTTON_3:
//                controllerInterface.u3LongPressStarted();
//                longPressTrigger = true;
//                break;
//
//            case KeyEvent.KEYCODE_ENTER:
//                controllerInterface.u6CenterLongPressStarted();
//                longPressCenter = true;
//                break;
//            case KeyEvent.KEYCODE_BACK:
//                controllerInterface.u2LongPressStarted();
//                longPressBack = true;
//                break;
//
//        }
//        return true;
//    }
//
//   /* @Override
//    public boolean dispatchKeyEvent(KeyEvent event) {
//        int keyCode = event.getAction();
//        if(event.isLongPress()){
//            switch (keyCode) {
//
//                case KeyEvent.KEYCODE_BUTTON_3:
//                    controllerInterface.u3LongPressStarted();
//                    longPressTrigger = true;
//                    break;
//
//                case KeyEvent.KEYCODE_ENTER:
//                    controllerInterface.u6CenterLongPressStarted();
//                    longPressCenter = true;
//                    break;
//                case KeyEvent.KEYCODE_BACK:
//                    controllerInterface.u2LongPressStarted();
//                    longPressBack = true;
//                    break;
//
//            }
//        }
//        if(event.getAction()==KeyEvent.ACTION_UP){
//            switch (keyCode) {
//                case KeyEvent.KEYCODE_BACK:
//                    if(longPressBack){
//                        controllerInterface.u2LongPressStopped();
//                        longPressBack = false;
//                    }else {
//                        controllerInterface.u2Pressed();
//                    }
//                    break;
//                case KeyEvent.KEYCODE_BUTTON_A:
//                    controllerInterface.u1Pressed();
//                    break;
//                case KeyEvent.KEYCODE_BUTTON_3:
//                    if(longPressTrigger) {
//                        controllerInterface.u3LongPressStopped();
//                        longPressTrigger = false;
//                    }else {
//                        controllerInterface.u3Pressed();
//                    }
//                    break;
//                case KeyEvent.KEYCODE_VOLUME_UP:
//                    //cameraViewModel.setFocusModeToManual();
//                    break;
//                case KeyEvent.KEYCODE_VOLUME_DOWN:
//                    //cameraViewModel.setFocusModeToAuto();
//                    break;
//                case KeyEvent.KEYCODE_ENTER:
//                    if(longPressCenter){
//                        controllerInterface.u6CenterLongPressStopped();
//                        longPressCenter = false;
//                    }else {
//                        controllerInterface.u6CenterPressed();
//                    }
//                    break;
//                case KeyEvent.KEYCODE_DPAD_DOWN:
//                    controllerInterface.u6DownPressed();
//                    break;
//                case KeyEvent.KEYCODE_DPAD_UP:
//                    controllerInterface.u6UpPressed();
//                    break;
//                case KeyEvent.KEYCODE_DPAD_RIGHT:
//                    controllerInterface.u6RightPressed();
//                    break;
//                case KeyEvent.KEYCODE_DPAD_LEFT:
//                    controllerInterface.u6LeftPressed();
//                    break;
//                default:
//                    break;
//
//            }
//        }
//        if(event.getAction()==KeyEvent.ACTION_DOWN){
//            if(keyCode == KeyEvent.KEYCODE_BACK ||
//                    keyCode == KeyEvent.KEYCODE_ENTER ||
//                    keyCode == KeyEvent.KEYCODE_BUTTON_3 ) {
//                event.startTracking();
//            }
//        }
//
//        return super.dispatchKeyEvent(event);
//    }*/
//
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent event) {
//
//        switch(event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//
//                if(velocityTracker == null) {
//                    velocityTracker = VelocityTracker.obtain();
//                } else {
//                    velocityTracker.clear();
//                }
//
//                velocityTracker.addMovement(event);
//                init_x = event.getX();
//                init_y = event.getY();
//                break;
//            case MotionEvent.ACTION_MOVE:
//                velocityTracker.addMovement(event);
//
//                float delta_x = event.getX() - init_x;
//                float delta_y = event.getY() - init_y;
//                if(Math.abs(delta_y) > threshold || Math.abs(delta_x) > threshold) {
//                    if (Math.abs(delta_y) > (2 * Math.abs(delta_x))) {
//                        velocityTracker.computeCurrentVelocity(1);
//                        Log.d(TAG,"Velocity Y" + velocityTracker.getYVelocity());
//
//                        controllerInterface.u6SwipeVertical(delta_y,velocityTracker.getYVelocity());
//
//                        init_x = event.getX();
//                        init_y = event.getY();
//                    } else if (Math.abs(delta_x) > (2 * Math.abs(delta_y))) {
//                        velocityTracker.computeCurrentVelocity(1);
//                        controllerInterface.u6SwipeHorizontal(delta_x,velocityTracker.getXVelocity());
//                        Log.d(TAG,"Velocity X" + velocityTracker.getXVelocity());
//                        init_x = event.getX();
//                        init_y = event.getY();
//                    }
//                }
//                break;
//            case MotionEvent.ACTION_UP:
//            case MotionEvent.ACTION_CANCEL:
//                velocityTracker.recycle();
//                velocityTracker = null;
//                break;
//        }
//        return true;
//    }
//
//
//}
