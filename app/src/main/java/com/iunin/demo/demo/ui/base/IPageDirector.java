package com.iunin.demo.demo.ui.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;

import java.util.List;

/**
 * 页面切换管理
 *
 * @author leon@iunin.com
 */
public interface IPageDirector {

    /**
     * 页面流程定义接口
     */
    interface Writer {
        /**
         * 设置第一个的页面
         *
         * @param page
         */
        void setFirstPage(Fragment page);

        /**
         * 设置最后的页面
         *
         * @param page
         */
        void setLastPage(Fragment page);

        /**
         * 添加页面
         *
         * @param page 页面的类
         * @param prev page 页面的上一页
         * @param next page 页面的下一页
         */
        void addPage(Class page, Fragment prev, Fragment next);

        /**
         * 添加页面
         *
         * @param page 页面的类
         * @param fragment page 对应的 Fragment 对象
         */
        void addPage(Class page, Fragment fragment);

        /**
         * 添加页面
         *
         * @param page     页面的类
         * @param tag      页面实例的 tag，有效值：非0
         * @param fragment page 对应的 Fragment 对象
         */
        void addPage(Class page, int tag, Fragment fragment);

        /**
         * 清除所有页面
         */
        void clearPage();
    }

    /**
     * 页面切换接口
     */
    interface Scene {

        /**
         * 显示第一个页面
         *
         * @return 可以切换页面返回true, 否则返回false
         */
        boolean showFirstPage();

        /**
         * 显示最后的页面
         *
         * @return 可以切换页面返回true, 否则返回false
         */
        boolean showLastPage();

        /**
         * 当前页面是否是第一页
         * @return
         */
        boolean isOnFirstPage();

        /**
         * 当前页面是否是最后一页
         * @return
         */
        boolean isOnLastPage();

        /**
         * 显示上一页
         *
         * @param fromPage       当前的页面
         * @param params         附加参数
         * @param sharedElements 页面切换动画的共享元素
         * @return 可以切换页面返回true, 否则返回false
         */

        boolean showPrevPage(Fragment fromPage, Bundle params, List<Pair> sharedElements);

        /**
         * 显示下一页
         *
         * @param fromPage       当前的页面
         * @param params         附加参数
         * @param sharedElements 页面切换动画的共享元素
         * @return 可以切换页面返回true, 否则返回false
         */
        boolean showNextPage(Fragment fromPage, Bundle params, List<Pair> sharedElements);

        /**
         * 显示指定页面
         *
         * @param toPage         目标页面的类
         * @param fromPage       当前的页面
         * @param params         附加参数
         * @param sharedElements 页面切换动画的共享元素
         * @return 可以切换页面返回true, 否则返回false
         */
        boolean showPage(Class toPage, Fragment fromPage, Bundle params, List<Pair> sharedElements);

        /**
         * 显示指定页面
         *
         * @param toPage         目标页面的类
         * @param tag            页面实例的 tag，有效值：非0
         * @param fromPage       当前的页面
         * @param params         附加参数
         * @param sharedElements 页面切换动画的共享元素
         *
         * @return 可以切换页面返回true, 否则返回false
         */
        boolean showPage(Class toPage, int tag, Fragment fromPage, Bundle params, List<Pair> sharedElements);

        /**
         * 后退一页
         * <p>
         * 与 {@link Scene#showPage(Class, Fragment, Bundle, List)} 对应
         *
         * @param fromPage       当前的页面
         * @param params         附加参数
         * @param sharedElements 页面切换动画的共享元素
         * @return 可以切换页面返回true, 否则返回false
         */
        boolean backPage(Fragment fromPage, Bundle params, List<Pair> sharedElements);

        /**
         * 设置页面切换监听接口
         *
         * @param sceneListener
         */
        void setSceneListener(SceneListener sceneListener);

    }

    interface SceneListener {
        /**
         * 切换到新的页面
         * @param fragment
         */
        void onSwitchToPage(Fragment fragment);
    }

    Writer EMPTY_WRITER = new Writer() {
        @Override
        public void setFirstPage(Fragment page) {

        }

        @Override
        public void setLastPage(Fragment page) {

        }

        @Override
        public void addPage(Class page, Fragment prev, Fragment next) {

        }

        @Override
        public void addPage(Class page, Fragment fragment) {

        }

        @Override
        public void addPage(Class page, int tag, Fragment fragment) {

        }

        @Override
        public void clearPage() {

        }
    };

    Scene EMPTY_SCENE = new Scene() {
        @Override
        public boolean showFirstPage() {
            return false;
        }

        @Override
        public boolean showLastPage() {
            return false;
        }

        @Override
        public boolean isOnFirstPage() {
            return false;
        }

        @Override
        public boolean isOnLastPage() {
            return false;
        }

        @Override
        public boolean showPrevPage(Fragment fromPage, Bundle params, List<Pair> sharedElements) {
            return false;
        }

        @Override
        public boolean showNextPage(Fragment fromPage, Bundle params, List<Pair> sharedElements) {
            return false;
        }

        @Override
        public boolean showPage(Class toPage, Fragment fromPage, Bundle params, List<Pair>
                sharedElements) {
            return false;
        }

        @Override
        public boolean showPage(Class toPage, int tag, Fragment fromPage, Bundle params,
                                List<Pair> sharedElements) {
            return false;
        }

        @Override
        public boolean backPage(Fragment fromPage, Bundle params, List<Pair> sharedElements) {
            return false;
        }

        @Override
        public void setSceneListener(SceneListener sceneListener) {

        }
    };

    SceneListener EMPTY_SCENE_LISTENER = new SceneListener() {
        @Override
        public void onSwitchToPage(Fragment fragment) {

        }
    };
}
