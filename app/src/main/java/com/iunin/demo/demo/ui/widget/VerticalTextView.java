package com.iunin.demo.demo.ui.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by ShouHu on 2016/11/30.
 */
public class VerticalTextView extends LinearLayout {


    public VerticalTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
        this.context = context;
    }

    private String text;
    private Context context;
    private int color;
    private int size;

    public VerticalTextView(Context context) {
        super(context);
        setOrientation(VERTICAL);
        this.context = context;
    }


    public void setText(String text) {
        this.text = text;
        addText();
    }

    private void addText() {
        removeAllViews();
        if (text != null) {
            char[] chara = text.toCharArray();
            for (int i = 0; i < chara.length; i++) {
                TextView oneText = new TextView(context);
                oneText.setLayoutParams(new LinearLayoutCompat.LayoutParams(20,25));
                oneText.setGravity(Gravity.TOP);
                oneText.setTextColor(color);
                oneText.setText(text.substring(i, i + 1));
                if (size > 0) {
                    oneText.setTextSize(5);
                }
                oneText.animate().rotation(90);
                addView(oneText);
            }
        }

    }

    public void setTextColor(int color) {
        this.color = color;
    }

    public void setTextSize(int size) {
        this.size = size;
    }


}