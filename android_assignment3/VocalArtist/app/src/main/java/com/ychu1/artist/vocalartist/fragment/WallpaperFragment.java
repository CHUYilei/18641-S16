package com.ychu1.artist.vocalartist.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.ychu1.artist.vocalartist.R;

/**
 * Created by ychu1 on 16/3/31.
 */
public class WallpaperFragment extends Fragment {
    private static int imageId;
    private Button btnNext;
    private static ImageView imageView;
    private Button btnBack;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.wallpaper_page, container, false);

        imageId = 1;
        imageView = (ImageView) v.findViewById(R.id.wallpaper_imageView);
        imageView.setImageResource(R.drawable.image1);

        initButtons(v);
        return v;
    }

    private void initButtons(View v){
        this.btnNext = (Button) v.findViewById(R.id.wallpaper_btnNext);
        this.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageId = (imageId + 1) % 4;
                switch (imageId) {
                    case 1:
                        imageView.setImageResource(R.drawable.image1);
                        break;
                    case 2:
                        imageView.setImageResource(R.drawable.image2);
                        break;
                    case 3:
                        imageView.setImageResource(R.drawable.image3);
                        break;
                    case 4:
                        imageView.setImageResource(R.drawable.image4);
                        break;
                }
            }
        });

        initBackButton(v);
    }

    private void initBackButton(View v){
        this.btnBack = (Button) v.findViewById(R.id.wallpaper_btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();   //should not start a new activity, otherwise native back arrow will malfunction (activity stack)
            }
        });
    }
}
