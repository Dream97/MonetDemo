package com.rick.monet.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.rick.monet.R;

/**
 * Author: Rick
 * Email: zhiyuanfeng@rastar.com
 * Date: 2019/2/22
 */
@SuppressLint("AppCompatCustomView")
public class BucketNameTextView extends TextView {
    int mRightWith;
    int mRightHeight;
    int mLeft;
    public BucketNameTextView(Context context) {
        this(context, null);
    }

    public BucketNameTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BucketNameTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Drawable drawableBottom = null;
        Drawable drawableTop = null;
        Drawable drawableLeft = null;
        Drawable drawableRight = null;
        if (attrs != null) {
            float scale = context.getResources().getDisplayMetrics().density;
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MonetTextView);
            int n = a.getIndexCount();
            for (int i = 0; i < n; i++) {
                int attr = a.getIndex(i);
                if (attr == R.styleable.MonetTextView_Monet_drawableBottom) {
                    drawableBottom = a.getDrawable(attr);

                } else if (attr == R.styleable.MonetTextView_Monet_drawableTop) {
                    drawableTop = a.getDrawable(attr);

                } else if (attr == R.styleable.MonetTextView_Monet_drawableLeft) {
                    drawableLeft = a.getDrawable(attr);

                } else if (attr == R.styleable.MonetTextView_Monet_drawableRight) {
                    drawableRight = a.getDrawable(attr);

                } else if (attr == R.styleable.MonetTextView_Monet_drawableRightWidth) {
                    mRightWith = (int) (a.getDimension(attr, 20) * scale + 0.5f);

                } else if (attr == R.styleable.MonetTextView_Monet_drawableRightHeight) {
                    mRightHeight = (int) (a.getDimension(attr, 20) * scale + 0.5f);

                } else if (attr == R.styleable.MonetTextView_Monet_left){
                    mLeft = (int) (a.getDimension(attr, 20) * scale + 0.5f);

                }
            }
            a.recycle();
            setCompoundDrawablesWithIntrinsicBounds(drawableLeft, drawableTop, drawableRight, drawableBottom);
        }
    }

    @Override
    public void setCompoundDrawablesWithIntrinsicBounds(Drawable left, Drawable top, Drawable right, Drawable bottom) {
        if (right != null) {
            right.setBounds(0, 0, mRightWith <= 0 ? right.getIntrinsicWidth() : mRightWith, mRightHeight <= 0 ? right.getMinimumHeight() : mRightHeight);
        }
        setCompoundDrawables(left, top, right, bottom);
    }

}
