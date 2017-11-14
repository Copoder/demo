package com.iunin.demo.demo.fuction.fillout;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.iunin.demo.demo.R;
import com.iunin.demo.demo.ui.base.IPageDirector;
import com.iunin.demo.demo.ui.base.ViewFragmentActivity;

/**
 * Created by copo on 17-11-14.
 */

public class InvoiceFilloutActivity extends ViewFragmentActivity {
    private InvoiceTypeSelector mFplxMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mRootLayout = R.layout.activity_fillout;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onInitData(Context context, Bundle savedInstanceState) {

    }

    @Override
    protected void onCreateFragments(Context context, Bundle savedInstanceState, final IPageDirector.Writer sceneWriter) {
        sceneWriter.setFirstPage(new PageNormalInvoiceFillout());

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(null);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
        getScene().showFirstPage();

        final TextView mFplxTitle = findViewById(R.id.id_right);
        mFplxMenu = new InvoiceTypeSelector(this, mFplxTitle);
//        mFplxMenu.addListener();

        mFplxTitle.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                {
                    mFplxMenu.show(mFplxTitle);
                }
            }
        });

//        mFplxMenu.addListener(new FpLxListener() {
//            @Override
//            public void onFpLxChanged(String fplxdm) {
//                Fragment fragment = getCurrentFragment();
//                if (fragment instanceof FpLxListener) {
//                    ((FpLxListener)fragment).onFpLxChanged(fplxdm);
//                }
//                mInvoiceForm.setFpLxDm(fplxdm);
//                mPageTitle.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        switchFillOutPage(mInvoiceForm.getFpLxDm());
//                    }
//                });
//
//            }
//        });
        mFplxMenu.addListener(new FpLxListener() {
            @Override
            public void onFpLxChanged(String fplxdm) {
                Toast.makeText(getApplicationContext(),fplxdm,Toast.LENGTH_SHORT).show();
                if(!fplxdm.equals("025")){
                    if(!(getCurrentFragment() instanceof PageNormalInvoiceFillout)){
                        getSceneWriter().clearPage();
                        PageNormalInvoiceFillout pageNormalInvoiceFillout = new PageNormalInvoiceFillout();
                        getSceneWriter().setFirstPage(pageNormalInvoiceFillout);
//                        getScene().showFirstPage();
                    }
                }else {
                    if(!(getCurrentFragment() instanceof PageInvoiceFillOut)){
                        getSceneWriter().clearPage();
                        PageInvoiceFillOut pageInvoiceFillOut = new PageInvoiceFillOut();
                        getSceneWriter().setFirstPage(pageInvoiceFillOut);
//                        getScene().showFirstPage();
                    }
                }
                    getScene().showFirstPage();
            }
        });
    }

    private void initRollFragments(IPageDirector.Writer sceneWriter) {
//        sceneWriter.clearPage();
//        PageInvoiceFillOut fillOutFragment = new PageInvoiceFillOut();
//        sceneWriter.setFirstPage(fillOutFragment);
//        sceneWriter.addPage(PageInvoiceFillOut.class, null, confirmMakeFragment);
//        sceneWriter.addPage(ConfirmMakeFragment.class, fillOutFragment, null);
//        sceneWriter.addPage(PageInvoiceFillOut.class, fillOutFragment);
//        sceneWriter.addPage(ConfirmMakeFragment.class, confirmMakeFragment);
    }
//
//
//    private void initNormalFragments(IPageDirector.Writer sceneWriter) {
//        sceneWriter.clearPage();
//
//        sceneWriter.setFirstPage(filloutFragment);
//        sceneWriter.addPage(FillOutNormalFragment.class, null, confirmMakeFragment);
//        sceneWriter.addPage(ConfirmNormalMakeFragment.class, filloutFragment, null);
//        sceneWriter.addPage(FillOutNormalFragment.class, filloutFragment);
//        sceneWriter.addPage(ConfirmNormalMakeFragment.class, confirmMakeFragment);
//    }

}
