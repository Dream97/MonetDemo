package com.rick.monet.model;

import com.rick.monet.Monet;
import com.rick.monet.utils.ImageEngine;

/**
 * 配置信息
 * Author: Rick
 * Email: zhiyuanfeng@rastar.com
 * Date: 2019/2/19
 */
public class MonetSpec {
    private static MonetSpec mMonetSpec;
    private ImageEngine mImageEngine;
    private int RequestCode;
    private MonetSpec() {
    }

    public static MonetSpec getInstance() {
        if (mMonetSpec == null) {
            synchronized (MonetSpec.class) {
                if (mMonetSpec == null) {
                    mMonetSpec = new MonetSpec();
                }
            }
        }
        return mMonetSpec;
    }

    public int getRequestCode() {
        return RequestCode;
    }

    public void setRequestCode(int requestCode) {
        RequestCode = requestCode;
    }

    public ImageEngine getmImageEngine() {
        return mImageEngine;
    }

    public void setmImageEngine(ImageEngine mImageEngine) {
        this.mImageEngine = mImageEngine;
    }
}
