package com.rick.monet.ui.fragment;

import android.annotation.SuppressLint;
import android.database.Cursor;
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

/**
 * 展示所选图库页
 * Author: Rick
 * Email: zhiyuanfeng@rastar.com
 * Date: 2019/2/25
 */
public class MediaSelectionFragment extends Fragment implements AlbumMediaCollection.AlbumMediaCallbacks {
    private static MediaSelectionFragment mInstance;
    private RecyclerView mRvCover;
    private static String ALBUM_KEY = "album";
    private AlbumMediaCollection mAlbumMediaCollection;
    private AlbumMediaAdapter albumMediaAdapter;

    public MediaSelectionFragment getInstance(Album album) {
        if (mInstance == null) {
            synchronized (MediaSelectionFragment.class) {
                if (mInstance == null) {
                    mInstance = new MediaSelectionFragment();
                }
            }
        }
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
        albumMediaAdapter = new AlbumMediaAdapter(getContext());
        mRvCover.setAdapter(albumMediaAdapter);
    }

    @Override
    public void onAlbumMediaLoad(Cursor cursor) {
        albumMediaAdapter = new AlbumMediaAdapter(getContext(), cursor);
        mRvCover.setAdapter(albumMediaAdapter);
    }

    @Override
    public void onAlbumMediaReset() {

    }
}
