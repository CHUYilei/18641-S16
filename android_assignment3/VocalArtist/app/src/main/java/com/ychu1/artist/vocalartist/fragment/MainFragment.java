package com.ychu1.artist.vocalartist.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ychu1.artist.vocalartist.R;
import com.ychu1.artist.vocalartist.ui.EmailActivity;
import com.ychu1.artist.vocalartist.ui.SongActivity;
import com.ychu1.artist.vocalartist.ui.VideoActivity;
import com.ychu1.artist.vocalartist.ui.WallpaperActivity;

/**
 */
public class MainFragment extends Fragment {
    private Button btnSong,btnVideo,btnEmail,btnWallpaper;
    private TextView linkWebsite,linkSocialNetwork;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.main_page, container, false);

        initButtons(v);
        initLinks(v);

        return v;
    }

    private void initButtons(View v){
        this.btnSong = (Button)v.findViewById(R.id.main_btnSong);
        this.btnVideo = (Button)v.findViewById(R.id.main_btnVideo);
        this.btnEmail = (Button)v.findViewById(R.id.main_btnEmail);
        this.btnWallpaper = (Button)v.findViewById(R.id.main_btnWallpaper);

        //allocate listeners
        btnSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SongActivity.class);
                startActivity(intent);
            }
        });

        btnVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), VideoActivity.class);
                startActivity(intent);
            }
        });

        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EmailActivity.class);
                startActivity(intent);
            }
        });

        btnWallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WallpaperActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initLinks(View v){
        this.linkWebsite= (TextView)v.findViewById(R.id.main_link_website);
        this.linkSocialNetwork = (TextView)v.findViewById(R.id.main_links);
        linkWebsite.setMovementMethod(LinkMovementMethod.getInstance());
        linkSocialNetwork.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
