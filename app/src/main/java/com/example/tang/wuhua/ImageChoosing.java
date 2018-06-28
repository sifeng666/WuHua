package com.example.tang.wuhua;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tang on 28/06/2018.
 */

public class ImageChoosing extends AppCompatActivity {
//    private static final int REQUEST_CODE_CHOOSE = 23;
//    @BindView(R.id.send_lyle)
//    Button sendLyle;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        ButterKnife.bind(this);
//
//
//        sendLyle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Matisse.from(ImageChoosing.this)
//                        .choose(MimeType.allOf())//图片类型
//                        .countable(true)//true:选中后显示数字;false:选中后显示对号
//                        .maxSelectable(5)//可选的最大数
//                        .capture(true)//选择照片时，是否显示拍照
//                        .captureStrategy(new CaptureStrategy(true, "com.example.tang.wuhua.fileprovider"))//参数1 true表示拍照存储在共有目录，false表示存储在私有目录；参数2与 AndroidManifest中authorities值相同，用于适配7.0系统 必须设置
//                        .imageEngine(new GlideEngine())//图片加载引擎
//                        .forResult(REQUEST_CODE_CHOOSE);//
//            }
//        });
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
//            List<Uri> result = Matisse.obtainResult(data);
//            Log.d("Matisse", "mSelected: " + result);
//        }
//    }
}