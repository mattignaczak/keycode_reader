package com.app.esightmain.backbuttontesting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.app.esightmain.backbuttontesting.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ViewModel vm;


    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        vm = ViewModelProviders.of(this).get(ViewModel.class);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setVm(vm);
        binding.setLifecycleOwner(this);

        initUI();
    }


    @Override
    public void onBackPressed(){
        Log.i(TAG, "onBackPressed: u2 pressed");
        vm.u2Pressed();
    }



    void initUI() {

        Log.i(TAG, "initUI: initialized ui ");

        ((LiveCustomLayout)findViewById(R.id.focusable_layout)).setLiveListener(new ControllerInterface.LiveListener() {
            @Override
            public void onBackEvent() {

            }

            @Override
            public void onResetEvent() {

            }

            @Override
            public void onContextEvent() {

            }

            @Override
            public void onFunctionEvent() {

            }

            @Override
            public void onUpEvent() {

            }

            @Override
            public void onDownEvent() {

            }

            @Override
            public void onLeftEvent() {

            }

            @Override
            public void onRightEvent() {

            }

            @Override
            public void onCenterEvent() {

            }

            @Override
            public void onCenterLongEvent() {

            }

            @Override
            public void onSwipeUpEvent() {

            }

            @Override
            public void onSwipeDownEvent() {

            }

            @Override
            public void onSwipeLeftEvent() {

            }

            @Override
            public void onSwipeRightEvent() {

            }

            @Override
            public void onHmdFunctionEvent() {
                Toast.makeText(getApplicationContext(), "hmdFunctionEvent", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinderDownEvent() {

            }

            @Override
            public void onFinderUpEvent() {

            }

            @Override
            public void onLauncherEvent() {

            }

            @Override
            public void onVolumeUpEvent() {

            }

            @Override
            public void onVolumeDownEvent() {

            }

            @Override
            public void onHmdContextEvent() {
                Toast.makeText(getApplicationContext(), "hmdContextEvent", Toast.LENGTH_SHORT).show();
            }
        });



    }
}
