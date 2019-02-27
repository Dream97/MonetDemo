package com.rick.monet.entity;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;

import com.rick.monet.loader.AlbumLoader;

/**
 * 根据文件夹名称分类实体类
 * Author: Rick
 * Email: zhiyuanfeng@rastar.com
 * Date: 2019/2/27
 */
public class Album implements Parcelable {
    private String id;
    private String coverPath;
    private String displayName;
    private long count;

    public Album(Cursor cursor) {
        this(
                cursor.getString(cursor.getColumnIndex("bucket_id")),
                cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA)),
                cursor.getString(cursor.getColumnIndex("bucket_display_name")),
                cursor.getLong(cursor.getColumnIndex(AlbumLoader.COLUMN_COUNT)));
    }

    private Album(String id, String coverPath, String albumName, long count) {
        this.id = id;
        this.coverPath = coverPath;
        this.displayName = albumName;
        this.count = count;
    }

    /**
     * 实现Parcelable接口对Parcelable类解析
     * @param in 输入
     */
    public Album(Parcel in) {
        id = in.readString();
        coverPath = in.readString();
        displayName = in.readString();
        count = in.readLong();
    }

    public static final Creator<Album> CREATOR = new Creator<Album>() {
        @Override
        public Album createFromParcel(Parcel in) {
            return new Album(in);
        }

        @Override
        public Album[] newArray(int size) {
            return new Album[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCoverPath() {
        return coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(displayName);
        dest.writeString(coverPath);
        dest.writeLong(count);
    }
}
