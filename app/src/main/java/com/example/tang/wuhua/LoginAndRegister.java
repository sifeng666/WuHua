package com.example.tang.wuhua;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tang on 21/06/2018.
 */

public class LoginAndRegister extends AppCompatActivity {

    @BindView(R.id.edit_name_login)
    EditText editNameLogin;
    @BindView(R.id.img_clear_name_login)
    ImageView imgClearNameLogin;
    @BindView(R.id.edit_pwd_login)
    EditText editPwdLogin;
    @BindView(R.id.img_clear_pwd_login)
    ImageView imgClearPwdLogin;
    @BindView(R.id.checkbox_name_login)
    CheckBox checkboxNameLogin;
    @BindView(R.id.checkbox_pwd_login)
    CheckBox checkboxPwdLogin;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_register_login)
    Button btnRegisterLogin;

    private String userNameLogin;
    private String userPwdLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        EditTextClearTools.addClearListener(editNameLogin, imgClearNameLogin);
        EditTextClearTools.addClearListener(editPwdLogin, imgClearPwdLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userNameLogin = editNameLogin.getText().toString();
                userPwdLogin = editPwdLogin.getText().toString();
                Intent intent = new Intent(LoginAndRegister.this, MainActivity.class).setFlags(
                        Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK
                );
                startActivity(intent);
            }
        });
    }
}
