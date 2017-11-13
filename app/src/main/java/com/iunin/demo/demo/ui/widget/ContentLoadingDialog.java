package com.iunin.demo.demo.ui.widget;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;


/**
 * 等待对话框
 * 
 * Created by leon@iunin.com on 6/6/15.
 */
public class ContentLoadingDialog extends ProgressDialog {

    private static final long MIN_SHOW_DURATION = 600;
    private Handler mHandler;
    private int mDelayShowTime = 0;
    private int mActionId = 0;
    private boolean mIsCanceled = false;
    private long mShowTimeStamp = 0;

    public ContentLoadingDialog(Context context) {
        super(context);
        setCancelable(false);
        mHandler = new Handler(Looper.getMainLooper());
    }

    private Runnable mDelayShowRunnable = new Runnable() {
        @Override
        public void run() {
            show();
        }
    };

    private Runnable mDelayDismissRunnable = new Runnable() {
        @Override
        public void run() {
            ContentLoadingDialog.super.dismiss();
        }
    };

    private void clearDelayRunnables() {
        mHandler.removeCallbacks(mDelayShowRunnable);
        mHandler.removeCallbacks(mDelayDismissRunnable);
    }

    public void showLoading() {
        showLoading(mDelayShowTime);
        mDelayShowTime = 0;
    }

    public void showLoading(int delayMillis) {
        clearDelayRunnables();
        mHandler.postDelayed(mDelayShowRunnable, delayMillis);
    }

    public void setDelayShowTime(int delayShowTime) {
        mDelayShowTime = delayShowTime;
    }

    public void dismiss() {
        clearDelayRunnables();
        try {
            if (mIsCanceled) {
                super.dismiss();
            } else {
                long showDuration = System.currentTimeMillis() - mShowTimeStamp;
                if (showDuration < MIN_SHOW_DURATION) {
                    mHandler.postDelayed(mDelayDismissRunnable, MIN_SHOW_DURATION - showDuration);
                } else {
                    super.dismiss();
                }
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public void show() {
        mIsCanceled = false;
        mShowTimeStamp = System.currentTimeMillis();
        if (getContext() instanceof Activity) {
            if (((Activity)getContext()).isDestroyed()) {
                return;
            }
        } else {
            if (getWindow() == null) {
                return;
            }
        }
        super.show();
    }

    public void cancel() {
        mIsCanceled = true;
        clearDelayRunnables();
        super.cancel();
    }

    public int getActionId() {
        return mActionId;
    }

    public void setActionId(int actionId) {
        mActionId = actionId;
    }
}
