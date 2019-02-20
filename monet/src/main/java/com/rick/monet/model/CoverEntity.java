package com.rick.monet.model;

import android.provider.MediaStore;

/**
 * Author: Rick
 * Email: zhiyuanfeng@rastar.com
 * Date: 2019/2/18
 */
public class CoverEntity  {
//    MediaStore.Files.FileColumns._ID,
//            "bucket_id",
//            "bucket_display_name",
//    MediaStore.MediaColumns.DATA,
//    COLUMN_COUNT
    private String _id;
    private String bucket_id;
    private String bucket_display_name;
    private String _data;
    private String count;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getBucket_id() {
        return bucket_id;
    }

    public void setBucket_id(String bucket_id) {
        this.bucket_id = bucket_id;
    }

    public String getBucket_display_name() {
        return bucket_display_name;
    }

    public void setBucket_display_name(String bucket_display_name) {
        this.bucket_display_name = bucket_display_name;
    }

    public String get_data() {
        return _data;
    }

    public void set_data(String _data) {
        this._data = _data;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
