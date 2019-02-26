package com.rick.monet.model;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import java.lang.ref.WeakReference;

/**
 * 图片处理器 主要是封装LoaderManager的处理
 * Author: Rick
 * Email: zhiyuanfeng@rastar.com
 * Date: 2019/2/15
 */
public class AlbumCollection implements LoaderManager.LoaderCallbacks<Cursor> {
    private static  final int LOADER_ID = 1;
    private WeakReference<Context> mContext;
    private LoaderManager mLoaderManager;
    private AlbumCallbacks mCallbacks;
    private boolean mLoadFinished; //判断是否加载完成
    public void onCreate(FragmentActivity activity, AlbumCallbacks callbacks) {
        mContext = new WeakReference<Context>(activity);
        mLoaderManager = activity.getSupportLoaderManager();
        mCallbacks = callbacks;
    }

    public void loadAlbums() {
        mLoaderManager.initLoader(LOADER_ID, null, this);
    }


    /**
     * 把相册加载器添加到LoaderManager中
     * @param id
     * @param args
     * @return
     */
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        Context context = mContext.get();
        if (context == null) {
            return null;
        }
        mLoadFinished = false;
        return AlbumLoader.newInstance(context);
    }

    /**
     * 数据加载完成
     * @param loader
     * @param data
     */
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Context context = mContext.get();
        if (context == null) {
            return;
        }

        if (!mLoadFinished) {
            mLoadFinished = true;
            mCallbacks.onAlbumLoad(data);
        }
    }

    /**
     * 重置Loader
     * @param loader
     */
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Context context = mContext.get();
        if (context == null) {
            return;
        }

        mCallbacks.onAlbumReset();
    }


    /**
     * 回调接口
     */
    public interface AlbumCallbacks {
        void onAlbumLoad(Cursor cursor);

        void onAlbumReset();
    }
}
