package com.rick.monet.model;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.rick.monet.entity.Album;
import com.rick.monet.loader.AlbumMediaLoader;

import java.lang.ref.WeakReference;

/**
 * 对查询当前图库后数据回调管理
 * 主要是封装LoaderManager的处理
 * Author: Rick
 * Email: zhiyuanfeng@rastar.com
 * Date: 2019/2/27
 */
public class AlbumMediaCollection implements LoaderManager.LoaderCallbacks<Cursor> {

    private int LOADER_ID = 2; //LoaderManager标识
    private String ALBUM_KEY = "album";
    private WeakReference<Context> mContext ;
    private LoaderManager mLoaderManager;
    private AlbumMediaCallbacks mMediaCallbacks;

    /**
     * 初始化数据
     * @param activity 页面
     * @param callbacks 数据回调接口
     */
    public void onCreate(FragmentActivity activity, AlbumMediaCallbacks callbacks) {
        this.mContext = new WeakReference<Context>(activity);
        this.mLoaderManager = activity.getLoaderManager();
        this.mMediaCallbacks = callbacks;
    }

    /**
     * 开始加载
     * @param album
     */
    public void load(Album album) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ALBUM_KEY, album);
        mLoaderManager.initLoader(LOADER_ID, bundle, this);
    }
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Album album = args.getParcelable(ALBUM_KEY);
        if (album == null) {
            return null;
        }
        Context context = mContext.get();
        if (context == null) {
            return null;
        }
        return AlbumMediaLoader.newInstance(context, album);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mMediaCallbacks.onAlbumMediaLoad(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    /**
     * 查询数据回调
     */
    public interface AlbumMediaCallbacks {
        void onAlbumMediaLoad(Cursor cursor);
        void onAlbumMediaReset();
    }

    /**
     * 在页面销毁的时候需要destroy LoaderManager
     * 否则捕获数据为空
     */
    public void onDestroy() {
        if (mLoaderManager != null) {
            mLoaderManager.destroyLoader(LOADER_ID);
        }
        mMediaCallbacks = null;
    }

}
