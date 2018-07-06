package com.example.tang.wuhua;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.UiThread;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.tang.wuhua.Data.User;
import com.example.tang.wuhua.model.parameter.MomentModel;
import com.example.tang.wuhua.model.response.BaseResponse;
import com.example.tang.wuhua.net.Network;
import com.example.tang.wuhua.net.helper.NetworkHelper;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;
import com.squareup.picasso.Picasso;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by tang on 02/07/2018.
 */


public class SendPageNineImage extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGHT = 2000;//delay second
    private static final int REQUEST_CODE_CHOOSE = 23;
    private LinearLayout layout;
    private TextView tvHint;
    private TextView cancel;
    private TextView send;
    private LocationClient mLocationClient; //位置信息
    private String location;
    private double latitude; //经度
    private double longitude; //纬度
    private String textContent;
    private User me;
    @BindView(R.id.img_add_button_in_send_page)
    CircleImageView imgAddButtonInSendPage;
    @BindView(R.id.add_img_layout)
    LinearLayout addImgLayout;
    @BindView(R.id.ngiv_nine_grid)
    NineGridImageView<String> nine_grid;
    @BindView(R.id.my_location_send_page)
    TextView tvLocation;
    @BindView(R.id.edit_input_in_send_page)
    EditText etText;

    private NineGridImageViewAdapter<String> mAdapter1;

    private List<String> urls_list = new ArrayList<>();
    private List<String> urls_list_send = new ArrayList<>();
    private String[] IMG_URL_LIST = {
            "http://pic9.nipic.com/20100814/2889649_133155087075_2.jpg",
            "http://img5.imgtn.bdimg.com/it/u=3173289370,1988370443&fm=27&gp=0.jpg",
            "http://img.lssdjt.com/201506/24003803129.jpg",
            "http://img4.cache.netease.com/photo/0005/2013-04-11/600x450_8S5JAMQV00BV0005.jpg",
            "http://image.uczzd.cn/12206158155493040271.jpeg",
            "http://www.cnr.cn/newscenter/tyxw/international/news/20151104/W020151104324334665281.jpg",
            "http://cdnq.duitang.com/uploads/item/201406/19/20140619172206_F5AMv.thumb.700_0.jpeg",
            "http://images.china.cn/attachement/jpg/site1000/20140617/7427ea210951150a150456.jpg",
            //"http://i2.hoopchina.com.cn/blogfile/201303/30/136461474350812.jpg",
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.comment_color_2));
        setContentView(R.layout.activity_send_page);
        ButterKnife.bind(this);
        me = (User) getIntent().getSerializableExtra("user_data");
        mLocationClient = new LocationClient(SendPageNineImage.this);
        mLocationClient.registerLocationListener(new SendPageNineImage.MyLocationListener());
        tvHint = (TextView) findViewById(R.id.addImg_hint);
        layout = (LinearLayout) findViewById(R.id.send_layout);
        cancel = (TextView) findViewById(R.id.cancel_in_send_page);
        send = (TextView) findViewById(R.id.btn_send_moment);
        requestLocation();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
                overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textContent = etText.getText().toString();
                Log.d("latitude", Double.toString(latitude));
                Log.d("longitude", Double.toString(longitude));
                Log.d("publishTime", new Date().toString());
                sendMoment(me.getId(), latitude, longitude, textContent, urls_list_send, MomentModel.MEDIA_TYPE_IMG, new Date(), location);
