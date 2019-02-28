package com.rick.monet.ui.widget;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.ListPopupWindow;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.rick.monet.R;
import com.rick.monet.entity.Album;
/**
 * 管理toolbar右上角图库的展开
 * Author: Rick
 * Email: zhiyuanfeng@rastar.com
 * Date: 2019/2/26
 */
public class AlbumsSpinner {
    //PopupWindow最多展示6排
    private int MAX_ABLUM_COUNT = 6;
    private Context mContext ;
    //弹窗
    private ListPopupWindow mListPopupWindow;
    private TextView mView;
    //适配器
    private CursorAdapter mCursorAdapter;
    //item监听
    private AdapterView.OnItemSelectedListener mItemSelecterListener;

    public AlbumsSpinner(Context context) {
        this.mContext = context;
        mListPopupWindow = new ListPopupWindow(mContext);
        mListPopupWindow.setModal(true);
        float density = context.getResources().getDisplayMetrics().density;
        mListPopupWindow.setContentWidth((int) (216 * density));
        mListPopupWindow.setHorizontalOffset((int) (16 * density));
        mListPopupWindow.setVerticalOffset((int) (-48 * density));
        mListPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setItemSelect(position); //右上角设置相册文字
                mItemSelecterListener.onItemSelected(parent, view, position, id);
                mListPopupWindow.dismiss();
            }
        });
    }

    /**
     * 点击列表时更新
     * @param position
     */
    public void setItemSelect(int position) {
        Cursor cursor = mCursorAdapter.getCursor();
        cursor.moveToPosition(position);
        Album album = new Album(cursor);
        mView.setText(album.getDisplayName());
    }

    /**
     * 传入点击后展开PopupWindow的控件
     * @param view 控件
     */
    public void setShowView(TextView view) {
        this.mView = view;
        setPopupAnchorView(view);
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int height = v.getResources().getDimensionPixelOffset(R.dimen.monet_album_item_height);
//                mListPopupWindow.setHeight(mCursorAdapter.getCount() > MAX_ABLUM_COUNT ? MAX_ABLUM_COUNT : mCursorAdapter.getCount() * height);
                mListPopupWindow.setHeight(MAX_ABLUM_COUNT * height);
                mListPopupWindow.show();
            }
        });
    }

    /**
     * 设置Cursor适配器
     * @param cursorAdapter 针对Cursor系统自带的适配器
     */
    public void setAdapter(CursorAdapter cursorAdapter) {
        this.mCursorAdapter = cursorAdapter;
        mListPopupWindow.setAdapter(cursorAdapter);
    }

    /**
     *
     * 设置参照控件
     *  Attempt to invoke virtual method 'void android.view.View.getWindowVisibleDisplayFrame(android.graphics.Rect)' on a null object reference
     * @param view
     */
    public void setPopupAnchorView(View view) {
        mListPopupWindow.setAnchorView(view);
    }

    /**
     * 设置弹窗列表选择监听
     * @param itemListener
     */
    public void setItemListener(AdapterView.OnItemSelectedListener itemListener) {
        this.mItemSelecterListener = itemListener;
    }

}
