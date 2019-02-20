package com.rick.monet;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.rick.monet.model.MonetSpec;
import com.rick.monet.ui.MonetActivity;
import com.rick.monet.utils.ImageEngine;

/**
 * 建造者模式 便于添加更多属性
 * Author: Rick
 * Email: zhiyuanfeng@rastar.com
 * Date: 2019/2/15
 */
public class MonetBuilder {
    private Monet mMonet;
    MonetBuilder(Monet monet) {
        this.mMonet = monet;
    }

    public MonetBuilder setEngine(ImageEngine imageEngine) {
        MonetSpec.getInstance().setmImageEngine(imageEngine);
        return this;
    }

    /**
     * 执行跳转到图片选择器页面
     * @param requestCode 当前请求Activity/Fragment标志
     */
    public void start(int requestCode) {
        Activity activity = mMonet.getActivity();
        if(activity == null) {
            return;
        }

        Fragment fragment = mMonet.getFragment();
        Intent intent = new Intent(activity, MonetActivity.class);
        if (fragment == null) {
            activity.startActivityForResult(intent, requestCode);
        } else {
            fragment.startActivityForResult(intent, requestCode);
        }
    }


}
