package com.example.test.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.test.Models.Result;
import com.example.test.R;

import java.util.ArrayList;

public class MjSongListAdapter extends RecyclerView.Adapter<MjSongListAdapter.CustomViewHolder> {

    Context context;
    //
    ArrayList<Result> songsresultArrayList = new ArrayList<>();
    private static ClickListener clickListener;

    public MjSongListAdapter(Context context,ArrayList<Result> songsresultArrayList){
        this.context = context;
        this.songsresultArrayList = songsresultArrayList;
    }

    @NonNull
    @Override
    public MjSongListAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_song_layout, viewGroup, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MjSongListAdapter.CustomViewHolder customViewHolder, int i) {
        Log.e("MjSongListAdapter","ArrayList Data "+songsresultArrayList.get(i).toString());

        customViewHolder.tv_artistName.setText(songsresultArrayList.get(i).getArtistName());
        customViewHolder.tv_songName.setText(songsresultArrayList.get(i).getTrackName());
        customViewHolder.tv_collectionName.setText(songsresultArrayList.get(i).getCollectionName());
        Glide
                .with(context)
                .load(songsresultArrayList.get(i).getArtworkUrl100())
                .into(customViewHolder.imgv_artistImage);
    }

    @Override
    public int getItemCount() {
        return songsresultArrayList.size();
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        MjSongListAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imgv_artistImage;
        TextView tv_songName;
        TextView tv_artistName;
        TextView tv_collectionName;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            imgv_artistImage = itemView.findViewById(R.id.imgv_artistImage);
            tv_songName = itemView.findViewById(R.id.tv_songName);
            tv_artistName = itemView.findViewById(R.id.tv_artistName);
            tv_collectionName = itemView.findViewById(R.id.tv_collectionName);
        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(getAdapterPosition(), view);
            //Toast.makeText(context,"Hello",Toast.LENGTH_SHORT).show();
        }
    }
}
