package com.ychu1.artist.vocalartist.fragment;

import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import com.ychu1.artist.vocalartist.R;
import com.ychu1.artist.vocalartist.exception.MyException;

/**
 * Created by caixiaomo on 16/3/31.
 */
public class VideoFragment extends Fragment {
    private VideoView videoView;
    private MediaRecorder player;
    private Button btnPlay1,btnPlay2,btnStop;
    private Button btnBack;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.video_page, container, false);
        videoView = (VideoView) v.findViewById(R.id.video_videoView);
        initButtons(v);
        return v;
    }

    private void initButtons(View v){
        this.btnPlay1 = (Button)v.findViewById(R.id.video_play1);
        this.btnPlay2 = (Button)v.findViewById(R.id.video_play2);
        this.btnStop = (Button)v.findViewById(R.id.video_stop);

        // allocate listener
        btnPlay1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playVideo(1);
            }
        });
        btnPlay2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playVideo(2);
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stop();
            }
        });
        initBackButton(v);
    }

    private void playVideo(int id){
        videoView.setMediaController(new MediaController(getActivity()));
        switch (id){
            case 1:
                videoView.setVideoURI(Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.video1));
                break;
            case 2:
                videoView.setVideoURI(Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.video2));
                break;
        }
        videoView.start();
    }

    private void stop(){
        videoView.stopPlayback();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.release();
        }
    }

    private void initBackButton(View v){
        this.btnBack = (Button) v.findViewById(R.id.video_btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();   //should not start a new activity, otherwise native back arrow will malfunction (activity stack)
            }
        });
    }
}
