package com.rick.monet.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;

import com.rick.monet.R;
import com.rick.monet.model.CoverEntity;
import com.rick.monet.model.MonetSpec;
import com.rick.monet.ui.MonetActivity;
import com.rick.monet.ui.widget.MonetImage;
import com.rick.monet.utils.ImageEngine;
import com.rick.monet.utils.LoadBitmapUtil;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Author: Rick
 * Email: zhiyuanfeng@rastar.com
 * Date: 2019/2/18
 */
public class GVAdapter extends BaseAdapter implements AbsListView.OnScrollListener{
    private Context mContext;
    private ArrayList<CoverEntity> mCoverList;
    private List<String> mPathList = new ArrayList<>();
    private LoadBitmapUtil mLoadBitmapUtil;
    private int mStart = 0;
    private int mEnd = 0;
    private boolean mFirst = true;
    public static int SCROLL_STATE_IDLE = 0;            //结束滚动
    public static int SCROLL_STATE_TOUCH_SCROLL = 1;    //触摸滚动
    public static int SCROLL_STATE_FLING = 2;           //手指离开屏幕后的滚动状态
    public GVAdapter(Context context, ArrayList<CoverEntity> coverList) {
        this.mContext = context;
        this.mCoverList = coverList;
        for (CoverEntity coverEntity: coverList) {
            mPathList.add(coverEntity.get_data());
        }
        mLoadBitmapUtil = new LoadBitmapUtil(mPathList);
    }

    @Override
    public int getCount() {
        return mCoverList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.monet_item_gv_cell, parent, false);
        MonetImage cover = view.findViewById(R.id.monet_iv_item_cover);
        cover.setTag(mCoverList.get(position).get_data());
//        String imagePath = "file:////"+mCoverList.get(position).get_data();
//        Uri uri ;
//        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
//            //Android 7.0系统开始 使用本地真实的Uri路径不安全,使用FileProvider封装共享Uri
//            imagePath = Uri.parse(imagePath).getPath();
//        }
//        File file = new File(imagePath);
//        if (file != null && file.exists()) {
//            //本地文件
//            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
//                //Android 7.0系统开始 使用本地真实的Uri路径不安全,使用FileProvider封装共享Uri
//                uri = FileProvider.getUriForFile(mContext, MonetActivity.class.getPackage().getName() + ".fileprovider", file);
//            } else {
//                uri = Uri.fromFile(file);
//            }
//        } else {
//            //可能是资源路径的地址
//            uri = Uri.parse(imagePath);
//        }

//        Uri uri = Uri.parse(mCoverList.get(position).get_data());
//        Picasso.get().load(uri).into(cover);
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inSampleSize = 2;
//        Bitmap bitmap = BitmapFactory.decodeFile(uri.getPath(), options);
//        cover.setImageBitmap(bitmap);
//        Picasso.get().load(mCoverList.get(position).get_data()).into(cover);
//        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"zhc.png");
//        Picasso.get().load(uri).into(cover);
//        ImageEngine imageEngine = MonetSpec.getInstance().getmImageEngine();
//        imageEngine.loadImage(mContext, mCoverList.get(position).get_data(), cover);
        return view;
    }

    /**
     * 监听滑动状态
     * @param view
     * @param scrollState
     */
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_IDLE) {
            mLoadBitmapUtil.load(view, mStart, mEnd);
        } else  if (scrollState == SCROLL_STATE_TOUCH_SCROLL){
            mLoadBitmapUtil.cancel();
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        mStart = firstVisibleItem;
        mEnd = firstVisibleItem + visibleItemCount;
        if (mFirst && visibleItemCount > 0) {
            mLoadBitmapUtil.load(view, mStart, mEnd);
            mFirst = false;
        }
//        mLoadBitmapUtil.load(view, mStart, mEnd);
//        Log.d("item数目", visibleItemCount+"==============");
    }
}
