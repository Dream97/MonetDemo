package com.rick.monet.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.rick.monet.R;
import com.rick.monet.model.MonetSpec;

import java.util.ArrayList;

/**
 * Author: Rick
 * Email: zhiyuanfeng@rastar.com
 * Date: 2019/2/27
 */
public class AlbumMediaAdapter extends RecyclerView.Adapter<AlbumMediaAdapter.MediaHolder> {

    private Context mContext;
    private Cursor mCursor;
    private ArrayList<String> mList = new ArrayList<>();
    public AlbumMediaAdapter(Context context) {
        this.mContext = context;
    }

    public AlbumMediaAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        this.mCursor = cursor;
        do {
            mList.add(cursor.getString(cursor.getColumnIndex("_data")));
        }while (cursor.moveToNext());

    }

    @NonNull
    @Override
    public MediaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull MediaHolder holder, int position) {
        MonetSpec.getInstance().getmImageEngine().loadImage(mContext, mList.get(position), holder.mIvCover);
    }

    class MediaHolder extends RecyclerView.ViewHolder{
        private ImageView mIvCover;
        public MediaHolder(View itemView) {
            super(itemView);
            mIvCover = itemView.findViewById(R.id.monet_iv_item_cover);
        }
    }
}
