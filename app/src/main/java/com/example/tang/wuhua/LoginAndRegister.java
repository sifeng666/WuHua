package com.example.tang.wuhua;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tang.wuhua.Data.User;
import com.example.tang.wuhua.model.parameter.LoginModel;
import com.example.tang.wuhua.model.response.UserResponse;
import com.example.tang.wuhua.net.Network;
import com.example.tang.wuhua.net.helper.NetworkHelper;
import com.example.tang.wuhua.net.service.RemoteService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by tang on 21/06/2018.
 */

public class LoginAndRegister extends AppCompatActivity {

    private EditText editNameLogin;
    private EditText editPwdLogin;
    private ImageView imgClearNameLogin;
    private ImageView imgClearPwdLogin;
    private String userNameLogin;
    private String userPwdLogin;
    private Button btnLogin;
    private Button btnRegister;
    private CheckBox cbAccount;
    private CheckBox cbPassword;
    private long mExitTime; //退出时的时间

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.logoScreenBackground));
        setContentView(R.layout.activity_login);
        init();
    }

    private void init(){
        editNameLogin = (EditText) findViewById(R.id.edit_name_login);
        editPwdLogin = (EditText) findViewById(R.id.edit_pwd_login);
        imgClearNameLogin = (ImageView) findViewById(R.id.img_clear_name_login);
        imgClearPwdLogin = (ImageView) findViewById(R.id.img_clear_pwd_login);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnRegister = (Button) findViewById(R.id.btn_register_login);
        cbAccount = (CheckBox) findViewById(R.id.checkbox_name_login);
        cbPassword = (CheckBox) findViewById(R.id.checkbox_pwd_login);

        SharedPreferences sp = null;
        sp = getSharedPreferences("userData", MODE_PRIVATE);
        if (sp.getBoolean("rememberAccount", false)) {
            cbAccount.setChecked(true);
            editNameLogin.setText(sp.getString("username", null));
            if (sp.getBoolean("rememberPassword", false)) {
                cbPassword.setChecked(true);
                editPwdLogin.setText(sp.getString("password", null));
            }
        }


        EditTextClearTools.addClearListener(editNameLogin, imgClearNameLogin);
        EditTextClearTools.addClearListener(editPwdLogin, imgClearPwdLogin);

        cbPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cbAccount.setChecked(true);
                }
            }
        });

        cbAccount.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    cbPassword.setChecked(false);
                }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userNameLogin = editNameLogin.getText().toString();
                userPwdLogin = editPwdLogin.getText().toString();
                SharedPreferences.Editor editor = getSharedPreferences("userData", MODE_PRIVATE).edit();
                if (cbAccount.isChecked()) {
                    editor.putString("username", userNameLogin);
                    editor.putString("password", userPwdLogin);
                    editor.putBoolean("rememberAccount", true);
                    if (cbPassword.isChecked()) {
                        editor.putBoolean("rememberPassword", true);
                    }
                    else {
                        editor.putBoolean("rememberPassword", false);
                        editor.putString("password", null);
                    }
                    editor.apply();
                }
                else {
                    editor.putString("username", null);
                    editor.putString("password", null);
                    editor.putBoolean("rememberAccount", false);
                    editor.putBoolean("rememberPassword", false);
                    editor.apply();
                }
                login(userNameLogin, userPwdLogin);
//                userNameLogin = "13919334033";
//                userPwdLogin = "123";
//                Intent intent = new Intent(LoginAndRegister.this, MainActivity.class).setFlags(
//                        Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK
//                );
//                intent.putExtra("offline", true);
//                startActivity(intent);
//                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginAndRegister.this, OnlyDoRegister.class);
                startActivityForResult(intent, 1);
                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            exitApp();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exitApp() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Toast.makeText(LoginAndRegister.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    String username = data.getStringExtra("username");
                    editNameLogin.setText(username);
                    editPwdLogin.requestFocus();
                    editPwdLogin.setText("");
                    cbAccount.setChecked(false);
                    cbPassword.setChecked(false);
                }
                break;
            default:
        }
    }

    private void login(String username, String password) {
        NetworkHelper.login(new LoginModel(userNameLogin, userPwdLogin), new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    UserResponse result = response.body();
                    Log.d("response", result.toString());
                    if (result.success()) {
                        User user = new User(result);
                        Intent intent = new Intent(LoginAndRegister.this, MainActivity.class).setFlags(
                                Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK
                        );
                        intent.putExtra("user_data", user);
                        startActivity(intent);
                        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                    }
                    else {
                        Toast.makeText(LoginAndRegister.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(LoginAndRegister.this, "登录失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(LoginAndRegister.this, "Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
