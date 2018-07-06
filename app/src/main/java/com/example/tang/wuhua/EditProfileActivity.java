package com.example.tang.wuhua;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tang.wuhua.Data.User;
import com.example.tang.wuhua.model.parameter.InfoUpdateModel;
import com.example.tang.wuhua.model.response.BaseResponse;
import com.example.tang.wuhua.net.helper.NetworkHelper;
import com.squareup.picasso.Picasso;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    String portrait;
    private static final int REQUEST_CODE_CHOOSE = 23;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.comment_color_2));
        setContentView(R.layout.activity_set_profile);
        initData();
        bindListener();
    }

    private void initData() {
        user = (User) getIntent().getSerializableExtra("user_data");
        if (user == null) {
            user = new User("JACK");
        }
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
        portrait = user.getPortrait();
        Picasso.with(EditProfileActivity.this)
                .load(Constant.Value.BASE_URL + user.getPortrait())
                .into(civPortrait);
    }

    private void bindListener() {
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
            }
        });

        tvSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //finish();
                //overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
                String newPwd = etPassword.getText().toString();
                String newPwsConfirm = etPasswordConfirm.getText().toString();
                if (!newPwd.equals(newPwsConfirm)) {
                    Toast.makeText(EditProfileActivity.this, "密码不一致", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (newPwd == "") {
                    newPwd = null;
                }
                Log.d("update", "fuck");
                updateInfo(user.getUsername(), etNickname.getText().toString(), newPwd, portrait, etSignature.getText().toString());
            }
        });
        civPortrait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Matisse.from(EditProfileActivity.this)
                        .choose(MimeType.allOf())//图片类型
                        .countable(true)//true:选中后显示数字;false:选中后显示对号
                        .maxSelectable(1)//可选的最大数
                        .capture(true)//选择照片时，是否显示拍照
                        .captureStrategy(new CaptureStrategy(true, "com.example.tang.wuhua.fileprovider"))//参数1 true表示拍照存储在共有目录，false表示存储在私有目录；参数2与 AndroidManifest中authorities值相同，用于适配7.0系统 必须设置
                        .imageEngine(new GlideEngine())//图片加载引擎
                        .forResult(REQUEST_CODE_CHOOSE);//
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            List<Uri> result = Matisse.obtainResult(data);
            portrait = SendPageNineImage.getRealPathFromUri(EditProfileActivity.this, result.get(0));
            Picasso.with(EditProfileActivity.this)
                    .load(result.get(0))
                    .into(civPortrait);
        }
    }

    private void updateInfo(String username, String nickname, String password, String portraitPath, String signature) {
        NetworkHelper.updateUserInfo(new InfoUpdateModel(username, nickname, password, portraitPath, signature),
                new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        Log.d("updateInfo", response.toString());
                        if (response.isSuccessful()) {
                            if (response.body().success()) {
                                Toast.makeText(EditProfileActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                finish();
                                overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {
                        t.printStackTrace();
                        Toast.makeText(EditProfileActivity.this, "Fail", Toast.LENGTH_SHORT);
                    }
                });
    }
}
