package com.iunin.demo.demo.fuction.fillout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iunin.demo.demo.R;
import com.iunin.demo.demo.goods.Goods;
import com.iunin.demo.demo.model.TaxGoodsModel;
import com.iunin.demo.demo.ui.base.ViewFragment;

import java.util.ArrayList;

/**
 * Created by copo on 17-11-14.
 */

public class PageInvoiceFillOut extends ViewFragment {
    RecyclerView goodsList;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.page_make_invoice,container,false);
        initView(rootView);
        return rootView;
    }

    private void initView(View view) {
        goodsList = view.findViewById(R.id.spbmData);
        goodsList.setAdapter(new GoodsListAdapter(new ArrayList<TaxGoodsModel>(),getContext()));
        goodsList.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
