package com.example.tang.wuhua;

import android.Manifest;
import android.animation.TimeInterpolator;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.tang.wuhua.Adapter.MomentAdapter;
import com.example.tang.wuhua.Data.User;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yalantis.guillotine.animation.GuillotineAnimation;
import com.yalantis.guillotine.interfaces.GuillotineListener;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private static final long RIPPLE_DURATION = 250;
    private static final int REQUEST_CODE_CHOOSE = 23;
    private static final int REQUEST_CODE_PROFILECHANGE = 24;
    private long mExitTime; //退出时的时间
    private User user; //登录的用户
    private boolean isOffline; //离线测试
    final private int REQUEST_CODE_SEND = 10;
    private TextView tvMenuTitle;
    private enum fragmentId {
        PROFILE,
        ALBUM,
        MOMENT,
        SETTING,
        SUPPORT
    }
    private fragmentId curFragment;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.root)
    FrameLayout root;
    @BindView(R.id.content_hamburger)
    View contentHamburger;
    //@BindView(R.id.main_recyclerView)
    //RecyclerView mainRecyclerview;
    //@BindView(R.id.refreshLayout)
    //RefreshLayout refreshLayout;

    @BindView(R.id.send_lyle)
    ImageView sendLyle;
    @BindView(R.id.change_profile)
    ImageView changeProfile;

    private Dialog bigImgDialog;
    private ImageView bigImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.guillotine_background));
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        bigImgDialog = new Dialog(MainActivity.this);
        bigImageView = new ImageView(MainActivity.this);
        bigImgDialog.setContentView(bigImageView);
        bigImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bigImgDialog.dismiss();
            }
        });

        bigImageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setItems(new String[]{getResources().getString(R.string.save_picture)}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saveCroppedImage(((BitmapDrawable) bigImageView.getDrawable()).getBitmap());
                    }
                });
                builder.show();
                return true;
            }
        });

        //if (user == null) {
        user = (User) getIntent().getSerializableExtra("user_data");
        //}
        //Log.d("user", user.getUsername());
        isOffline = getIntent().getBooleanExtra("offline", true);

        //申请权限
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
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
            String [] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(MainActivity.this, permissions, 1);
        }


        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);
        }

        View guillotineMenu = LayoutInflater.from(this).inflate(R.layout.activity_guillotine, null);
        LinearLayout llProfit = (LinearLayout) guillotineMenu.findViewById(R.id.profile_group);
        LinearLayout llAlbum = (LinearLayout) guillotineMenu.findViewById(R.id.album_group);
        LinearLayout llLyle = (LinearLayout) guillotineMenu.findViewById(R.id.lyle_group);
        LinearLayout llSettings = (LinearLayout) guillotineMenu.findViewById(R.id.settings_group);
        LinearLayout llSupport = (LinearLayout) guillotineMenu.findViewById(R.id.support_group);
        final ImageView ivSwitchMenu = (ImageView) guillotineMenu.findViewById(R.id.guillotine_hamburger);
        tvMenuTitle = (TextView) findViewById(R.id.menu_title);
        FragmentManager menuManager = getSupportFragmentManager();
        FragmentTransaction menuTransaction = menuManager.beginTransaction();
        menuTransaction.replace(R.id.main_frame, new MomentFragment());
        menuTransaction.commit();
        root.addView(guillotineMenu);
        llProfit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager menuManager = getSupportFragmentManager();
                FragmentTransaction menuTransaction = menuManager.beginTransaction();
                menuTransaction.replace(R.id.main_frame, new ProfileFragment());
                //menuTransaction.addToBackStack(null);
                menuTransaction.commit();
                tvMenuTitle.setText("个人信息");
                curFragment = fragmentId.PROFILE;
                sendLyle.setVisibility(View.GONE);
                changeProfile.setVisibility(View.VISIBLE);
                ivSwitchMenu.post(new Runnable() {
                    @Override
                    public void run() {
                        ivSwitchMenu.performClick();
                    }
                });
            }
        });
        llAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager menuManager = getSupportFragmentManager();
                FragmentTransaction menuTransaction = menuManager.beginTransaction();
                menuTransaction.replace(R.id.main_frame, new AlbumFragment());
                menuTransaction.commit();
                tvMenuTitle.setText("相册");
                curFragment = fragmentId.ALBUM;
                sendLyle.setVisibility(View.GONE);
                changeProfile.setVisibility(View.GONE);
                ivSwitchMenu.post(new Runnable() {
                    @Override
                    public void run() {
                        ivSwitchMenu.performClick();
                    }
                });
            }
        });
        llLyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager menuManager = getSupportFragmentManager();
                FragmentTransaction menuTransaction = menuManager.beginTransaction();
                menuTransaction.replace(R.id.main_frame, new MomentFragment());
                menuTransaction.commit();
                tvMenuTitle.setText("lyle");
                curFragment = fragmentId.MOMENT;
                sendLyle.setVisibility(View.VISIBLE);
                changeProfile.setVisibility(View.GONE);
                ivSwitchMenu.post(new Runnable() {
                    @Override
                    public void run() {
                        ivSwitchMenu.performClick();
                    }
                });
            }
        });
        llSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager menuManager = getSupportFragmentManager();
                FragmentTransaction menuTransaction = menuManager.beginTransaction();
                menuTransaction.replace(R.id.main_frame, new SettingFragment());
                menuTransaction.commit();
                tvMenuTitle.setText("设置");
                curFragment = fragmentId.SETTING;
                sendLyle.setVisibility(View.GONE);
                changeProfile.setVisibility(View.GONE);
                ivSwitchMenu.post(new Runnable() {
                    @Override
                    public void run() {
                        ivSwitchMenu.performClick();
                    }
                });
            }
        });
        llSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager menuManager = getSupportFragmentManager();
                FragmentTransaction menuTransaction = menuManager.beginTransaction();
                menuTransaction.replace(R.id.main_frame, new SupportFragment());
                menuTransaction.commit();
                tvMenuTitle.setText("赞赏");
                curFragment = fragmentId.SUPPORT;
                sendLyle.setVisibility(View.GONE);
                changeProfile.setVisibility(View.GONE);
                ivSwitchMenu.post(new Runnable() {
                    @Override
                    public void run() {
                        ivSwitchMenu.performClick();
                    }
                });
            }
        });

        new GuillotineAnimation.GuillotineBuilder(guillotineMenu, guillotineMenu.findViewById(R.id.guillotine_hamburger), contentHamburger)
                .setStartDelay(RIPPLE_DURATION)
                .setActionBarViewForAnimation(toolbar)
                .setClosedOnStart(true)
                .setDuration(400)
                .build();



        sendLyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,
                        SendPageNineImage.class);
                intent.putExtra("user_data", user);
                startActivityForResult(intent, REQUEST_CODE_SEND);
                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                //finish();
            }
        });

        changeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditProfileActivity.class);
                intent.putExtra("user_data", user);
                startActivityForResult(intent, REQUEST_CODE_PROFILECHANGE);
                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
            }
        });

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
        else if (requestCode == REQUEST_CODE_SEND && resultCode == RESULT_OK) {
            Log.d("send", "send");
            MomentFragment momentFragment = (MomentFragment) getSupportFragmentManager().findFragmentById(R.id.main_frame);
            momentFragment.refreshLayout.autoRefresh();
        }
        else if (requestCode == REQUEST_CODE_PROFILECHANGE && resultCode == RESULT_OK) {
            ProfileFragment profileFragment = (ProfileFragment) getSupportFragmentManager().findFragmentById(R.id.main_frame);
            profileFragment.getUserInfo(user.getId());
        }
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
            Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }

    public User getUser() {
//        if (isOffline) {
//            return null;
//        }
        return user;
    }

    public void setUser(User newUser) {
        user = newUser;
    }

    public TextView getTvMenuTitle() {
        return tvMenuTitle;
    }

    public Dialog getBigImgDialog() {
        return bigImgDialog;
    }

    public ImageView getBigImageView() {
        return bigImageView;
    }

    //保存图片
    private void saveCroppedImage(Bitmap bmp) {
        File file = new File("/sdcard/myFolder");
        if (!file.exists())
            file.mkdir();

        file = new File("/sdcard/temp.jpg".trim());
        String fileName = file.getName();
        String mName = fileName.substring(0, fileName.lastIndexOf("."));
        String sName = fileName.substring(fileName.lastIndexOf("."));

        // /sdcard/myFolder/temp_cropped.jpg
        String newFilePath = "/sdcard/myFolder" + "/" + mName + "_cropped" + sName;
        file = new File(newFilePath);
        try {
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 50, fos);
            fos.flush();
            fos.close();
            Toast.makeText(MainActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}

