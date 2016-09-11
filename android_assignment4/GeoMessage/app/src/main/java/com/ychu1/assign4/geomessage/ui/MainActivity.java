package com.ychu1.assign4.geomessage.ui;

import android.support.v4.app.Fragment;

import com.ychu1.assign4.geomessage.fragment.MainFragment;

public class MainActivity extends SingleFragmentActivity{

    @Override
    protected Fragment createFragment() {
        return new MainFragment();
    }
}
