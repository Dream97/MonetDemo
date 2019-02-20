package com.rick.monet.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 正方形图片
 * Author: Rick
 * Email: zhiyuanfeng@rastar.com
 * Date: 2019/2/18
 */
@SuppressLint("AppCompatCustomView")
public class MonetImage extends ImageView {
    private Bitmap mBitmap;
    public MonetImage(Context context) {
        super(context);
    }

    public MonetImage(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MonetImage(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        this.mBitmap = bm;
        super.setImageBitmap(bm);
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }
}
