package com.example.tang.wuhua;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.tang.wuhua.Adapter.MomentAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yalantis.guillotine.animation.GuillotineAnimation;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private static final long RIPPLE_DURATION = 250;
    private List<Integer> momentList = new ArrayList<>();
    private MomentAdapter momentAdapter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.root)
    FrameLayout root;
    @BindView(R.id.content_hamburger)
    View contentHamburger;
    @BindView(R.id.main_recyclerView)
    RecyclerView mainRecyclerview;
    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_test);
        ButterKnife.bind(this);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                for (int i = 3; i < 6; i++) {
                    momentList.add(i);
                }
                momentAdapter.notifyDataSetChanged();
                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            }
        });

        for (int i = 0; i < 3; i++) {
            momentList.add(i);
        }
        LinearLayoutManager mainLayoutManager = new LinearLayoutManager(this);
        mainRecyclerview.setLayoutManager(mainLayoutManager);
        momentAdapter = new MomentAdapter(momentList);
        mainRecyclerview.setAdapter(momentAdapter);



        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);
        }

        View guillotineMenu = LayoutInflater.from(this).inflate(R.layout.activity_guillotine, null);
        root.addView(guillotineMenu);

        new GuillotineAnimation.GuillotineBuilder(guillotineMenu, guillotineMenu.findViewById(R.id.guillotine_hamburger), contentHamburger)
                .setStartDelay(RIPPLE_DURATION)
                .setActionBarViewForAnimation(toolbar)
                .setClosedOnStart(true)
                .build();
    }
}

