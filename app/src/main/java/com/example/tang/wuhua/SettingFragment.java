package com.example.tang.wuhua;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by root on 18-6-30.
 */

public class SettingFragment extends Fragment {

    TextView tvSwitchAccount;
    TextView tvLogout;
    TextView tvExit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_settings, container, false);
        tvSwitchAccount = v.findViewById(R.id.switch_account);
        tvLogout = v.findViewById(R.id.logout);
        tvExit = v.findViewById(R.id.exitApp);

        bindListener();

        return v;
    }

    private void bindListener() {
        tvSwitchAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginAndRegister.class);
                startActivity(intent);
                intent.putExtra("user_data", ((MainActivity) getActivity()).getUser());
                getActivity().finish();
            }
        });

        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginAndRegister.class);
                startActivity(intent);
                intent.putExtra("user_data", ((MainActivity) getActivity()).getUser());
                getActivity().finish();
            }
        });

        tvExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
                System.exit(0);
            }
        });
    }
}
