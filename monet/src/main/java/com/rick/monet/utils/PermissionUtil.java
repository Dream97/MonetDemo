package com.rick.monet.utils;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限请求工具类
 * Author: Rick
 * Email: zhiyuanfeng@rastar.com
 * Date: 2019/1/2
 */
public class PermissionUtil {
    /**
     * 检测权限
     * @param activity
     * @param view //随便一个view 来用显示snackbar
     * @param permissions  //请求的权限组
     * @param requestCode  //请求码
     */
    public  static void checkPermission(final Activity activity, View view, final String[] permissions, final int requestCode, permissionInterface permissionInterface) {
        //小于23 就什么都不做
        if (Build.VERSION.SDK_INT < 23) {
            return;
        }
        List<String> deniedPermissions = findDeniedPermissions(activity, permissions);  //没有授权的列表
        if (deniedPermissions!=null&&deniedPermissions.size()>0) {
            //大于0,表示有权限没申请
            PermissionUtil.requestContactsPermissions(activity, view,  deniedPermissions.toArray(new String[deniedPermissions.size()]), requestCode);
        } else {
            //拥有权限
            permissionInterface.success();

        }
    }

    /**
     * 请求权限
     */
    public static void requestContactsPermissions(final Activity activity, View view, final String[] permissions, final int requestCode) {
        //默认是false,但是只要请求过一次权限就会为true,除非点了不再询问才会重新变为false
        if (shouldShowPermissions(activity,permissions)) {

            Snackbar.make(view, "需要一些权限",
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ActivityCompat
                                    .requestPermissions(activity, permissions,
                                            requestCode);
                        }
                    })
                    .show();
        } else {

            // 无需向用户界面提示，直接请求权限,如果用户点了不再询问,即使调用请求权限也不会出现请求权限的对话框
            ActivityCompat.requestPermissions(activity,
                    permissions,
                    requestCode);
        }
    }

    /**
     * 判断请求权限是否成功
     * @param grantResults
     * @return
     */
    public static boolean verifyPermissions(int[] grantResults) {
        // At least one result must be checked.
        if(grantResults.length < 1){
            return false;
        }

        // Verify that each required permission has been granted, otherwise return false.
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;

    }

    //回调接口
    public interface permissionInterface{
        void success();
    }
    /**
     * 找到没有授权的权限
     * @param activity
     * @param permission
     * @return
     */
    public static List<String> findDeniedPermissions(Activity activity, String... permission)
    {
        //存储没有授权的权限
        List<String> denyPermissions = new ArrayList<>();
        for (String value : permission)
        {
            if (ContextCompat.checkSelfPermission(activity,value) != PackageManager.PERMISSION_GRANTED)
            {
                //没有权限 就添加
                denyPermissions.add(value);
            }
        }
        return denyPermissions;
    }

    /**
     * 检测这些权限中是否有 没有授权需要提示的
     * @param activity
     * @param permission
     * @return
     */
    public static boolean shouldShowPermissions(Activity activity, String... permission) {
        for (String value : permission) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    value)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 提示重新设置权限
     * @param activity
     * @param view
     * @param msg
     * @param permissions
     */
    public static void showSnackBar(Activity activity, View view, String msg, String[] permissions) {
        showSnackBar(activity, view, permissions, msg, null, null) ;
    }

    /**
     * 跳转到设置界面
     * @param activity
     * @param view
     * @param msg
     * @param tap
     * @param intent
     */
    public static void showSnackBar(Activity activity, View view, String msg, String tap, Intent intent) {
        showSnackBar(activity, view, null, msg, tap, intent) ;
    }


    public static void showSnackBar(final Activity activity, View view, final String[] permissions, String msg, String tap, final Intent intent) {
        Snackbar.make(view, msg,
                Snackbar.LENGTH_INDEFINITE)
                .setAction(tap, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (intent != null) {
                            activity.startActivityForResult(intent, ResultCode.REQUEST_PERMISSION_SETTING);
                        }else {
//                            activity.se
                        }
                    }
                })
                .show();
    }
}

