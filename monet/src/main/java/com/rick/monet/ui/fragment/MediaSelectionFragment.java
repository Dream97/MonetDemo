package com.rick.monet.ui.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rick.monet.R;

/**
 * Author: Rick
 * Email: zhiyuanfeng@rastar.com
 * Date: 2019/2/25
 */
public class MediaSelectionFragment extends Fragment {
    private static MediaSelectionFragment mInstance;

    public MediaSelectionFragment getInstance() {
        if (mInstance == null) {
            synchronized (MediaSelectionFragment.class) {
                if (mInstance == null) {
                    mInstance = new MediaSelectionFragment();
                }
            }
        }
        return mInstance;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.monet_fragment_media_selection, container, false);
    }
}
