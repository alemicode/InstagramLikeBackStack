package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.myapplication.fragment1.FragmentA;
import com.example.myapplication.fragment2.FragmentB1;
import com.example.myapplication.fragment3.FragmentC1;

import java.util.HashMap;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    private MainActivityViewModel viewModel;
    private RelativeLayout rtl_1,rtl_2,rtl_3;
    private MutableLiveData<HashMap<String, Stack<Fragment>>> mStacksLiveData = new MutableLiveData<>();

    private HashMap<String, Stack<Fragment>> mStacks;
    public static final String TAB_HOME  = "tab_home";
    public static final String TAB_DASHBOARD  = "tab_dashboard";
    public static final String TAB_NOTIFICATIONS  = "tab_notifications";

    private String mCurrentTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rtl_1 = findViewById(R.id.rtl_1);
        rtl_2 = findViewById(R.id.rtl_2);
        rtl_3 = findViewById(R.id.rtl_3);

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        mStacksLiveData = viewModel.getmStacksLiveData();
        mStacksLiveData.observe(this, new Observer<HashMap<String, Stack<Fragment>>>() {
            @Override
            public void onChanged(HashMap<String, Stack<Fragment>> stringStackHashMap) {
                mStacks = stringStackHashMap;
            }
        });


        rtl_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTab(TAB_HOME);
            }
        });
        rtl_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTab(TAB_DASHBOARD);
            }
        });
        rtl_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTab(TAB_NOTIFICATIONS);
            }
        });



    }

    private void gotoFragment(Fragment selectedFragment)
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content, selectedFragment);
        fragmentTransaction.commit();
    }



    private void selectedTab(String tabId)
    {
        //
        mCurrentTab = tabId;

        if(mStacks.get(tabId).size() == 0){
            /*
             *    First time this tab is selected. So add first fragment of that tab.
             *    Dont need animation, so that argument is false.
             *    We are adding a new fragment which is not present in stack. So add to stack is true.
             */
            if(tabId.equals(TAB_HOME)){
                pushFragments(tabId, new FragmentA(),true);
            }else if(tabId.equals(TAB_DASHBOARD)){
                pushFragments(tabId, new FragmentB1(),true);
            }else if(tabId.equals(TAB_NOTIFICATIONS)){
                pushFragments(tabId, new FragmentC1(),true);
            }
        }else {
            /*
             *    We are switching tabs, and target tab is already has atleast one fragment.
             *    No need of animation, no need of stack pushing. Just show the target fragment
             */
            pushFragments(tabId, mStacks.get(tabId).lastElement(),false);
        }
    }

    public void pushFragments(String tag, Fragment fragment, boolean shouldAdd){
        if(shouldAdd)
           // mStacks.get(tag).push(fragment);
            viewModel.push(tag,fragment);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.contentd, fragment);
        ft.commit();

    }

    public void popFragments(){
        /*
         *    Select the second last fragment in current tab's stack..
         *    which will be shown after the fragment transaction given below
         */
        Fragment fragment = mStacks.get(mCurrentTab).elementAt(mStacks.get(mCurrentTab).size() - 2);

        /*pop current fragment from stack.. */
//        mStacks.get(mCurrentTab).pop();

        viewModel.pop(mCurrentTab);
        /* We have the target fragment in hand.. Just show it.. Show a standard navigation animation*/
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.contentd, fragment);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        if(mStacks.get(mCurrentTab).size() == 1){
            // We are already showing first fragment of current tab, so when back pressed, we will finish this activity..
            finish();
            return;
        }

        /* Goto previous fragment in navigation stack of this tab */
        popFragments();
    }
}
