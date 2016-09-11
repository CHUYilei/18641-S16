package com.ychu1.artist.vocalartist.ui;

import android.support.v4.app.Fragment;

import com.ychu1.artist.vocalartist.fragment.MainFragment;

/**
 */
public class MainActivity extends SingleFragmentActivity{

    @Override
    protected Fragment createFragment() {
        return new MainFragment();
    }
}
