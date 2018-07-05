package com.example.tang.wuhua;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tang.wuhua.Data.User;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by root on 18-7-4.
 */

public class EditProfileActivity extends AppCompatActivity {

    TextView tvCancel;
    TextView tvSend;
    EditText etNickname;
    EditText etUsername;
    EditText etPassword;
    EditText etPasswordConfirm;
    EditText etSignature;
    CircleImageView civPortrait;
    User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.comment_color_2));
        setContentView(R.layout.activity_set_profile);
        initData();
    }

    private void initData() {
        user = (User) getIntent().getSerializableExtra("user_data");
        tvCancel = (TextView) findViewById(R.id.cancel_in_profile_page);
        tvSend = (TextView) findViewById(R.id.edit_in_profile_page);
        etNickname = (EditText) findViewById(R.id.edit_usr_name_set_profile);
        etPassword = (EditText) findViewById(R.id.edit_new_pwd_set_profile);
        etPasswordConfirm = (EditText) findViewById(R.id.edit_verify_new_pwd_set_profile);
        etSignature = (EditText) findViewById(R.id.edit_signatrue_profile);
        etUsername = (EditText) findViewById(R.id.edit_phone_set_profile);
        civPortrait = (CircleImageView) findViewById(R.id.img_change_head_portrait_set_profile);

        etUsername.setText(user.getUsername());
        etNickname.setText(user.getNickname());
        etSignature.setText(user.getSignature());
        Picasso.with(EditProfileActivity.this)
                .load(Constant.Value.BASE_URL + user.getPortrait())
                .into(civPortrait);
    }

    private void bindListener() {
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void updateInfo() {

    }
}
