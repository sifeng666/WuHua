package com.example.tang.wuhua;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;


/**
 * Created by tang on 21/06/2018.
 */

public class IntroToMain extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGHT = 5000;//delay second
    private Button mSkip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        setContentView(R.layout.activity_intro);
        mSkip = (Button)findViewById(R.id.buttonSkip);
        final Runnable myRun = new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                Intent intent = new Intent(IntroToMain.this, LoginAndRegister.class);
                startActivity(intent);
                finish();
            }
        };
        final Handler handler = new Handler();
        //使用handler对象来定时启动线程运行
        handler.postDelayed(myRun, SPLASH_DISPLAY_LENGHT);
        mSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //移除handler延迟加载里面的线程，就不会存在执行两次的情况
                handler.removeCallbacks(myRun);
                Intent intent = new Intent(IntroToMain.this,
                        LoginAndRegister.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
