package com.rick.monet.ui.widget;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Author: Rick
 * Email: zhiyuanfeng@rastar.com
 * Date: 2019/2/28
 */
public class MediaItemDecoration extends RecyclerView.ItemDecoration {
    private int mSpace;
    public MediaItemDecoration (int space) {
        this.mSpace = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = mSpace;
        outRect.right = mSpace;
        outRect.top = mSpace;
        outRect.bottom = mSpace;
    }
}
