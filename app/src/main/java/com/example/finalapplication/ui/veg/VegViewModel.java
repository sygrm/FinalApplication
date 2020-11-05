package com.example.finalapplication.ui.veg;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class VegViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public VegViewModel() {

    }

    public LiveData<String> getText() {
        return mText;
    }
}