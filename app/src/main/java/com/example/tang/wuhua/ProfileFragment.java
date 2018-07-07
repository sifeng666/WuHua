package com.example.tang.wuhua;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tang.wuhua.Data.User;
import com.example.tang.wuhua.model.response.BaseResponse;
import com.example.tang.wuhua.model.response.UserResponse;
import com.example.tang.wuhua.net.helper.NetworkHelper;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by root on 18-6-30.
 */

public class ProfileFragment extends Fragment {

    private User user;
    private TextView tvNickName;
    private TextView tvPhone;
    private TextView tvSignature;
    private CircleImageView ivPortrait;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_profile, container, false);
        user = ((MainActivity) getActivity()).getUser();
        if (user != null) {
            tvNickName = (TextView) v.findViewById(R.id.text_usr_name_profile);
            tvPhone = (TextView) v.findViewById(R.id.text_phone_profile);
            tvSignature = (TextView) v.findViewById(R.id.text_signature_profile);
            ivPortrait = (CircleImageView) v.findViewById(R.id.img_head_portrait_profile);
            tvNickName.setText(user.getNickname());
            tvPhone.setText(user.getUsername());
            tvSignature.setText(user.getSignature());
            Picasso.with(getContext())
                    .load(Constant.Value.BASE_URL + user.getPortrait())
                    .into(ivPortrait);
        }
        return v;
    }

    public void getUserInfo(String uid) {
        NetworkHelper.getUserInfoByUserId(uid, new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("changeProfile", response.body().toString());
                    UserResponse result = response.body();
                    if (result.success()) {
                        user = new User(result);
                        ((MainActivity) getActivity()).setUser(user);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvNickName.setText(user.getNickname());
                                tvPhone.setText(user.getUsername());
                                tvSignature.setText(user.getSignature());
                                Picasso.with(getContext())
                                        .load(Constant.Value.BASE_URL + user.getPortrait())
                                        .into(ivPortrait);
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {

            }
        });
    }
}



