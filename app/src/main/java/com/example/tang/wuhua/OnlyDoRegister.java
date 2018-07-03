package com.example.tang.wuhua;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tang on 26/06/2018.
 */

public class OnlyDoRegister extends AppCompatActivity {

    @BindView(R.id.edit_name_register)
    EditText editNameRegister;
    @BindView(R.id.img_clear_name_register)
    ImageView imgClearNameRegister;
    @BindView(R.id.edit_pwd_register)
    EditText editPwdRegister;
    @BindView(R.id.img_clear_pwd_register)
    ImageView imgClearPwdRegister;
    @BindView(R.id.edit_pwd2_register)
    EditText editPwd2Register;
    @BindView(R.id.img_clear_pwd2_register)
    ImageView imgClearPwd2Register;

    @BindView(R.id.btn_register_register)
    Button btnRegisterRegister;


    private String userNameRegister;
    private String userPwdRegister;
    private String userPwd2Register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        init();
    }

    private void init() {

        EditTextClearTools.addClearListener(editNameRegister, imgClearNameRegister);
        EditTextClearTools.addClearListener(editPwdRegister, imgClearPwdRegister);
        EditTextClearTools.addClearListener(editPwd2Register, imgClearPwd2Register);
    }
}
