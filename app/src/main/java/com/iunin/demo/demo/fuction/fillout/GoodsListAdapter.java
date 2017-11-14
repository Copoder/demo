package com.iunin.demo.demo.fuction.fillout;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iunin.demo.demo.R;
import com.iunin.demo.demo.goods.Goods;
import com.iunin.demo.demo.model.TaxGoodsModel;

import java.util.List;

/**
 * Created by copo on 17-11-14.
 */

public class GoodsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ADD = 0;
    private static final int TYPE_CONTENT = 1;
    private List<TaxGoodsModel> goods;
    private Context mContext;

    private OnaddGoodsListener onaddGoodsListener;
    public GoodsListAdapter(List<TaxGoodsModel> goods,Context context){
        this.goods = goods;
        mContext = context;
    }

    public void setOnaddGoodsListener(OnaddGoodsListener onaddGoodsListener){
        this.onaddGoodsListener = onaddGoodsListener;
    }
    class GoodsListHolder extends RecyclerView.ViewHolder {
        private TextView name;

        public GoodsListHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.id_good_name);
        }
    }

    class AddGoodsHolder extends RecyclerView.ViewHolder {
        public AddGoodsHolder(View itemView) {
            super(itemView);
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_ADD){
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_goods,parent,false);
            AddGoodsHolder addGoodsHolder = new AddGoodsHolder(itemView);
            return addGoodsHolder;
        }else {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_edit_good,parent,false);
            GoodsListHolder goodsListHolder = new GoodsListHolder(itemView);
            return goodsListHolder;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof AddGoodsHolder){
            if(onaddGoodsListener != null){
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //添加商品的回调
                        onaddGoodsListener.addGoods();
                    }
                });
            }
        }else {
            TaxGoodsModel goodsModel = goods.get(position);
            ((GoodsListHolder)holder).name.setText(goodsModel.spmc);
        }
    }


    @Override
    public int getItemViewType(int position) {
        if(position == goods.size()){
            return TYPE_ADD;
        }else {
            return TYPE_CONTENT;
        }
    }

    @Override
    public int getItemCount() {
        return goods.size() + 1;
    }

    interface OnaddGoodsListener{
        void addGoods();
    }
}
