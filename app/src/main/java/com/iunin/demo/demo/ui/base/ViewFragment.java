package com.iunin.demo.demo.ui.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.iunin.demo.demo.ui.widget.ContentLoadingDialog;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 视图页面的基类
 *
 * @author leon@iunin.com
 */

public class ViewFragment extends Fragment {
    private IPageDirector.Scene mSceneDirector = IPageDirector.EMPTY_SCENE;
    private ContentLoadingDialog mWaitingDialog;
    protected static final int DEFAULT_WAITTING_DELAY = 300;
    private Field mPresenterField;

    @CallSuper
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initReflect();
    }

    private void initReflect() {
        Field[] fields = getClass().getDeclaredFields();
        for (Field field : fields) {
            if ("mPresenter".equals(field.getName())) {
                mPresenterField = field;
                mPresenterField.setAccessible(true);
                break;
            }
        }
    }

    /**
     * 设置页面控制器
     *
     * @param director
     */
    public void setSceneDirector(IPageDirector.Scene director) {
        if (director == null) {
            director = IPageDirector.EMPTY_SCENE;
        }
        mSceneDirector = director;
    }

    /**
     * 获取页面控制器
     *
     * @return
     */
    protected IPageDirector.Scene getSceneDirector() {
        return mSceneDirector;
    }

    /**
     * 显示第一页面
     *
     * @return 切换成功返回 true，否则 false
     */
    protected boolean showFirstPage() {
        return mSceneDirector.showFirstPage();
    }

    /**
     * 切换到上一页
     *
     * @return 切换成功返回 true，否则 false
     */
    protected boolean showPrevPage() {
        return mSceneDirector.showPrevPage(this, null, null);
    }

    /**
     * 切换到下一页
     *
     * @return 切换成功返回 true，否则 false
     */
    protected boolean showNextPage() {
        return mSceneDirector.showNextPage(this, null, null);
    }

    /**
     * 切换到指定的页面
     *
     * @return 切换成功返回 true，否则 false
     */
    protected boolean showPage(Class toPage, Fragment fromPage, Bundle params, List<Pair> sharedElements) {
        return mSceneDirector.showPage(toPage, fromPage, params, sharedElements);
    }

    /**
     * 判断页面是否是活动状态
     * @return
     */
    public boolean isActive() {
        return isAdded();
    }

    protected void showWaitingDialogWithDelay(String message) {
        setWaitingDialogDelayShow(DEFAULT_WAITTING_DELAY);
        showWaitingDialog(message);
    }

    /**
     * 显示等待对话框
     *
     * @param message 提示的信息
     */
    protected void showWaitingDialog(String message) {
        showWaitingDialog(message, 0);
    }

    /**
     * 显示等待对话框
     *
     * @param message 提示的信息
     * @param actionId  等待的动作的 ID
     */
    protected void showWaitingDialog(String message, int actionId) {
        if (!isAdded()) return;
        if (mWaitingDialog == null) {
            mWaitingDialog = ViewUtils.createLoadingDialog(getActivity(), null, null);
            mWaitingDialog.setOnCancelListener(mOnCancelListener);
        }
        mWaitingDialog.setActionId(actionId);
        mWaitingDialog.setCancelable(actionId != 0);
        mWaitingDialog.setMessage(message);
        mWaitingDialog.showLoading();
    }

    /**
     * 隐藏等待对话框
     */
    protected void hideWaitingDialog() {
        if (mWaitingDialog == null) return;
        mWaitingDialog.dismiss();
    }

    /**
     *  取消等待对话框
     *
     *  用户按返回键取消等待对话框
     */
    private void cancelWaitingDialog() {
        if (mWaitingDialog == null) return;
        if (mWaitingDialog.isShowing()) {
            mWaitingDialog.cancel();
        }
    }

    /**
     * 设置对话框的显示的延迟时间
     * @param delayMs 延迟时间，单位：毫秒
     */
    protected void setWaitingDialogDelayShow(int delayMs) {
        if (mWaitingDialog == null) {
            mWaitingDialog = ViewUtils.createLoadingDialog(getActivity(), null, null);
            mWaitingDialog.setOnCancelListener(mOnCancelListener);
        }
        mWaitingDialog.setDelayShowTime(delayMs);
    }

    private Dialog.OnCancelListener mOnCancelListener = new DialogInterface.OnCancelListener() {
        @Override
        public void onCancel(DialogInterface dialog) {
            if (dialog instanceof ContentLoadingDialog) {
                cancelAction(((ContentLoadingDialog) dialog).getActionId());
            }
        }
    };

    /**
     * 用户取消等待对话时的回调接口
     *
     * @param actionId 取消动作的 ID
     */
    protected void cancelAction(int actionId) {

    }

    /**
     * 显示短时 Toast
     *
     * @param message 提示信息
     */
    protected void showShortToast(String message) {
        ViewUtils.showShortToast(message);
    }

    /**
     * 显示长时 Toast
     * @param message 提示信息
     */
    protected void showLongToast(String message) {
        ViewUtils.showLongToast(message);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ViewFragmentActivity) {
            setSceneDirector(((ViewFragmentActivity) context).getScene());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        setSceneDirector(null);
    }

    public void onStart() {
        super.onStart();
        if (mPresenterField != null) {
            Object val = null;
            try {
                val = mPresenterField.get(this);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (val == null) {
                showShortToast("加载页面数据出错，页面已关闭，请重新打开");
                finishActivity();
                return;
            }
        }
    }

    public void onDestroy() {
        super.onDestroy();
        cancelWaitingDialog();
    }

    /**
     * 终止当前页面
     */
    protected void finishActivity() {
        if (getActivity() != null) {
            getActivity().finish();
        }
    }

    /**
     * 设置标题文本
     *
     * @param title 标题文本
     */
    protected void setTitle(String title) {
        if (title == null) return;
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setTitle(title);
    }

    /**
     * 设置工具栏
     *
     * @param rootView 页面的根视图
     * @param resourceId 工具栏的资源 ID
     * @param displayHome 是否显示返回按钮
     * @return 设置的工具栏对象
     */
    protected Toolbar setToolbar(@NonNull View rootView,
                                 int resourceId, boolean displayHome) {
        Toolbar toolbar = (Toolbar) rootView.findViewById(resourceId);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        toolbar.setTitle(null);
        activity.setSupportActionBar(toolbar);
        activity.setTitle(null);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(displayHome);
        if (displayHome) {
            setHasOptionsMenu(true);
        }
        return toolbar;
    }

    /**
     * 隐藏软键盘
     */
    protected void hideSoftKeyboard() {
        if (!isAdded() || getView() == null) return;
        InputMethodManager imeMgr = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imeMgr == null) return;
        imeMgr.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }
}
