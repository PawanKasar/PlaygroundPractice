package com.example.test.Frgaments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;
import com.example.test.Adapters.MjSongListAdapter;
import com.example.test.MainActivity;
import com.example.test.Models.MjSongsModel;
import com.example.test.Models.Result;
import com.example.test.R;
import com.example.test.RetroClient.RetrofitApiUtils;
import com.example.test.SubUrlInterfaces.ApiInterface;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.Serializable;
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
    /**
     * Progress Dialog for delay Handle
     */
    ProgressDialog pd;
    /**
     * Created instance of SongDetailFragment
     */
    SongDetailFragment songDetailFragment;

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

        MainActivity.titleMain.setText("SongList");

        rv_songList = rootView.findViewById(R.id.rv_songList);

        pd = new ProgressDialog(getContext());
        pd.setMessage("Loading..Please Wait.!");
        pd.setCancelable(false);
        pd.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        pd.show();
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
                            Log.e("HomeFragment","resultArray "+resultArrayList.get(i).getArtistName());
                            result.setCollectionArtistName(resultArrayList.get(i).getCollectionArtistName());
                            result.setCollectionName(resultArrayList.get(i).getCollectionName());
                            result.setTrackName(resultArrayList.get(i).getTrackName());
                            result.setArtworkUrl30(resultArrayList.get(i).getArtworkUrl30());
                            result.setArtworkUrl60(resultArrayList.get(i).getArtworkUrl60());
                            result.setArtworkUrl100(resultArrayList.get(i).getArtworkUrl100());
                            result.setTrackCensoredName(resultArrayList.get(i).getTrackCensoredName());
                            result.setReleaseDate(resultArrayList.get(i).getReleaseDate());

                            songsresultArrayList.add(result);
                        }
                        setDataOnRecyclerView();
                        pd.dismiss();
                    }else {
                        Toast.makeText(getContext(),"Error",Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                }else {
                    Toast.makeText(getContext(),"No Response From Server",Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }

            @Override
            public void onFailure(Call<MjSongsModel> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }
        });
    }

    private void setDataOnRecyclerView(){
        rv_songList.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rv_songList.setLayoutManager(layoutManager);
        mjSongListAdapter = new MjSongListAdapter(getContext(),songsresultArrayList);
        rv_songList.setAdapter(mjSongListAdapter);
        mjSongListAdapter.notifyDataSetChanged();

        mjSongListAdapter.setOnItemClickListener(new MjSongListAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                openNewActivityOrFragment(position);
            }
        });
    }

    private void openNewActivityOrFragment(int position){
        songDetailFragment = new SongDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("SongData", songsresultArrayList.get(position).getArtworkUrl100());
        bundle.putString("albumName", songsresultArrayList.get(position).getCollectionName());
        bundle.putString("songName", songsresultArrayList.get(position).getTrackName());
        bundle.putString("releaseDate", songsresultArrayList.get(position).getReleaseDate());
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        songDetailFragment.setArguments(bundle);
        fragmentManager.beginTransaction()
                .replace(R.id.container,songDetailFragment)
                .commit();
    }
}
