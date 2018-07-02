package com.example.tang.wuhua;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
            "http://i2.hoopchina.com.cn/blogfile/201303/30/136461474350812.jpg",
    };


    @BindView(R.id.ngiv_nine_grid)
    NineGridImageView<String> nine_grid;

    private NineGridImageViewAdapter<String> mAdapter1 = new NineGridImageViewAdapter<String>() {
        @Override
        protected void onDisplayImage(Context context, ImageView imageView, String url) {
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_page);
        ButterKnife.bind(this);
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

        nine_grid.setAdapter(mAdapter1);
        nine_grid.setImagesData(urls_list);
        if (urls_list.size() >= 9) {
            imgAddButtonInSendPage.setVisibility(View.GONE);
        }
    }


}
