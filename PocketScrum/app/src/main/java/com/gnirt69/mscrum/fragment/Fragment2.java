package com.gnirt69.mscrum.fragment;/**
 * Created by NgocTri on 10/18/2015.
 */

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gnirt69.mscrum.R;

public class Fragment2 extends Fragment {
    public Fragment2() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment2, container, false);
        return rootView;
    }
}
