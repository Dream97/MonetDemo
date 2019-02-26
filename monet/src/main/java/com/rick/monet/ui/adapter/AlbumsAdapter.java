package com.rick.monet.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rick.monet.R;
import com.rick.monet.model.MonetSpec;

/**
 * Author: Rick
 * Email: zhiyuanfeng@rastar.com
 * Date: 2019/2/26
 */
public class AlbumsAdapter extends CursorAdapter {
    public AlbumsAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.monet_item_album_list, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ImageView cover = view.findViewById(R.id.monet_iv_item_cover);
        TextView tvName = view.findViewById(R.id.monet_tv_item_bucket_name);
        TextView tvCount = view.findViewById(R.id.monet_tv_item_bucket_count);
        MonetSpec.getInstance().getmImageEngine().loadImage(context, cursor.getString(cursor.getColumnIndex("_data")), cover);
        tvName.setText(cursor.getString(cursor.getColumnIndex("bucket_display_name")));
        tvCount.setText(cursor.getString(cursor.getColumnIndex("bucket_id")));
    }
}
