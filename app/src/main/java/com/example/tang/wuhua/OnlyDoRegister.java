package com.example.tang.wuhua;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

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
    private String userNameRegister;
    private String userPwdRegister;
    private String userPhoneRegister;
    private Button bntRegister2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init(){
        editNameRegister = (EditText) findViewById(R.id.edit_name_register);
        editPwdRegister = (EditText) findViewById(R.id.edit_pwd_register);
        editPhoneRegister = (EditText) findViewById(R.id.edit_phone_register);
        imgClearNameRegister = (ImageView) findViewById(R.id.img_clear_name_register);
        imgClearPwdRegister = (ImageView) findViewById(R.id.img_clear_pwd_register);
        imgClearPhoneRegister = (ImageView) findViewById(R.id.img_clear_phone_register);

        EditTextClearTools.addClearListener(editNameRegister, imgClearNameRegister);
        EditTextClearTools.addClearListener(editPwdRegister, imgClearPwdRegister);
        EditTextClearTools.addClearListener(editPhoneRegister, imgClearPhoneRegister);
    }
}
