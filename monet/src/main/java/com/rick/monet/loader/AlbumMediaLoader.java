package com.rick.monet.loader;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.rick.monet.entity.Album;

/**
 * 加载对应相册分类
 * Author: Rick
 * Email: zhiyuanfeng@rastar.com
 * Date: 2019/2/27
 */
public class AlbumMediaLoader extends CursorLoader {

    private static final Uri QUERY_URI = MediaStore.Files.getContentUri("external");
    private static final String[] PROJECTION = { //列名
            MediaStore.Files.FileColumns._ID,
            MediaStore.MediaColumns.DISPLAY_NAME,
            MediaStore.MediaColumns.MIME_TYPE,
            MediaStore.MediaColumns.SIZE,
            MediaStore.MediaColumns.DATA,
            "duration"};

    //======================================根据文件夹id查询图册===================================
    private static final String SELECTION_ALBUM = //筛选条件
            "(" + MediaStore.Files.FileColumns.MEDIA_TYPE + "=?"
                    + " OR "
                    + MediaStore.Files.FileColumns.MEDIA_TYPE + "=?)"
                    + " AND "
                    + " bucket_id=?"
                    + " AND " + MediaStore.MediaColumns.SIZE + ">0";

    private static String[] getSelectionAlbumArgs(String albumId) { //条件参数
        return new String[]{
                String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE),
                String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO),
                albumId
        };
    }

    //============================================================================================

    //====================================查询所有图册============================================
    private static final String SELECTION_ALBUM_ALL=
            "(" + MediaStore.Files.FileColumns.MEDIA_TYPE +"=?"
            + " OR "
            + MediaStore.Files.FileColumns.MEDIA_TYPE + "=?)"
            + " AND " + MediaStore.MediaColumns.SIZE + ">0";

    private static String[] SELECTION_ALBUM_ALL_ARGS = {
                String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE),
                String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO)
            };
    //===========================================================================================

    public AlbumMediaLoader(Context context, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        super(context, uri, projection, selection, selectionArgs, sortOrder);
    }

    /**
     * 获取实例，并开始查询
     * @param context  上下文
     * @param album 当前所选相册类
     * @return
     */
    public static AlbumMediaLoader newInstance(Context context, Album album) {
        if (album.getId().equals("-1")) {
            return new AlbumMediaLoader(context, QUERY_URI, PROJECTION, SELECTION_ALBUM_ALL, SELECTION_ALBUM_ALL_ARGS, null);
        } else {
            return new AlbumMediaLoader(context, QUERY_URI, PROJECTION, SELECTION_ALBUM, getSelectionAlbumArgs(album.getId()), null);
        }
    }
}
