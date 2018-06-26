package com.example.tang.wuhua;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author z1ycheng
 */

public abstract class BaseFragment extends Fragment {
    protected View mRoot; // 根布局
    protected Unbinder mRootUnbinder;

    @Override
    public void onAttach(Context context) {
        // 在fragment被添加到activity时最先调用的方法
        super.onAttach(context);

        // 初始化参数
        initArgs(getArguments());

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRoot == null) {
            int layoutId = getContentLayoutId();

            // 根据layoutId加载当前根布局，但不再在创建时加到container里面
            View root = inflater.inflate(layoutId, container, false);
            initWidget(root);
        }
        else {
            if(mRoot.getParent() != null){
                ((ViewGroup)mRoot.getParent()).removeView(mRoot);
            }
        }


        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // 在view创建完成后才初始化数据
        initData();
        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * 获取当前界面的资源文件id，由子类实现
     * @return 资源文件id
     */
    protected abstract int getContentLayoutId();

    /**
     * 初始化控件
     * @param root 根布局
     */
    protected void initWidget(View root){
        mRootUnbinder = ButterKnife.bind(this, root);
    }

    /**
     * 初始化数据
     */
    protected void initData(){}

    /**
     * 初始化相关参数
     */
    protected void initArgs(Bundle bundle) {

    }

    /**
     * 返回按键触发时调用
     * @return 返回true代表已处理返回逻辑，activity不用处理；false表示没有处理
     */
    public boolean onBackPressed() {

        // 默认情况下不拦截
        return false;
    }

}
