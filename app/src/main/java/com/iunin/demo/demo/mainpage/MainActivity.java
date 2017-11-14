package com.iunin.demo.demo.mainpage;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.iunin.demo.demo.fuction.fillout.PageInvoiceFillOut;
import com.iunin.demo.demo.mainpage.MainPage;
import com.iunin.demo.demo.ui.base.IPageDirector;
import com.iunin.demo.demo.ui.base.ViewFragmentActivity;

public class MainActivity extends ViewFragmentActivity {
    private Fragment mainPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onInitData(Context context, Bundle savedInstanceState) {

    }

    @Override
    protected void onCreateFragments(Context context, Bundle savedInstanceState, IPageDirector.Writer sceneWriter) {
        mainPage = new MainPage();
        sceneWriter.setFirstPage(mainPage);
        sceneWriter.addPage(PageInvoiceFillOut.class,new PageInvoiceFillOut());
        getScene().showFirstPage();
    }
}
