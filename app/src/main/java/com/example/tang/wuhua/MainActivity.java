package com.example.tang.wuhua;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.tang.wuhua.Adapter.MomentAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yalantis.guillotine.animation.GuillotineAnimation;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private static final long RIPPLE_DURATION = 250;
    private static final int REQUEST_CODE_CHOOSE = 23;
    private List<Integer> momentList = new ArrayList<>();
    private MomentAdapter momentAdapter;
    public LocationClient mLocationClient; //位置信息
    private double latitude; //经度
    private double longitude; //纬度

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

    @BindView(R.id.send_lyle)
    Button sendLyle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE) !=
                PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
            String [] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(MainActivity.this, permissions, 1);
        }
        else {
            requestLocation();
        }

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
        momentAdapter.setClickListener(new MomentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, MomentAdapter.ViewName viewName, MomentAdapter.ViewHolder holder, int position) {
                if (viewName == MomentAdapter.ViewName.USERNAME) {
                    TextView tv = (TextView) view;
                    Toast.makeText(MainActivity.this, tv.getText(), Toast.LENGTH_SHORT).show();
                }
                else if (viewName == MomentAdapter.ViewName.COMMENT) {
                    Toast.makeText(MainActivity.this, "img_comment", Toast.LENGTH_SHORT).show();
                }
                else if (viewName == MomentAdapter.ViewName.LIKE) {
                    StringBuilder likeName = new StringBuilder(holder.tvLikePeople.getText().toString());
                    String myUsername = holder.tvUsername.getText().toString();
                    if (!holder.isLike) {
                        likeName.append(", ").append(myUsername);
                        holder.isLike = true;
                        holder.tvLikePeople.setText(likeName);
                        holder.lbLike.setLiked(true);
                    }
                    else {
                        holder.isLike = false;
                        likeName.delete(likeName.length() - (myUsername.length() + 2), likeName.length());
                        holder.tvLikePeople.setText(likeName);
                        holder.lbLike.setLiked(false);
                    }
                }
            }
        });
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

        sendLyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Matisse.from(MainActivity.this)
                        .choose(MimeType.allOf())//图片类型
                        .countable(true)//true:选中后显示数字;false:选中后显示对号
                        .maxSelectable(5)//可选的最大数
                        .capture(true)//选择照片时，是否显示拍照
                        .captureStrategy(new CaptureStrategy(true, "com.example.tang.wuhua.fileprovider"))//参数1 true表示拍照存储在共有目录，false表示存储在私有目录；参数2与 AndroidManifest中authorities值相同，用于适配7.0系统 必须设置
                        .imageEngine(new GlideEngine())//图片加载引擎
                        .forResult(REQUEST_CODE_CHOOSE);//
            }
        });

    }

    private void requestLocation() {
        initLocation();
        mLocationClient.start();
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
    }

    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            latitude = bdLocation.getLatitude();
            longitude = bdLocation.getLongitude();
            Toast.makeText(MainActivity.this, bdLocation.getStreet(), Toast.LENGTH_SHORT).show();
            mLocationClient.stop();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "必须同意所有权限才能使用本程序", Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    requestLocation();
                } else {
                    Toast.makeText(this, "发生未知错误", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            List<Uri> result = Matisse.obtainResult(data);
            Log.d("Matisse", "mSelected: " + result);
        }
    }
}

