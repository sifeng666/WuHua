package com.example.tang.wuhua;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tang.wuhua.model.response.MomentResponse;
import com.example.tang.wuhua.net.helper.NetworkHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by root on 18-6-30.
 */

public class AlbumFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_header, container, false);
        return v;
    }

    void getAlbum(String userid) {
        NetworkHelper.getAllCommentsByUserId(userid, new Callback<MomentResponse>() {
            @Override
            public void onResponse(Call<MomentResponse> call, Response<MomentResponse> response) {

            }

            @Override
            public void onFailure(Call<MomentResponse> call, Throwable t) {

            }
        });
    }
}
