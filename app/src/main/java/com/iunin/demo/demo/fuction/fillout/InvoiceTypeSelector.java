package com.iunin.demo.demo.fuction.fillout;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.iunin.demo.demo.R;
import com.iunin.demo.demo.base.ArrayWapperAdapter;
import com.iunin.demo.demo.base.ViewHolder;
import com.iunin.demo.demo.ui.base.PopupWindowBase;
import com.iunin.demo.demo.ui.utils.ScreenUtils;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by heyuanda on 16/12/16.
 */

public class InvoiceTypeSelector extends PopupWindowBase implements IInvoiceTypeSelector {
    public static String[] INVOICE_TYPE_CODES = {
            "004", "007", "025", "026",
    };
    public static String[] INVOICE_TYPE_NAMES = {
            "增值税专用发票", "增值税普通发票", "增值税卷式发票", "增值税电子发票",
    };

    private int type = -1;

    private Set<FpLxListener> mFpLxListeners = new HashSet<>();
    private MenuAdapter menuAdapter;
    private HashMap<String,String> fplxMap;
    private HashMap<String,String> fplxMapTotal;
    private List<String> currentFplxs;

    private ListView id_listview;

    private String fplxdm;
    private TextView id_right;
    private  int selPosition = 0;

    public void setDisabledPosition(int disabledPosition) {
        this.disabledPosition = disabledPosition;
    }

    private int disabledPosition = -1; //不支持票种的位置 灰色显示
    public InvoiceTypeSelector(Context context, TextView id_rightIn) {
        super(context);
        this.id_right = id_rightIn;
        initView();
        bindListener();
    }

    @Override
    protected void initView() {
        rootView = inflater.inflate(R.layout.pop_menu_layout,null);
        setContentView(rootView);
        setWidth((int) (ScreenUtils.getScreenWidth(context)*0.5));
        setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);

        id_listview =  rootView.findViewById(R.id.id_listview);
        List<String> fplxs =  new ArrayList<>();
        fplxs.add("004");
        fplxs.add("007");
        fplxs.add("025");
        fplxs.add("026");
        //004 005 007  009  025  026
        fplxMap = new HashMap<>();
        currentFplxs = new ArrayList<>();
        fplxMapTotal = new HashMap<>();
        String[]   fplxNameCode =  INVOICE_TYPE_CODES;
        String[]   fplxNames =  INVOICE_TYPE_NAMES;

        for (int i=0;i<fplxNameCode.length;i++){
            fplxMapTotal.put(fplxNameCode[i],fplxNames[i]);
        }
        for (int i=0;i<fplxs.size();i++){
            if (fplxMapTotal.get(fplxs.get(i))!=null){
                fplxMap.put(fplxs.get(i),fplxMapTotal.get(fplxs.get(i)));
                currentFplxs.add(fplxMapTotal.get(fplxs.get(i)));
            }
        }

        menuAdapter= new  MenuAdapter(context,currentFplxs);
        id_listview.setAdapter(menuAdapter);
        String lastLocalType = "";
        if (checkHasType(fplxs,lastLocalType)){
            setType(lastLocalType);
        }else{
            if (fplxs.size()>0){
                setType(fplxs.get(0));
            }else{
                setType("025");
            }
        }
    }

    private boolean checkHasType(List<String> fplxs, String type) {
        boolean isHas = false;
        for (int i=0;i<fplxs.size();i++){
            if (type.equals(fplxs.get(i))){
                isHas=true;
                break;

            }
        }
        return isHas;
    }

    @Override
    protected void bindListener() {
        id_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == disabledPosition)return;
                String name = currentFplxs.get(position);
                selPosition = position;
                menuAdapter.notifyDataSetChanged();
                setFpLxDm(getKeyCode(name));
                notifyFpLxChanged(fplxdm);
                id_right.setText(name.substring(3,name.length()));

                dismiss();
            }
        });
    }


    private String getKeyCode(String value){
        String keyCode = "";
        for(Map.Entry<String,String> entry:this.fplxMap.entrySet()){
            if(value.equals(entry.getValue())){
                keyCode = entry.getKey();
                break;
            }else{
                continue;
            }
        }
        return keyCode;
    }


    public void setType(String mode){
        if (TextUtils.isEmpty(mode)){
            return;
        }
        setFpLxDm(mode);
        String value = fplxMap.get(mode);
        if (value==null){
            selPosition=0;
            menuAdapter.notifyDataSetChanged();
            return;
        }

        for (int i =0;i<currentFplxs.size();i++){
            if (value.equals(currentFplxs.get(i))){
                selPosition = i;
                break;
            }
        }
        menuAdapter.notifyDataSetChanged();
        if (fplxMap.get(mode)!=null){
            id_right.setText(fplxMap.get(mode).substring(3,fplxMap.get(mode).length()));
        }
    }

    @Override
    public void setFpLxDm(String fplxdm) {
        this.fplxdm = fplxdm;
    }

    public String getFpLxDm() {
        return fplxdm;
    }

    @Override
    public void addListener(FpLxListener listener) {
        if (listener == null) return;
        mFpLxListeners.add(listener);
    }

    @Override
    public void removeListener(FpLxListener listener) {
        if (listener == null) return;
        mFpLxListeners.remove(listener);
    }

    private void notifyFpLxChanged(String fplxdm) {
        for (FpLxListener listener : mFpLxListeners) {
            listener.onFpLxChanged(fplxdm);
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    public void show(View ancher){
        showAsDropDown(ancher);

    }


    class MenuAdapter extends ArrayWapperAdapter<String> {

        public MenuAdapter(Context context, List<String> objects) {
            super(context, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item, null);
            }
            TextView textView = ViewHolder.get(convertView, R.id.id_left_name);
            ImageView imageView = ViewHolder.get(convertView, R.id.id_right_roll);
            if (selPosition==position){
                imageView.setVisibility(View.VISIBLE);
            }else{
                imageView.setVisibility(View.INVISIBLE);
            }

            if (position == disabledPosition){

                imageView.setVisibility(View.INVISIBLE);
                textView.setTextColor(Color.GRAY);

            }else {
                 textView.setTextColor(context.getResources().getColor(R.color.color_text_Large));
                //color_text_Large
            }
            textView.setText(getItem(position));
            return convertView;
        }
    }

}
