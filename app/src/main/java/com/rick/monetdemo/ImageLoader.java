package com.rick.monetdemo;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.GenericTransitionOptions;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class ImageLoader {

    public static void displayImage(Context context, String uri, ImageView imageView) {
        displayImage(context, uri, imageView, false, false, -1, -1);
    }

    public static void displayImage(Context context, String uri, ImageView imageView,
                                    boolean isCircle) {
        displayImage(context, uri, imageView, isCircle, false , -1, -1);
    }

    public static void displayImage(Context context, Uri uri, ImageView imageView) {
        GlideApp.with(context).asDrawable()
                .load(uri)
                .thumbnail(0.1f)
                .transition(withCrossFade())
                .into(imageView);
    }




    public static void displayImage(Context context, String uri, ImageView imageView, boolean
            isCircle, int defaultIconId) {
        GlideApp.with(context).asDrawable()
                .load(uri)
                .thumbnail(0.1f)
                .placeholder(defaultIconId)
                .error(defaultIconId)
                .transition(withCrossFade())
                .into(imageView);
    }


    public static void displayImage(Context context, String uri, ImageView imageView, boolean
            isCircle, boolean border, float borderWidth, int borderColor) {

            GlideApp.with(context).asDrawable()
                    .load(uri)
                    .thumbnail(0.1f)
//                    .placeholder(R.mipmap.img_bg)
                    .error(R.mipmap.img_bg)
                    .placeholder(R.mipmap.img_bg)
//                    .transition(GenericTransitionOptions.with(R.anim.item_alpha))
                    .transition(withCrossFade())
                    .into(imageView);

    }
}
