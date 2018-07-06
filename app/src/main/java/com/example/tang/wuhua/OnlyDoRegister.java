package com.example.tang.wuhua;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tang.wuhua.model.parameter.RegisterModel;
import com.example.tang.wuhua.model.response.BaseResponse;
import com.example.tang.wuhua.net.helper.NetworkHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by tang on 26/06/2018.
 */

public class OnlyDoRegister extends AppCompatActivity {

    private EditText editNameRegister;
    private EditText editPwdRegister;
    private EditText editPhoneRegister;
    private ImageView imgClearNameRegister;
    private ImageView imgClearPwdRegister;
    private ImageView imgClearPhoneRegister;
    private String username;
    private String password;
    private String passwordConfirm;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.logoScreenBackground));
        setContentView(R.layout.activity_register);
        init();
    }

    private void init(){
        editNameRegister = (EditText) findViewById(R.id.edit_name_register);
        editPwdRegister = (EditText) findViewById(R.id.edit_pwd_register);
        editPhoneRegister = (EditText) findViewById(R.id.edit_pwd2_register);
        imgClearNameRegister = (ImageView) findViewById(R.id.img_clear_name_register);
        imgClearPwdRegister = (ImageView) findViewById(R.id.img_clear_pwd_register);
        imgClearPhoneRegister = (ImageView) findViewById(R.id.img_clear_pwd2_register);
        btnRegister = (Button) findViewById(R.id.btn_register_register);

        EditTextClearTools.addClearListener(editNameRegister, imgClearNameRegister);
        EditTextClearTools.addClearListener(editPwdRegister, imgClearPwdRegister);
        EditTextClearTools.addClearListener(editPhoneRegister, imgClearPhoneRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = editNameRegister.getText().toString();
                password = editPwdRegister.getText().toString();
                passwordConfirm = editPhoneRegister.getText().toString();
                if (!password.equals(passwordConfirm)) {
                    Toast.makeText(OnlyDoRegister.this, "密码不一致", Toast.LENGTH_SHORT).show();
                }
                else {
//                    register(username, password);
                    Intent intent = new Intent();
                    intent.putExtra("username", editNameRegister.getText().toString());
                    setResult(RESULT_OK, intent);
                    finish();
                    overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
                }
            }
        });
    }

    private void register(final String username, String password) {
        NetworkHelper.register(new RegisterModel(username, password), new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().success()) {
                        Toast.makeText(OnlyDoRegister.this, "注册成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        intent.putExtra("username", username);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {

            }
        });
    }
}
