package com.rick.monet.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.rick.monet.R;
import com.rick.monet.entity.MediaItem;
import com.rick.monet.model.MonetSpec;

/**
 * Author: Rick
 * Email: zhiyuanfeng@rastar.com
 * Date: 2019/2/28
 */
public class PreviewActivity extends AppCompatActivity {
    private ImageView mIvPhoto;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monet_activity_preview);
        mIvPhoto = findViewById(R.id.monet_iv_preview_photo);
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        Intent  intent = getIntent();
        MediaItem mediaItem = intent.getParcelableExtra("media");
        MonetSpec.getInstance().getmImageEngine().loadImage(this, mediaItem.get_data(), mIvPhoto);
    }

}
