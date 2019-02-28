package com.rick.monet.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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
    private MediaItemListener mMediaItemListener;
    public AlbumMediaAdapter(Context context) {
        this.mContext = context;
    }

    public AlbumMediaAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        this.mCursor = cursor;
        while (cursor.moveToNext()){
            mList.add(cursor.getString(cursor.getColumnIndex("_data")));
        }

    }

    @NonNull
    @Override
    public MediaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(mContext).inflate(R.layout.monet_item_gv_cell, parent, false);
        return new MediaHolder(view);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull MediaHolder holder, final int position) {
        MonetSpec.getInstance().getmImageEngine().loadImage(mContext, mList.get(position), holder.mIvCover);
        holder.mIvCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCursor != null) {
                    mCursor.moveToFirst();
                    mCursor.moveToPosition(position);
                    mMediaItemListener.onMediaClick(mCursor);
                }
            }
        });
    }

    class MediaHolder extends RecyclerView.ViewHolder{
        private ImageView mIvCover;
        public MediaHolder(View itemView) {
            super(itemView);
            mIvCover = itemView.findViewById(R.id.monet_iv_item_grid_cover);
        }
    }

    public void registerMediaItemListener(MediaItemListener mediaItemListener) {
        this.mMediaItemListener = mediaItemListener;
    }

    /**
     * 定义点击事件回调接口
     */
    public interface MediaItemListener {
        /**
         * 点击图片时调用
         * @param cursor 浮标数据
         */
        void onMediaClick(Cursor cursor);
    }
}
