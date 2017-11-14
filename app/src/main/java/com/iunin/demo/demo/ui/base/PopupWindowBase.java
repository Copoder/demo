package com.iunin.demo.demo.ui.base;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;


public abstract class PopupWindowBase extends PopupWindow {

    protected Activity mActivity;
    protected LayoutInflater inflater;
    protected Context context;
    protected int displayWidth, displayHeight;
    protected View titleView, rootView;
    protected Drawable backgroundDrawable;
    protected int headingBarHeight;

    public PopupWindowBase(Context context) {
        this.mActivity = (Activity) context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        requestFocus();
    }


    protected abstract void initView();

    protected abstract void bindListener();


    protected void requestFocus() {// Common logic for all subclasses
//        setFocusable(true);
        setTouchable(true);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setOutsideTouchable(true);
    }


    protected int getDisplayWidth() {
        if (displayWidth == 0) {
            displayWidth = mActivity.getResources().getDisplayMetrics().widthPixels;
        }
        return displayWidth;
    }

    protected void setDisplayWidth(int displayWidth) {
        this.displayWidth = displayWidth;
    }

    protected int getDisplayHeight() {
        if (displayHeight == 0) {
            displayHeight = mActivity.getResources().getDisplayMetrics().heightPixels;
        }
        return displayHeight;
    }

    protected void setDisplayHeight(int displayHeight) {
        this.displayHeight = displayHeight;
    }


}
