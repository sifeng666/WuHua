package com.example.tang.wuhua;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tang.wuhua.Data.User;
import com.example.tang.wuhua.model.response.MomentResponse;
import com.example.tang.wuhua.net.helper.NetworkHelper;
import com.squareup.picasso.Picasso;

import net.datafans.android.common.widget.imageview.CommonImageView;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by root on 18-6-30.
 */

public class AlbumFragment extends Fragment {

    private User user;
    private TextView tvNickname;
    private TextView tvSignature;
    //private CommonImageView ivPortrait;
    private CircleImageView ivPortrait;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_header, container, false);
        tvNickname = v.findViewById(R.id.text_name_header);
        tvSignature = v.findViewById(R.id.text_sign_header);
        ivPortrait = v.findViewById(R.id.commonImg_icon_header);
        user = ((MainActivity) getActivity()).getUser();
        tvNickname.setText(user.getNickname());
        tvSignature.setText(user.getSignature());
        Picasso.with(getContext()).load(Constant.Value.BASE_URL+user.getPortrait()).into(ivPortrait);
        //ivPortrait.loadImage(Constant.Value.BASE_URL+user.getPortrait());
        return v;
    }

    void getAlbum(String userid) {
        NetworkHelper.getAllMomentsByUserId(userid, new Callback<MomentResponse>() {
            @Override
            public void onResponse(Call<MomentResponse> call, Response<MomentResponse> response) {

            }

            @Override
            public void onFailure(Call<MomentResponse> call, Throwable t) {

            }
        });
    }
}
