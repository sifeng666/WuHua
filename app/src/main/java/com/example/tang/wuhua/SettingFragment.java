package com.example.tang.wuhua;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.suke.widget.SwitchButton;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by root on 18-6-30.
 */

public class SettingFragment extends Fragment {

    private MaterialDialog.Builder mBuilder;
    private MaterialDialog mMaterialDialog;

    TextView tvSwitchAccount;
    TextView tvLogout;
    TextView tvExit;
    LinearLayout layoutRemindSettings;
    LinearLayout layoutGeneralSettings;
    LinearLayout layoutPrivacySettings;
    LinearLayout layoutHelpSettings;
    LinearLayout layoutAboutSettings;
    SwitchButton toggleBtn;

    ArrayList advList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_settings, container, false);
        tvSwitchAccount = v.findViewById(R.id.switch_account);
        tvLogout = v.findViewById(R.id.logout);
        tvExit = v.findViewById(R.id.exitApp);
        layoutRemindSettings = v.findViewById(R.id.layout_remind_settings);
        layoutGeneralSettings = v.findViewById(R.id.layout_general_settings);
        layoutAboutSettings = v.findViewById(R.id.layout_about_settings);
        layoutHelpSettings = v.findViewById(R.id.layout_help_settings);
        layoutPrivacySettings = v.findViewById(R.id.layout_privacy_settings);
        toggleBtn = v.findViewById(R.id.switch_button);

        bindListener();


        return v;
    }

    private void bindListener() {

        toggleBtn.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                //TODO do your job
                showSorry();

            }
        });

        layoutRemindSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSorry();
            }
        });

        layoutGeneralSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSorry();
            }
        });
        layoutAboutSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSorry();
            }
        });
        layoutHelpSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSorry();
            }
        });
        layoutPrivacySettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSorry();
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

    private void showSorry() {
        mBuilder = new MaterialDialog.Builder(getActivity());
        mBuilder.title("提醒");
        mBuilder.titleGravity(GravityEnum.CENTER);
        mBuilder.titleColor(Color.parseColor("#000000"));
        mBuilder.content("很抱歉，该功能尚未完成\n敬请期待！");
        mBuilder.contentColor(Color.parseColor("#000000"));
        mMaterialDialog = mBuilder.build();
        mMaterialDialog.show();

    }
}
