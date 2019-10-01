package com.example.myapplication;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.HashMap;
import java.util.Stack;

public class MainActivityViewModel extends ViewModel {

    private MutableLiveData<HashMap<String, Stack<Fragment>>> mStacksLiveData = new MutableLiveData<>();

    private HashMap<String, Stack<Fragment>> mStacks;
    public static final String TAB_HOME  = "tab_home";
    public static final String TAB_DASHBOARD  = "tab_dashboard";
    public static final String TAB_NOTIFICATIONS  = "tab_notifications";


    public MutableLiveData<HashMap<String, Stack<Fragment>>> getmStacksLiveData() {
        mStacksLiveData.setValue(mStacks);

        return mStacksLiveData;
    }

    public MainActivityViewModel() {
        mStacks = new HashMap<String, Stack<Fragment>>();
        mStacks.put(TAB_HOME, new Stack<Fragment>());
        mStacks.put(TAB_DASHBOARD, new Stack<Fragment>());
        mStacks.put(TAB_NOTIFICATIONS, new Stack<Fragment>());
    }

    public void push(String tag, Fragment fragment){
        mStacks.get(tag).push(fragment);
        mStacksLiveData.setValue(mStacks);

    }


    public void pop(String mCurrentTab){
        mStacks.get(mCurrentTab).pop();
        mStacksLiveData.setValue(mStacks);


    }

}

