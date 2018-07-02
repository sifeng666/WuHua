package com.example.tang.wuhua;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
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
