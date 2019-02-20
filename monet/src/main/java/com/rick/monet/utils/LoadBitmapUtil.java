package com.rick.monet.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Author: Rick
 * Email: zhiyuanfeng@rastar.com
 * Date: 2019/2/20
 */
public class LoadBitmapUtil {
    private List<String> mPathList;
    private List<LoadBitmapAsyncTask> mAsyncTasks = new ArrayList<>(); //存放所有异步操作
    /**
     * 构造函数
     * //     * @param absListView 继承自AbsListView
     *
     * @param pathList 路径列表
     */
    public LoadBitmapUtil(List<String> pathList) {
//        this.mAbsListView = absListView;
        this.mPathList = pathList;
    }

    /**
     * 加载Bitmap
     *
     * @param start 始启点
     * @param end   末点
     */
    public void load(AbsListView absListView, int start, int end) {
        List<String> list = mPathList.subList(start, end);
        for (String path : list) {
            ImageView imageView = absListView.findViewWithTag(path);
            LoadBitmapAsyncTask asyncTask = new LoadBitmapAsyncTask(imageView);
            asyncTask.execute(path);
            mAsyncTasks.add(asyncTask);
        }
    }

    /**
     * 取消加载图片
     */
    public void cancel() {
        for (LoadBitmapAsyncTask asyncTask : mAsyncTasks) {
            if (!asyncTask.isCancelled()) {
                asyncTask.cancel(true);
            }
        }
    }


    /**
     * 异步任务
     */
    class LoadBitmapAsyncTask extends AsyncTask<String,Void, Bitmap> {
        private ImageView mImg;
        public LoadBitmapAsyncTask(ImageView imageView) {
            this.mImg = imageView;
        }
        @Override
        protected Bitmap doInBackground(String... path) {
            if (BitmapCache.getInstance().getBitmap(path[0]) != null) {

                Log.d("Bitmap来源", "Cache ============================");
                return BitmapCache.getInstance().getBitmap(path[0]);
            } else {
                Log.d("Bitmap来源", "BitmapFactory ============================");
                Uri uri = Uri.parse(path[0]);

                //--------------------获取图片原始信息，计算采样率------------
                BitmapFactory.Options optionsTest = new BitmapFactory.Options();
                optionsTest.inJustDecodeBounds = true;//这个参数设置为true才有效，
                Bitmap bmp = BitmapFactory.decodeFile(path[0], optionsTest);//这里的bitmap是个空
                int scale  = 1;
                int outHeight=optionsTest.outHeight;
                int imgHeight = mImg.getHeight();
                scale = outHeight/imgHeight;

                int outWidth= optionsTest.outWidth;
                int imgWidth = mImg.getWidth();
                scale = scale < (outWidth/imgHeight) ? scale : outWidth/imgWidth;
//                if (scale != 0) {
//                    Log.e("outHeight",outHeight/scale + "==="+imgHeight);
//                    Log.e("outWidth",outWidth/scale + "==="+imgWidth);
//                }
                //-----------------获取图片原始信息，计算采样率------------

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = scale;
                try {
                    Bitmap bitmap = BitmapFactory.decodeFile(uri.getPath(), options);
                    if (bitmap != null) {
                        BitmapCache.getInstance().setBitmap(path[0], bitmap);
                        return BitmapCache.getInstance().getBitmap(path[0]);
                    }
                } catch (OutOfMemoryError error) {
                    Log.d("OOM", path[0]+"============================");
                    return null;
                }
            }
            return null;
        }

        //用于更新UI, 代替普通线程中Handler操作
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                mImg.setImageBitmap(bitmap);
            }
            super.onPostExecute(bitmap);
        }
    }
}
