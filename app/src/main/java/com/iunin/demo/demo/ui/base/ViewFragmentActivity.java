package com.iunin.demo.demo.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

import com.iunin.demo.demo.R;


/**
 * 视图页面的Activity
 *
 * @author leon@iunin.com
 */

public abstract class  ViewFragmentActivity extends AppCompatActivity {

    private PageDirector mPageDirector;
    protected int mRootLayout;


    public ViewFragmentActivity() {
        mPageDirector = new PageDirector(this, R.id.content);
        mPageDirector.setSceneListener(sSceneListenerProxy);
    }

    private static IPageDirector.SceneListener sSceneListenerProxy = IPageDirector
            .EMPTY_SCENE_LISTENER;

    public static void setSceneListenerProxy(IPageDirector.SceneListener sceneListener) {
        if (sceneListener == null) {
            sceneListener = IPageDirector.EMPTY_SCENE_LISTENER;
        }
        sSceneListenerProxy = sceneListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mRootLayout > 0) {
            setContentView(mRootLayout);
        } else {
            setContentView(R.layout.activity_viewfragment);
        }
        onInitData(getApplicationContext(), savedInstanceState);
        mPageDirector.clear();
        onCreateFragments(getApplicationContext(), savedInstanceState, mPageDirector);
    }

    /**
     * 初始化数据接口
     * 优先于 @link ViewFragmentActivity#onCreateFragments 接口调用
     *
     * @param context Application Context
     * @param savedInstanceState
     */
    protected abstract void onInitData(Context context, Bundle savedInstanceState);

    /**
     * 创建页面接口
     *
     * @param context Application Context
     * @param savedInstanceState
     * @param sceneWriter 页面管理者
     */
    protected abstract void onCreateFragments(Context context, Bundle savedInstanceState, IPageDirector.Writer sceneWriter);

    protected IPageDirector.Writer getSceneWriter() {
        return mPageDirector;
    }

    protected IPageDirector.Scene getScene() {
        return mPageDirector;
    }


    @Override
    public void onBackPressed() {
        Fragment fragment = getCurrentFragment();
        if (fragment instanceof View.OnKeyListener) {
            if (((View.OnKeyListener) fragment).onKey(null, KeyEvent.KEYCODE_BACK, null)) {
                return;
            }
        }

        if (mPageDirector.showPrevPage(getCurrentFragment(), null, null)) {
            // not thing to do
        } else {
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Fragment fragment = getCurrentFragment();
        if (fragment == null) {
            finish();
            return true;
        }
        if (getCurrentFragment().onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case android.R.id.home:
                if (!mPageDirector.showPrevPage(getCurrentFragment(), null, null)) {
                    finish();
                }
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    protected Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentById(R.id.content);
    }

    public void onDestroy() {
        super.onDestroy();
        mPageDirector.clearPage();
    }

}
