package com.rick.monet.utils;

import android.graphics.Bitmap;
import android.util.LruCache;

import java.util.ArrayList;

/**
 *
 * Author: Rick
 * Email: zhiyuanfeng@rastar.com
 * Date: 2019/2/20
 */
public class BitmapCache {
    /**
     * 缓存类
     */
    private LruCache<String, Bitmap> mLruCache;
    /**
     * 缓存key
     */
    private ArrayList<String> mListKey;
    /**
     * 实例
     */
    private static BitmapCache mBitmapCache;

    private BitmapCache() {
        //设置缓存大小
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheMemory = maxMemory/8;
        mLruCache = new LruCache<>(cacheMemory);
        mListKey = new ArrayList<>();
    }

    /**
     * 获取单例
     * @return 图片缓存实例
     */
    public static BitmapCache getInstance() {
        if (mBitmapCache == null) {
            synchronized (BitmapCache.class) {
                if (mBitmapCache == null) {
                    mBitmapCache = new BitmapCache();
                }
            }
        }
        return mBitmapCache;
    }

    /**
     * 存储到缓存
     * @param key 标记
     * @param bitmap
     */
    public void setBitmap(String key, Bitmap bitmap) {
        mLruCache.put(key, bitmap);
        mListKey.add(key);
    }

    /**
     * 获取缓存
     * @param key 根据标记获取
     * @return
     */
    public Bitmap getBitmap(String key) {
        return mLruCache.get(key);
    }

    /**
     * 判断是否存在该key
     * @param key
     * @return
     */
    public boolean isKey(String key) {
        return mListKey.contains(key);
    }
}
