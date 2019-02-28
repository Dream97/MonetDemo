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
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListPopupWindow;

import com.rick.monet.Monet;
import com.rick.monet.R;
import com.rick.monet.entity.Album;
import com.rick.monet.entity.MediaItem;
import com.rick.monet.model.AlbumCollection;
import com.rick.monet.model.CoverEntity;
import com.rick.monet.ui.activity.PreviewActivity;
import com.rick.monet.ui.adapter.AlbumMediaAdapter;
import com.rick.monet.ui.adapter.AlbumsAdapter;
import com.rick.monet.ui.adapter.GVAdapter;
import com.rick.monet.ui.fragment.MediaSelectionFragment;
import com.rick.monet.ui.widget.AlbumsSpinner;
import com.rick.monet.ui.widget.BucketNameTextView;
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
public class MonetActivity extends AppCompatActivity implements AlbumCollection.AlbumCallbacks
    , AdapterView.OnItemSelectedListener, View.OnClickListener , AlbumMediaAdapter.MediaItemListener {

    private BucketNameTextView mTvBucketName;
    private AlbumCollection mAlbumCollection = new AlbumCollection();
    private MonetGridView mMonetGridView;
    private Toolbar mToolbar;
    private GVAdapter mGvAdapter;
    private ImageView mIvClose;
    private ArrayList<CoverEntity> mCoverList = new ArrayList<>();
    private String[] permissions = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    //弹窗管理
    private AlbumsSpinner mAlbumsSpinner;
    private AlbumsAdapter mAlbumsAdapter;
    //中间展示内容frameLayout
    private View mContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monet_activity_main);
        applyPermission();
        mMonetGridView = findViewById(R.id.monet_gv_main);
        mTvBucketName = findViewById(R.id.monet_tv_main_bucket_name);
        mIvClose = findViewById(R.id.monet_iv_main_back);
        mIvClose.setOnClickListener(this);
//        mToolbar = findViewById(R.id.monet_main_toolbar);
//        mToolbar.setNavigationIcon(R.mipmap.ic_left_white);
//        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//        mMonetGridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        mAlbumsSpinner = new AlbumsSpinner(this);
        mAlbumsAdapter = new AlbumsAdapter(this,null);
        mAlbumsSpinner.setShowView(mTvBucketName);
        mAlbumsSpinner.setItemListener(this);
        mAlbumsSpinner.setAdapter(mAlbumsAdapter);

        mContainer = findViewById(R.id.monet_activity_fl_media);
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


    /**
     * Loader查询后数据回调
     * @param cursor 浮标
     */
    @Override
    public void onAlbumLoad(Cursor cursor) {
//        mMonetGridView.setNumColumns(4);
//        mGvAdapter = new GVAdapter(MonetActivity.this, mCoverList);
//        mMonetGridView.setAdapter(mGvAdapter);
//        mMonetGridView.setOnScrollListener(mGvAdapter);

        mAlbumsAdapter = new AlbumsAdapter(this, cursor);
        mAlbumsSpinner.setAdapter(mAlbumsAdapter);

        cursor.moveToFirst();
        onAlbumSelect(new Album(cursor));
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


    /**
     * listPopupWindow选中回调监听
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Cursor cursor = mAlbumsAdapter.getCursor();
        cursor.moveToPosition(position);
        Album album = new Album(cursor);
        onAlbumSelect(album);
    }

    /**
     * 选择某图库分类时刷新
     * @param album
     */
    public void onAlbumSelect(Album album) {
        if (album != null) {
            MediaSelectionFragment fragment = MediaSelectionFragment.getInstance(album);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.monet_activity_fl_media,fragment, MediaSelectionFragment.class.getSimpleName())
                    .commitAllowingStateLoss();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.monet_iv_main_back) {
            finish();
        }
    }

    @Override
    public void onMediaClick(Cursor cursor) {
        Intent intent = new Intent(this, PreviewActivity.class);
        MediaItem item = new MediaItem(cursor);
        intent.putExtra("media",item);
        startActivityForResult(intent, 1);
    }
}
