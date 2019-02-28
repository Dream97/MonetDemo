package com.rick.monet.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rick.monet.R;
import com.rick.monet.entity.Album;
import com.rick.monet.model.AlbumMediaCollection;
import com.rick.monet.ui.adapter.AlbumMediaAdapter;
import com.rick.monet.ui.widget.MediaItemDecoration;

/**
 * 展示所选图库页
 * Author: Rick
 * Email: zhiyuanfeng@rastar.com
 * Date: 2019/2/25
 */
public class MediaSelectionFragment extends Fragment implements AlbumMediaCollection.AlbumMediaCallbacks , AlbumMediaAdapter.MediaItemListener {
    private static MediaSelectionFragment mInstance;
    private RecyclerView mRvCover;
    private static String ALBUM_KEY = "album";
    private AlbumMediaCollection mAlbumMediaCollection = new AlbumMediaCollection();
    private AlbumMediaAdapter mAlbumMediaAdapter;
    private AlbumMediaAdapter.MediaItemListener mMediaItemListener;
    public static MediaSelectionFragment getInstance(Album album) {
        mInstance = new MediaSelectionFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ALBUM_KEY, album);
        mInstance.setArguments(bundle);
        return mInstance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.monet_fragment_media_selection, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRvCover = view.findViewById(R.id.monet_rv_fragment_cover);
        mRvCover.setHasFixedSize(true);
        int spacing = getResources().getDimensionPixelSize(R.dimen.monet_media_grid_spacing);
//        mRvCover.addItemDecoration(new MediaItemDecoration(spacing));
        mAlbumMediaCollection.onCreate(getActivity(), this);
        Album album = getArguments().getParcelable(ALBUM_KEY);
        mAlbumMediaCollection.load(album);
        initView();
    }

    /**
     * 初始化页面
     */
    private void initView() {
        mRvCover.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mAlbumMediaAdapter = new AlbumMediaAdapter(getContext());
        mRvCover.setAdapter(mAlbumMediaAdapter);
        mAlbumMediaAdapter.registerMediaItemListener(this);
    }

    @Override
    public void onAlbumMediaLoad(Cursor cursor) {
        mAlbumMediaAdapter = new AlbumMediaAdapter(getContext(), cursor);
        mAlbumMediaAdapter.registerMediaItemListener(this);
        mRvCover.setAdapter(mAlbumMediaAdapter);
    }

    @Override
    public void onAlbumMediaReset() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAlbumMediaCollection.onDestroy();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AlbumMediaAdapter.MediaItemListener) {
            mMediaItemListener = (AlbumMediaAdapter.MediaItemListener) context;
        }
    }

    @Override
    public void onMediaClick(Cursor cursor) {
        if (mMediaItemListener != null) {
            mMediaItemListener.onMediaClick(cursor);
        }
    }
}
