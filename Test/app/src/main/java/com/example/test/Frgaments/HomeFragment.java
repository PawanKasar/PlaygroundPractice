package com.example.test.Frgaments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;
import com.example.test.Adapters.MjSongListAdapter;
import com.example.test.Models.MjSongsModel;
import com.example.test.Models.Result;
import com.example.test.R;
import com.example.test.RetroClient.RetrofitApiUtils;
import com.example.test.SubUrlInterfaces.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    /**
    * Retrofit Library Call
     */
    private ApiInterface iapiInterface;
    private RetrofitApiUtils retrofitApiUtils;
    /**
     * RecyclerView for display Data
     */
    private RecyclerView rv_songList;
    /**
     * Model Class
     */
    Result result;
    /**
     * ArrayList to store Result Model class
     */
    ArrayList<Result> songsresultArrayList;
    /**
     * Adapter Class support for RecyclerView
     */
    MjSongListAdapter mjSongListAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        initViews(rootView);

        return rootView;
    }

    private void initViews(View rootView){
        rv_songList = rootView.findViewById(R.id.rv_songList);

        requestServerToGetMjSonList();
    }

    private void requestServerToGetMjSonList(){

        iapiInterface = retrofitApiUtils.getAPIService();

        iapiInterface.getMjSongsListData().enqueue(new Callback<MjSongsModel>() {
            @Override
            public void onResponse(Call<MjSongsModel> call, Response<MjSongsModel> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){
                        ArrayList<Result> resultArrayList = response.body().getResults();

                        songsresultArrayList = new ArrayList<>();
                        for (int i=0; i<resultArrayList.size(); i++){
                            result = new Result();

                            result.setArtistName(resultArrayList.get(i).getArtistName());
                            result.setCollectionArtistName(resultArrayList.get(i).getCollectionArtistName());
                            result.setTrackName(resultArrayList.get(i).getTrackName());

                            songsresultArrayList.add(result);
                        }
                        setDataOnRecyclerView(songsresultArrayList);
                    }else {
                        Toast.makeText(getContext(),"Error",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getContext(),"No Response From Server",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MjSongsModel> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setDataOnRecyclerView(ArrayList<Result> resultArrayList){
        rv_songList.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rv_songList.setLayoutManager(layoutManager);
        mjSongListAdapter = new MjSongListAdapter(getContext(),resultArrayList);
        rv_songList.setAdapter(mjSongListAdapter);
        mjSongListAdapter.notifyDataSetChanged();
    }
}
