package com.iunin.demo.demo.ui.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 页面切换控制
 *
 * @author leon@iunin.com
 */

public class PageDirector implements IPageDirector.Writer, IPageDirector.Scene {

    private Map<Object, WizardPage> mStepMap = new HashMap<>();
    private Map<Object, Map<Integer, Fragment>> mListMap = new HashMap<>();
    private Map<Fragment, Stack<Fragment>> mBackStack = new HashMap<>();
    private Fragment mFirstPage;
    private Fragment mLastPage;

    private final FragmentManager mFragmentManager;
    private final int mContainerId;
    private IPageDirector.SceneListener mSceneListener = IPageDirector.EMPTY_SCENE_LISTENER;

    /**
     * 构造函数
     *
     * @param activity  所属的 Activity
     * @param container fragment 的容器的 ID
     */
    public PageDirector(AppCompatActivity activity, int container) {
        mFragmentManager = activity.getSupportFragmentManager();
        mContainerId = container;
    }
    @Override
    public boolean showFirstPage() {
        if (mFirstPage == null) {
            return false;
        } else {
            switchToFragment(mFirstPage);
            return true;
        }
    }

    @Override
    public boolean showLastPage() {
        if (mLastPage == null) {
            return false;
        } else {
            switchToFragment(mLastPage);
            return true;
        }
    }

    @Override
    public boolean isOnFirstPage() {
        return mFirstPage != null && mFirstPage == getCurrentFragment();
    }

    @Override
    public boolean isOnLastPage() {
        return mLastPage != null && mLastPage == getCurrentFragment();
    }

    @Override
    public boolean showPrevPage(Fragment fromPage, Bundle params, List<Pair> sharedElements) {
        if (fromPage == null) return false;
        Fragment toFragment = getPrevPage(fromPage.getClass());
        if (toFragment != null) {
            switchToFragment(toFragment);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean showNextPage(Fragment fromPage, Bundle params, List<Pair> sharedElements) {
        if (fromPage == null) return false;
        Fragment toFragment = getNextPage(fromPage.getClass());
        if (toFragment != null) {
            switchToFragment(toFragment);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean showPage(Class toPage, Fragment fromPage, Bundle params, List<Pair> sharedElements) {
        return showPage(toPage, 0, fromPage, params, sharedElements);
    }

    @Override
    public boolean showPage(Class toPage, int tag, Fragment fromPage, Bundle params, List<Pair>
            sharedElements) {
        Map<Integer, Fragment> map = mListMap.get(toPage);
        if (map == null) {
            return false;
        }
        Fragment fragment = map.get(tag);
        if (fragment != null) {
            switchToFragment(fragment);
            pushBackPage(fragment, fromPage);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean backPage(Fragment fromPage, Bundle params, List<Pair> sharedElements) {
        Fragment back = popBackPage(fromPage);
        if (back != null) {
            switchToFragment(back);
            return true;
        } else {
            return false;
        }
    }

    private void pushBackPage(Fragment fragment, Fragment fromPage) {
        Stack<Fragment> stack = mBackStack.get(fragment);
        if (stack == null) {
            stack = new Stack<>();
            mBackStack.put(fragment, stack);
        }
        stack.push(fromPage);
    }

    private Fragment popBackPage(Fragment fragment) {
        Stack<Fragment> stack = mBackStack.get(fragment);
        return stack.pop();
    }

    @Override
    public void setSceneListener(IPageDirector.SceneListener sceneListener) {
        if (sceneListener == null) {
            sceneListener = IPageDirector.EMPTY_SCENE_LISTENER;
        }
        mSceneListener = sceneListener;
    }

    @Override
    public void setFirstPage(Fragment page) {
        mFirstPage = page;
    }

    @Override
    public void setLastPage(Fragment page) {
        mLastPage = page;
    }

    @Override
    public void addPage(Class page, Fragment prev, Fragment next) {
        mStepMap.put(page, new WizardPage(prev, next));
    }

    @Override
    public void addPage(Class page, Fragment fragment) {
        addPage(page, 0, fragment);
    }

    @Override
    public void addPage(Class page, int tag, Fragment fragment) {
        Map<Integer, Fragment> map = mListMap.get(page);
        if (map == null) {
            map = new HashMap<>();
        }
        map.put(tag, fragment);
        mListMap.put(page, map);
    }

    @Override
    public void clearPage() {
        clear();
    }

    public void clear() {
        mFirstPage = null;
        mLastPage = null;
        mStepMap.clear();
        for (Stack<Fragment> stack : mBackStack.values()) {
            stack.clear();
        }
        mBackStack.clear();
        removeCurrentFragment();
    }

    private Fragment getCurrentFragment() {
        return mFragmentManager.findFragmentById(mContainerId);
    }

    private void switchToFragment(Fragment fragment) {
        Fragment currFragment = getCurrentFragment();
        if (currFragment == null) {
            addFragmentToActivity(mFragmentManager, fragment, mContainerId);
            mSceneListener.onSwitchToPage(fragment);
        } else {
            if (currFragment == fragment && fragment.isAdded()) {
                return;
            }
            switchToFragmentInActivity(mFragmentManager, fragment, mContainerId);
            mSceneListener.onSwitchToPage(fragment);
        }
    }

    private void addFragmentToActivity(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, int frameId) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.addToBackStack(null);
        transaction.commitAllowingStateLoss();
    }

    private void switchToFragmentInActivity(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, int frameId) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(frameId, fragment);
        transaction.addToBackStack(null);
        transaction.commitAllowingStateLoss();
    }

    private void removeCurrentFragment() {
        Fragment fragment = getCurrentFragment();
        if (fragment == null) return;
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(mContainerId, fragment);
        transaction.addToBackStack(null);
        transaction.commitAllowingStateLoss();
    }


    public Fragment getPrevPage(Class step) {
        WizardPage target = mStepMap.get(step);
        if (target == null) return null;
        return target.prev;
    }

    public Fragment getNextPage(Class step) {
        WizardPage target = mStepMap.get(step);
        if (target == null) return null;
        return target.next;
    }


    private class WizardPage {
        Fragment prev;
        Fragment next;

        public WizardPage(Fragment _prev, Fragment _next) {
            prev = _prev;
            next = _next;
        }
    }
}
