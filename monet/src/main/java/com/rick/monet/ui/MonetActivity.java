package com.rick.monet.ui;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.TextView;

import com.rick.monet.Monet;
import com.rick.monet.R;
import com.rick.monet.model.AlbumCollection;
import com.rick.monet.model.CoverEntity;
import com.rick.monet.ui.adapter.GVAdapter;
import com.rick.monet.ui.widget.MonetGridView;
import com.rick.monet.utils.PermissionUtil;
import com.rick.monet.utils.ResultCode;

import java.util.ArrayList;

/**
 * Monet相册主页面
 * Author: Rick
 * Email: zhiyuanfeng@rastar.com
 * Date: 2019/2/15
 */
public class MonetActivity extends AppCompatActivity implements AlbumCollection.AlbumCallbacks {

    private AlbumCollection mAlbumCollection = new AlbumCollection();
    private MonetGridView mMonetGridView;
    private GVAdapter mGvAdapter;
    private ArrayList<CoverEntity> mCoverList = new ArrayList<>();
    private String[] permissions = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monet_activity_main);
        applyPermission();
        mMonetGridView = findViewById(R.id.monet_gv_main);
        mMonetGridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
    }

    /**
     * 权限申请
     */
    private void applyPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            PermissionUtil.checkPermission(this, mMonetGridView, permissions, ResultCode.REQUEST_PERMISSION_CODE_WRITE_READ, new PermissionUtil.permissionInterface() {
                @Override
                public void success() {
                    loadAblums();
                }
            });
        } else {
            loadAblums();
        }
    }

    /**
     * 读取器媒体
     */
    private void loadAblums() {
        mAlbumCollection.onCreate(this, this);
        mAlbumCollection.loadAlbums();
    }


    @Override
    public void onAlbumLoad(Cursor cursor) {
        Log.d("Cursor内容", cursor.toString() + "\n------------------------------------------------");
        StringBuilder builder = new StringBuilder();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                CoverEntity coverEntity = new CoverEntity();
                builder.append("  " + cursor.getString(cursor.getColumnIndex("bucket_id")));
                builder.append("  " + cursor.getString(cursor.getColumnIndex("bucket_display_name")));
                builder.append("  " + cursor.getString(cursor.getColumnIndex("_data")));
                builder.append("  " + cursor.getString(cursor.getColumnIndex("count")) + "\n");
                coverEntity.setBucket_id(cursor.getString(cursor.getColumnIndex("bucket_id")));
                coverEntity.setBucket_display_name(cursor.getString(cursor.getColumnIndex("bucket_display_name")));
                coverEntity.set_data(cursor.getString(cursor.getColumnIndex("_data")));
                coverEntity.setCount(cursor.getString(cursor.getColumnIndex("count")));
                mCoverList.add(coverEntity);
            } while (cursor.moveToNext());
        }
        Log.d("Cursor", builder.toString());
        mMonetGridView.setNumColumns(4);
        mGvAdapter = new GVAdapter(MonetActivity.this, mCoverList);
        mMonetGridView.setAdapter(mGvAdapter);
        mMonetGridView.setOnScrollListener(mGvAdapter);
    }


    @Override
    public void onAlbumReset() {

    }

    /**
     * 权限申请处理
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == ResultCode.REQUEST_PERMISSION_CODE_WRITE_READ) {
            if (PermissionUtil.verifyPermissions(grantResults)) {//有权限
                //
                loadAblums();
            } else {
                //没有权限
                if (!PermissionUtil.shouldShowPermissions(this, permissions)) {//这个返回false 表示勾选了不再提示
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    showSnackBar(mMonetGridView, "请去设置界面设置权限", "去设置", intent);
                } else {
                    //表示没有权限 ,但是没勾选不再提示
                    showSnackBar(mMonetGridView, "请允许权限请求!", "确定", null);
                }
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void showSnackBar(View view, String msg, String tap, final Intent intent) {
        Snackbar.make(view, msg,
                Snackbar.LENGTH_INDEFINITE)
                .setAction(tap, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (intent != null) {
                            startActivityForResult(intent, ResultCode.REQUEST_PERMISSION_SETTING);
                        } else {
                            applyPermission();
                        }
                    }
                })
                .show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //如果是从设置界面返回,就继续判断权限
        if (requestCode == ResultCode.REQUEST_PERMISSION_SETTING) {
            applyPermission();
        }
    }
}
