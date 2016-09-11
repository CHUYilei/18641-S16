package com.ychu1.artist.vocalartist.fragment;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ychu1.artist.vocalartist.R;
import com.ychu1.artist.vocalartist.exception.MyException;

/**
 * Created by ychu1 on 16/3/31.
 */
public class SongFragment extends Fragment {
    private static MediaPlayer player;
    private static int pausePos;
    private Button btnPlay1,btnPlay2,btnRestart,btnPause;
    private Button btnBack;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.song_page, container, false);

        initButtons(v);

        return v;
    }

    private void initButtons(View v){
        this.btnPlay1 = (Button)v.findViewById(R.id.song_play1);
        this.btnPlay2 = (Button)v.findViewById(R.id.song_play2);
        this.btnRestart = (Button)v.findViewById(R.id.song_restart);
        this.btnPause = (Button)v.findViewById(R.id.song_pause);

        // allocate listener
        btnPlay1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playSong(1);
            }
        });

        btnPlay2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playSong(2);
            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(player == null) throw new MyException(MyException.ERROR_NULL_PLAYER);
                    pausePos = player.getCurrentPosition();
                    player.pause();
                } catch (MyException e) {
                    e.handle(getActivity());
                }
            }
        });

        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (player == null) throw new MyException(MyException.ERROR_NULL_PLAYER);
                    player.seekTo(pausePos);
                    player.start();
                } catch (MyException e) {
                    e.handle(getActivity());
                }
            }
        });

        initBackButton(v);
    }

    private void playSong(int id){
        stopCurrentPlayer();
        switch (id){
            case 1:
                player = MediaPlayer.create(getActivity(),R.raw.song1);
                break;
            case 2:
                player = MediaPlayer.create(getActivity(),R.raw.song2);
                break;
        }
        player.start();
    }

    private void stopCurrentPlayer(){
        if(player != null){
            player.release();
        }
    }

    private void initBackButton(View v){
        this.btnBack = (Button) v.findViewById(R.id.song_btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();   //should not start a new activity, otherwise native back arrow will malfunction (activity stack)
            }
        });
    }
}
