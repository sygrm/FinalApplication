package com.example.finalapplication.ui.all;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.finalapplication.R;
import com.example.finalapplication.ui.MyAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class AllViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private ArrayList<HashMap<String, String>> listItems;
    private ListView listview;
    public AllViewModel() {

    }
    public LiveData<String> getText() {
        return mText;
    }
}