package com.example.tang.wuhua;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by root on 18-6-30.
 */

public class SettingFragment extends Fragment {


    TextView tvSwitchAccount;
    TextView tvLogout;
    TextView tvExit;
    @BindView(R.id.layout_profile_settings)
    LinearLayout layoutProfileSettings;
    @BindView(R.id.layout_remind_settings)
    LinearLayout layoutRemindSettings;
    @BindView(R.id.layout_privacy_settings)
    LinearLayout layoutPrivacySettings;
    @BindView(R.id.layout_help_settings)
    LinearLayout layoutHelpSettings;
    @BindView(R.id.layout_about_settings)
    LinearLayout layoutAboutSettings;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_settings, container, false);
        tvSwitchAccount = v.findViewById(R.id.switch_account);
        tvLogout = v.findViewById(R.id.logout);
        tvExit = v.findViewById(R.id.exitApp);

        bindListener();

        unbinder = ButterKnife.bind(this, v);
        return v;
    }

    private void bindListener() {
        layoutProfileSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProfileFragment.class);
            }
        });



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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
