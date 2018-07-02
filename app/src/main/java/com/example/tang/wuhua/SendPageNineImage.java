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
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;
import com.squareup.picasso.Picasso;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by tang on 02/07/2018.
 */


public class SendPageNineImage extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGHT = 2000;//delay second
    private static final int REQUEST_CODE_CHOOSE = 23;
    @BindView(R.id.img_add_button_in_send_page)
    CircleImageView imgAddButtonInSendPage;


    private List<String> urls_list = new ArrayList<>();
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


    //@BindView(R.id.ngiv_nine_grid)
    NineGridImageView<String> nine_grid;

    private NineGridImageViewAdapter<String> mAdapter1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_page);
        ButterKnife.bind(this);
        nine_grid = (NineGridImageView<String>) findViewById(R.id.ngiv_nine_grid);
        imgAddButtonInSendPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Matisse.from(SendPageNineImage.this)
                        .choose(MimeType.allOf())//图片类型
                        .countable(true)//true:选中后显示数字;false:选中后显示对号
                        .maxSelectable(9)//可选的最大数
                        .capture(true)//选择照片时，是否显示拍照
                        .captureStrategy(new CaptureStrategy(true, "com.example.tang.wuhua.fileprovider"))//参数1 true表示拍照存储在共有目录，false表示存储在私有目录；参数2与 AndroidManifest中authorities值相同，用于适配7.0系统 必须设置
                        .imageEngine(new GlideEngine())//图片加载引擎
                        .forResult(REQUEST_CODE_CHOOSE);//
            }
        });

        urls_list.addAll(Arrays.asList(IMG_URL_LIST));
        initAdapter();
        nine_grid.setAdapter(mAdapter1);
        nine_grid.setImagesData(urls_list);
//        if (urls_list.size() >= 9) {
//            imgAddButtonInSendPage.setVisibility(View.GONE);
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            List<Uri> result = Matisse.obtainResult(data);
            for (int i = 0; i < result.size(); i++) {
                Log.d("img", result.get(i).toString());
                //urls_list.add(getRealPathFromUri(getApplicationContext(),  result.get(i)));
                //urls_list.add(result.get(i));
            }
            urls_list.add("http://i2.hoopchina.com.cn/blogfile/201303/30/136461474350812.jpg");
            //urls_list.remove(0);
            Log.d("list", urls_list.toString());

            //initAdapter();
//            nine_grid = (NineGridImageView<String>) findViewById(R.id.ngiv_nine_grid);
            //nine_grid.setAdapter(mAdapter1);
            //nine_grid.setImagesData(urls_list);
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


