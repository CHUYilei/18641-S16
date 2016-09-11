package com.ychu1.artist.vocalartist.ui;

import android.support.v4.app.Fragment;

import com.ychu1.artist.vocalartist.fragment.EmailFragment;
import com.ychu1.artist.vocalartist.fragment.VideoFragment;

/**
 */
public class EmailActivity extends SingleFragmentActivity{

    @Override
    protected Fragment createFragment() {
        return new EmailFragment();
    }


}
