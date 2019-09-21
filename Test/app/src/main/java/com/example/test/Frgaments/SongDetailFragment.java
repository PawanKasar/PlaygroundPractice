package com.example.test.Frgaments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.test.MainActivity;
import com.example.test.Models.Result;
import com.example.test.R;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class SongDetailFragment extends Fragment {

    /**
     * ImageView
     */
    ImageView imgv_artistImage;
    /**
     * TextView Album Name
     */
    TextView tv_albumName;
    /**
     * TextView Song Name
     */
    TextView tv_songsName;
    /**
     * TextView Song Release Name
     */
    TextView tv_songsReleaseDate;
    //
    Bundle bundle;
    //
    String imageUrl="", albumName="", songName="", songReleaseDate="";

    public SongDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_song_detail, container, false);

        getDataFromFragment();

        initViews(rootView);

        renderDataOnViews();

        return rootView;
    }

    private void getDataFromFragment(){
        if (getArguments().getString("SongData") != null){
            imageUrl = getArguments().getString("SongData");
            Log.e("SongDetailFragment","ImageUrl "+imageUrl);
            albumName = getArguments().getString("albumName");
            Log.e("SongDetailFragment","albumName "+albumName);
            songName = getArguments().getString("songName");
            Log.e("SongDetailFragment","songName "+songName);
            songReleaseDate = getArguments().getString("releaseDate");
            Log.e("SongDetailFragment","releaseDate "+songReleaseDate);
        }
    }

    private void initViews(View rootView){
        MainActivity.titleMain.setText("SongListDetails");

        imgv_artistImage = rootView.findViewById(R.id.imgv_artistImage);
        tv_albumName = rootView.findViewById(R.id.tv_albumName);
        tv_songsName = rootView.findViewById(R.id.tv_songsName);
        tv_songsReleaseDate = rootView.findViewById(R.id.tv_songsReleaseDate);
    }

    private void renderDataOnViews(){
        if (imageUrl != null && albumName != null && songName != null && songReleaseDate != null){

            Glide
                    .with(getContext())
                    .load(imageUrl)
                    .into(imgv_artistImage);

            tv_albumName.setText(albumName);
            tv_songsName.setText(songName);
            tv_songsReleaseDate.setText(songReleaseDate);
        }
    }

}
