package com.app.esightmain.backbuttontesting;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class ViewModel extends AndroidViewModel {

    int i = 1;


    public ViewModel(@NonNull Application application) {
        super(application);
    }

    MutableLiveData<String> backButtonText = new MutableLiveData<>("No press yet");


    public MutableLiveData<String> getBackButtonText() {
        return backButtonText;
    }

    public void setBackButtonText(String backButtonText) {
        this.backButtonText.setValue(backButtonText);
    }

    public void u2Pressed(){
        setBackButtonText(Integer.toString(i));
        i++;

    }
}
