package com.example.tang.wuhua;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import butterknife.ButterKnife;

/**
 * @author z1ycheng
 */

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 初始化窗口
        initWindow();

        if(initArgs(getIntent().getExtras())){ // activity之间传值使用intent
            // 得到界面id并设置到界面中
            int layoutId = getContentLayoutId();
            setContentView(layoutId);

            initWidget();
            initData();
        }
        else {
            finish();
        }

    }

    /**
     * 初始化
     * @param  bundle 参数bundle
     * @return 参数正确返回true
     */
    protected boolean initArgs(Bundle bundle) {
        return true;
    }


    /**
     * 获取当前界面的资源文件id，由子类实现
     * @return 资源文件id
     */
    protected abstract int getContentLayoutId();


    /**
     * 初始化窗口
     */
    protected void initWindow() {

    }


    /**
     * 初始化控件
     */
    protected void initWidget() {
        ButterKnife.bind(this); // 绑定activity
    }

    /**
     * 初始化数据
     */
    protected void initData() {

    }

    /**
     * 点击导航上返回按钮
     * @return 返回按钮
     */
    @Override
    public boolean onSupportNavigateUp() {
        // 当点击界面上方导航返回键时，结束当前界面
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        // 当按下手机返回键时
        // 获取所有Fragment
        @SuppressLint("RestrictedApi")
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null && fragments.size() > 0) {
            for (Fragment fragment : fragments) {
                // 判断是否为可处理的Fragment类型
                if (fragment instanceof BaseFragment) {
                    // 判断是否拦截了返回按钮
                    if (((BaseFragment) fragment).onBackPressed()) {
                        return; // 直接返回
                    }
                }
            }
        }

        super.onBackPressed();
        finish();
    }
}
