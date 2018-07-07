package com.example.tang.wuhua;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tang.wuhua.Adapter.SimpleMomentAdapter;
import com.example.tang.wuhua.Data.Moment;
import com.example.tang.wuhua.Data.User;
import com.example.tang.wuhua.model.response.MomentResponse;
import com.example.tang.wuhua.model.response.card.MomentCard;
import com.example.tang.wuhua.net.helper.NetworkHelper;
import com.squareup.picasso.Picasso;

import net.datafans.android.common.widget.imageview.CommonImageView;

import java.util.ArrayList;
import java.util.List;

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
    private ImageView imgBackGround;
    private CircleImageView ivPortrait;
    private RecyclerView albumRecyclerView;
    private List<Moment> mMomentList;
    SimpleMomentAdapter simpleMomentAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_header, container, false);
        tvNickname = v.findViewById(R.id.text_name_header);
        tvSignature = v.findViewById(R.id.text_sign_header);
        ivPortrait = v.findViewById(R.id.commonImg_icon_header);
        imgBackGround = v.findViewById(R.id.Img_cover_header);
        user = ((MainActivity) getActivity()).getUser();
        tvNickname.setText(user.getNickname());
        tvSignature.setText(user.getSignature());
        Picasso.with(getContext()).load(Constant.Value.BASE_URL+user.getPortrait()).into(ivPortrait);
        //ivPortrait.loadImage(Constant.Value.BASE_URL+user.getPortrait());
        albumRecyclerView = v.findViewById(R.id.recyclerview_show_header);
        LinearLayoutManager albumLayoutManager = new LinearLayoutManager(getContext());
        albumRecyclerView.setLayoutManager(albumLayoutManager);
        int index = tvNickname.getText().length() % 5;
        switch (index) {
            case 0:
                imgBackGround.setImageResource(R.mipmap.bg_random_1);
                break;
            case 1:
                imgBackGround.setImageResource(R.mipmap.bg_random_2);
                break;
            case 2:
                imgBackGround.setImageResource(R.mipmap.bg_random_3);
                break;
            case 3:
                imgBackGround.setImageResource(R.mipmap.bg_random_4);
                break;
            case 4:
                imgBackGround.setImageResource(R.mipmap.bg_random_5);
                break;
        }
        mMomentList = new ArrayList<>();
        simpleMomentAdapter = new SimpleMomentAdapter(getContext(), mMomentList);
        albumRecyclerView.setAdapter(simpleMomentAdapter);
        getAlbum(user.getId());
        return v;
    }

    void getAlbum(String userid) {
        NetworkHelper.getAllMomentsByUserId(userid, new Callback<MomentResponse>() {
            @Override
            public void onResponse(Call<MomentResponse> call, Response<MomentResponse> response) {
                if (response.isSuccessful()) {
                    MomentResponse result = response.body();
                    if (result.success()) {
                        mMomentList.clear();
                        List<MomentCard> momentCardList = result.getMomentCards();
                        Log.d("album", Integer.toString(momentCardList.size()));
                        for (int i = 0; i < momentCardList.size(); i++) {
                            mMomentList.add(0, new Moment(momentCardList.get(i)));
                        }
                        simpleMomentAdapter.notifyDataSetChanged();
                        Log.d("album", Integer.toString(mMomentList.size()));
                    }
                }
            }

            @Override
            public void onFailure(Call<MomentResponse> call, Throwable t) {

            }
        });
    }
}
