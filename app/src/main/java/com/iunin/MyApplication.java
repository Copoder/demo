package com.iunin;

import android.app.Application;
import android.support.v4.app.Fragment;

import com.iunin.demo.demo.ui.base.IPageDirector;
import com.iunin.demo.demo.ui.base.ViewFragmentActivity;

/**
 * Created by copo on 17-11-13.
 */

public class MyApplication extends Application {
    private static MyApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        ViewFragmentActivity.setSceneListenerProxy(mSceneListener);
    }

    public static MyApplication getInstance(){
        return instance;
    }

    private final IPageDirector.SceneListener mSceneListener = new IPageDirector.SceneListener() {
        @Override
        public void onSwitchToPage(Fragment fragment) {

        }
    };
}
