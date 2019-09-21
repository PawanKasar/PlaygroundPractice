package com.example.test.RetroClient;

import android.app.ProgressDialog;
import android.content.Context;
import com.example.test.BaseUrlClient.ApiClient;
import com.example.test.SubUrlInterfaces.ApiInterface;
import com.example.test.Utilities.URLs;

public class RetrofitApiUtils {
    public ProgressDialog pDialog;
    Context activity;

    public RetrofitApiUtils(Context activity) {
        this.activity = activity;
        this.pDialog = new ProgressDialog(activity);
    }

    public static ApiInterface getAPIService() {
            return ApiClient.getClient(URLs.BaseUrl).create(ApiInterface.class);
    }
}
