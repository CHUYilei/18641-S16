package com.ychu1.artist.vocalartist.ui;

import android.support.v4.app.Fragment;

import com.ychu1.artist.vocalartist.fragment.MainFragment;
import com.ychu1.artist.vocalartist.fragment.SongFragment;

/**
 */
public class SongActivity extends SingleFragmentActivity{

    @Override
    protected Fragment createFragment() {
        return new SongFragment();
    }
}
