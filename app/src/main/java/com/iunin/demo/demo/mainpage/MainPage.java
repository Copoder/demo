package com.iunin.demo.demo.mainpage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iunin.demo.demo.R;
import com.iunin.demo.demo.fuction.fillout.InvoiceFilloutActivity;
import com.iunin.demo.demo.fuction.invalidinvoice.InvalidInvoice;
import com.iunin.demo.demo.fuction.invoicequery.InvoiceQueryActivity;
import com.iunin.demo.demo.ui.base.ViewFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by copo on 17-11-13.
 */

public class MainPage extends ViewFragment {
    private RecyclerView mRecyclerView;
    private List<FunctionItem> items;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.page_main,container,false);
        mRecyclerView = rootView.findViewById(R.id.main_recyclerview);
        items = new ArrayList<>();
        items.add(new FunctionItem("发票开具",R.drawable.new_ic_red_about, InvoiceFilloutActivity.class));
        items.add(new FunctionItem("发票查询",R.drawable.new_ic_red_about, InvoiceQueryActivity.class));
        items.add(new FunctionItem("发票作废",R.drawable.new_ic_red_about, InvalidInvoice.class));
        MainFunctionAdapter adapter = new MainFunctionAdapter(items,getActivity());
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.setOnItemClickListener(new MainFunctionAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                //TODO 点击跳转
                Intent intent = new Intent(getContext(),items.get(position).getClazz());
                startActivity(intent);
            }
        });
        return rootView;
    }
}
