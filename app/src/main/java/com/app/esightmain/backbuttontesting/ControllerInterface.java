package com.app.esightmain.backbuttontesting;

public class ControllerInterface {

    public interface MenuListener {

        void onMenuClose();
        void onVolumeUpEvent();
        void onVolumeDownEvent();
    }

    public interface LiveListener {

        void onBackEvent();
        void onResetEvent();
        void onContextEvent();
        void onFunctionEvent();
        void onUpEvent();
        void onDownEvent();
        void onLeftEvent();
        void onRightEvent();
        void onCenterEvent();
        void onCenterLongEvent();
        void onSwipeUpEvent();
        void onSwipeDownEvent();
        void onSwipeLeftEvent();
        void onSwipeRightEvent();
        void onHmdFunctionEvent();
        void onFinderDownEvent();
        void onFinderUpEvent();
        void onLauncherEvent();
        void onVolumeUpEvent();
        void onVolumeDownEvent();
        void onHmdContextEvent();
    }


}
