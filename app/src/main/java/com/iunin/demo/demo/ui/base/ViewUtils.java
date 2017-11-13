package com.iunin.demo.demo.ui.base;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.iunin.MyApplication;
import com.iunin.demo.demo.ui.widget.ContentLoadingDialog;


/**
 * Created by leon@iunin.com on 9/5/15.
 */
public final class ViewUtils {

    /**
     * 创建等待对话框
     *
     * @param context
     * @param title 对话框标题
     * @param tips 对话框提示信息
     * @return 对话框对象
     */
    public static ContentLoadingDialog createLoadingDialog(Context context, String title,
                                                             String tips) {
        ContentLoadingDialog dialog = new ContentLoadingDialog(context);
        dialog.setTitle(title);
        dialog.setMessage(tips);
        dialog.setInverseBackgroundForced(true);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    private static Context sContext;
    private static Toast sToast;

    private static Context getContext() {
        if (sContext == null) {
            sContext = MyApplication.getInstance();
        }
        return sContext;
    }

    /**
     * 显示短时 Toast
     * @param text 提示信息
     */
    public static void showShortToast(String text) {
        showToast(getContext(), text, Toast.LENGTH_SHORT);
    }

    /**
     * 显示长时 Toast
     *
     * @param text
     */
    public static void showLongToast(String text) {
        showToast(getContext(), text, Toast.LENGTH_LONG);
    }

    private static void showToast(Context context, String text, int length) {
        if (sToast == null) {
            sToast = Toast.makeText(context, text, length);
        } else {
            sToast.setText(text);
            sToast.setDuration(length);
        }
        sToast.show();
    }

    /**
     * 隐藏设备编号中间数
     *
     * @param device_id
     * @return
     */
    public static String getHideId(String device_id) {
        if (device_id != null && device_id.length() == 15) {
            String headerNumber = device_id.substring(0, 4);
            String lastNumber = device_id.substring(device_id.length() - 4, device_id.length());
            return headerNumber + "*******" + lastNumber;
        }
        return "";
    }

    public static boolean isShowingFragmentDialog(FragmentManager fragmentManager, String tag) {
        return fragmentManager.findFragmentByTag(tag) != null;
    }

    public static void hideFragmentDialog(FragmentManager fragmentManager, String tag) {
        Fragment prev = fragmentManager.findFragmentByTag(tag);
        if (prev instanceof DialogFragment) {
            ((DialogFragment) prev).dismiss();
        }
        if (prev != null) {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.remove(prev);
            ft.commit();
        }
    }

    public static void showFragmentDialog(FragmentManager fragmentManager, DialogFragment dialog, String tag) {
        showFragmentDialog(fragmentManager, dialog, tag, true);
    }

    public static void showFragmentDialog(FragmentManager fragmentManager, DialogFragment dialog,
                                          String tag, boolean replace) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment prev = fragmentManager.findFragmentByTag(tag);
        if (prev != null) {
            if (!replace) return;
            ft.remove(prev);
            ft.commit();
        }

        ft = fragmentManager.beginTransaction();
        ft.addToBackStack(null);
        dialog.show(ft, tag);
    }

    public static void hideInputMethod(Context context, View view) {
        InputMethodManager imeMgr = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imeMgr.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void setAlpha(View view, float alpha) {
        AlphaAnimation animation = new AlphaAnimation(alpha, alpha);
        animation.setDuration(0);
        animation.setFillAfter(true);
        view.startAnimation(animation);
    }
}
