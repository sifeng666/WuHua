package com.example.tang.wuhua;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tang.wuhua.Data.User;

/**
 * Created by root on 18-6-30.
 */

public class ProfileFragment extends Fragment {

    private User user;
    private TextView tvNickName;
    private TextView tvPhone;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_profile, container, false);
        user = ((MainActivity) getActivity()).getUser();
        if (user != null) {
            tvNickName = (TextView) v.findViewById(R.id.text_usr_name_profile);
            tvPhone = (TextView) v.findViewById(R.id.text_phone_profile);
            tvNickName.setText(user.getNickname());
            tvPhone.setText(user.getUsername());
        }
        return v;
    }
}