//                setResult(RESULT_OK);
//                finish();
            }
        });

        imgAddButtonInSendPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Matisse.from(SendPageNineImage.this)
                        .choose(MimeType.allOf())//图片类型
                        .countable(true)//true:选中后显示数字;false:选中后显示对号
                        .maxSelectable(9-urls_list.size())//可选的最大数
                        .capture(true)//选择照片时，是否显示拍照
                        .captureStrategy(new CaptureStrategy(true, "com.example.tang.wuhua.fileprovider"))//参数1 true表示拍照存储在共有目录，false表示存储在私有目录；参数2与 AndroidManifest中authorities值相同，用于适配7.0系统 必须设置
                        .imageEngine(new GlideEngine())//图片加载引擎
                        .forResult(REQUEST_CODE_CHOOSE);//
            }
        });
        //urls_list.addAll(Arrays.asList(IMG_URL_LIST));
        initAdapter();
        nine_grid.setAdapter(mAdapter1);
        nine_grid.setImagesData(urls_list);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            List<Uri> result = Matisse.obtainResult(data);
            for (int i = 0; i < result.size(); i++) {
                Log.d("img", result.get(i).toString());
                urls_list.add("file://" + getRealPathFromUri(getApplicationContext(),  result.get(i)));
                urls_list_send.add(getRealPathFromUri(getApplicationContext(),  result.get(i)));
            }
            layout.removeView(nine_grid);
            nine_grid = new NineGridImageView<String>(this);
            nine_grid.setAdapter(mAdapter1);
            nine_grid.setImagesData(urls_list);
            nine_grid.setGap(4);
            nine_grid.setShowStyle(NineGridImageView.STYLE_GRID);
            nine_grid.setPadding(10, 0, 10, 0);

            nine_grid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            nine_grid.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return false;
                }
            });


            layout.addView(nine_grid, 3);
            if (urls_list.size() == 9) {
                addImgLayout.setVisibility(View.GONE);
            }
        }
    }

    public void initAdapter() {
        mAdapter1 = new NineGridImageViewAdapter<String>() {
            @Override
            protected void onDisplayImage(Context context, ImageView imageView, String url) {
                Log.d("url", url);
                Picasso.with(context)
                        .load(url)
                        .into(imageView);
            }

            @Override
            protected ImageView generateImageView(Context context) {

                return super.generateImageView(context);
            }

            @Override
            protected void onItemImageClick(Context context, int index, List list) {
                super.onItemImageClick(context, index, list);
                Toast.makeText(context, "" + index, Toast.LENGTH_LONG).show();
            }



        };
    }

    public void sendMoment(String userId, double latitude, double longitude, String text, List<String> images,
                           int videos, Date time, String location) {
        NetworkHelper.publishMoment(new MomentModel(userId, latitude, longitude, text, images, videos, time, location),
                new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        if (response.isSuccessful()) {
                            Log.d("sendResponse", response.toString());
                            if (response.body().success()) {
                                setResult(RESULT_OK);
                                Log.d("send", "ok");
                                finish();
                                overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
                            }
                            else {
                                Toast.makeText(SendPageNineImage.this, "发送失败，请重试", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(SendPageNineImage.this, "发送失败，请重试", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {
                        Toast.makeText(SendPageNineImage.this, "网络失败，请重试", Toast.LENGTH_SHORT).show();
                        t.printStackTrace();
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
            location = bdLocation.getStreet();
            final String loc = location;
            //Toast.makeText(getContext(), bdLocation.getStreet(), Toast.LENGTH_SHORT).show();
            SendPageNineImage.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tvLocation.setText(loc);
                }
            });
            Log.d("location", location);
            mLocationClient.stop();
        }
    }

    /**
     * 根据Uri获取图片的绝对路径
     *
     * @param context 上下文对象
     * @param uri     图片的Uri
     * @return 如果Uri对应的图片存在, 那么返回该图片的绝对路径, 否则返回null
     */
    public static String getRealPathFromUri(Context context, Uri uri) {
        int sdkVersion = Build.VERSION.SDK_INT;
        if (sdkVersion >= 19) { // api >= 19
            return getRealPathFromUriAboveApi19(context, uri);
        } else { // api < 19
            return getRealPathFromUriBelowAPI19(context, uri);
        }
    }

    /**
     * 适配api19以下(不包括api19),根据uri获取图片的绝对路径
     *
     * @param context 上下文对象
     * @param uri     图片的Uri
     * @return 如果Uri对应的图片存在, 那么返回该图片的绝对路径, 否则返回null
     */
    private static String getRealPathFromUriBelowAPI19(Context context, Uri uri) {
        return getDataColumn(context, uri, null, null);
    }

    /**
     * 适配api19及以上,根据uri获取图片的绝对路径
     *
     * @param context 上下文对象
     * @param uri     图片的Uri
     * @return 如果Uri对应的图片存在, 那么返回该图片的绝对路径, 否则返回null
     */
    @SuppressLint("NewApi")
    private static String getRealPathFromUriAboveApi19(Context context, Uri uri) {
        String filePath = null;
        if (DocumentsContract.isDocumentUri(context, uri)) {
            // 如果是document类型的 uri, 则通过document id来进行处理
            String documentId = DocumentsContract.getDocumentId(uri);
            if (isMediaDocument(uri)) { // MediaProvider
                // 使用':'分割
                String id = documentId.split(":")[1];

                String selection = MediaStore.Images.Media._ID + "=?";
                String[] selectionArgs = {id};
                filePath = getDataColumn(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection, selectionArgs);
            } else if (isDownloadsDocument(uri)) { // DownloadsProvider
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(documentId));
                filePath = getDataColumn(context, contentUri, null, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())){
            // 如果是 content 类型的 Uri
            filePath = getDataColumn(context, uri, null, null);
        } else if ("file".equals(uri.getScheme())) {
            // 如果是 file 类型的 Uri,直接获取图片对应的路径
            filePath = uri.getPath();
        }
        return filePath;
    }

    /**
     * 获取数据库表中的 _data 列，即返回Uri对应的文件路径
     * @return
     */
    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        String path = null;

        String[] projection = new String[]{MediaStore.Images.Media.DATA};
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(projection[0]);
                path = cursor.getString(columnIndex);
            }
        } catch (Exception e) {
            if (cursor != null) {
                cursor.close();
            }
        }
        return path;
    }

    /**
     * @param uri the Uri to check
     * @return Whether the Uri authority is MediaProvider
     */
    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri the Uri to check
     * @return Whether the Uri authority is DownloadsProvider
     */
    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

}


