package com.ychu1.artist.vocalartist.ui;

import android.support.v4.app.Fragment;

import com.ychu1.artist.vocalartist.fragment.VideoFragment;
import com.ychu1.artist.vocalartist.fragment.WallpaperFragment;

/**
 */
public class WallpaperActivity extends SingleFragmentActivity{

    @Override
    protected Fragment createFragment() {
        return new WallpaperFragment();
    }
}
