package com.ychu1.artist.vocalartist.ui;

import android.support.v4.app.Fragment;

import com.ychu1.artist.vocalartist.fragment.SongFragment;
import com.ychu1.artist.vocalartist.fragment.VideoFragment;

/**
 */
public class VideoActivity extends SingleFragmentActivity{

    @Override
    protected Fragment createFragment() {
        return new VideoFragment();
    }
}
