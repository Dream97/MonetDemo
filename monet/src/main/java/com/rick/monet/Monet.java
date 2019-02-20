package com.rick.monet;

import android.app.Activity;
import android.support.v4.app.Fragment;

import java.lang.ref.WeakReference;

/**
 * Monet入口
 * Author: Rick
 * Email: zhiyuanfeng@rastar.com
 * Date: 2019/2/15
 */
public class Monet {
    private final WeakReference<Activity> mContext;
    private final WeakReference<Fragment> mFragment;

    private Monet(Activity activity) {
        this(activity, null);
    }

    /**
     * 当参数是fragment时，需要通过getActivity获取它所在的Activity
     * @param fragment 参数
     */
    private Monet(Fragment fragment) {
        this(fragment.getActivity(), fragment);
    }

    private Monet(Activity activity, Fragment fragment) {
        mContext = new WeakReference<>(activity);
        mFragment = new WeakReference<>(fragment);
    }
    public static Monet from(Activity activity) {
        return new Monet(activity);
    }

    public static Monet from(Fragment fragment) {
        return new Monet(fragment);
    }

    public MonetBuilder choose() {
        return new MonetBuilder(this);
    }

    Activity getActivity() {
        return mContext.get();
    }

    Fragment getFragment() {
        return mFragment != null ? mFragment.get() : null;
    }
}
