package com.rick.monet.entity;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Author: Rick
 * Email: zhiyuanfeng@rastar.com
 * Date: 2019/2/28
 */
public class MediaItem implements Parcelable {
    private String _data;
    public MediaItem(Cursor cursor) {
        set_data(cursor.getString(cursor.getColumnIndex("_data")));
    }


    protected MediaItem(Parcel in) {
        _data = in.readString();
    }

    public static final Creator<MediaItem> CREATOR = new Creator<MediaItem>() {
        @Override
        public MediaItem createFromParcel(Parcel in) {
            return new MediaItem(in);
        }

        @Override
        public MediaItem[] newArray(int size) {
            return new MediaItem[size];
        }
    };

    public String get_data() {
        return _data;
    }

    public void set_data(String _data) {
        this._data = _data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_data);
    }
}
