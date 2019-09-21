package com.example.test.SubUrlInterfaces;

import com.example.test.Models.MjSongsModel;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.ArrayList;

public interface ApiInterface {

    @GET("search?term=Michael+jackson")
    Call<MjSongsModel> getMjSongsListData();
}
