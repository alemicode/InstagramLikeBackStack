package com.example.myapplication.fragment2;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.fragment1.FragmentA2;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentB3 extends Fragment {


    public FragmentB3() {
        // Required empty public constructor
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_b3, container, false);
        Button gotoNextFragment = (Button) view.findViewById(R.id.btn);

        gotoNextFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).pushFragments(MainActivity.TAB_DASHBOARD, new FragmentA2(),true);
            }
        });
        return view;
    }

}
