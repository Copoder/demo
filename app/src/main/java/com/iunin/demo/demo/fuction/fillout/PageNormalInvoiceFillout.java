package com.iunin.demo.demo.fuction.fillout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.iunin.demo.demo.R;
import com.iunin.demo.demo.goods.Goods;
import com.iunin.demo.demo.model.TaxGoodsModel;
import com.iunin.demo.demo.ui.base.ViewFragment;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by copo on 17-11-14.
 */

public class PageNormalInvoiceFillout extends ViewFragment {

    private RecyclerView mRecyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.page_normal_invoice,container,false);
        initView(rootView);
        return rootView;
    }

    private void initView(View view) {
        mRecyclerView = view.findViewById(R.id.spbmData);
        final List<TaxGoodsModel> models = new ArrayList<>();
        final GoodsListAdapter goodsListAdapter = new GoodsListAdapter(new ArrayList<TaxGoodsModel>(), getContext());
        goodsListAdapter.setOnaddGoodsListener(new GoodsListAdapter.OnaddGoodsListener() {
            @Override
            public void addGoods() {
                TaxGoodsModel model = new TaxGoodsModel("0","测试", BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO);
                models.add(model);
                goodsListAdapter.notifyDataSetChanged();
                Toast.makeText(getContext(), "test", Toast.LENGTH_SHORT).show();
            }
        });
        mRecyclerView.setAdapter(goodsListAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
