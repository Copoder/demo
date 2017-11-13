package com.iunin.demo.demo.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;

/**
 * Created by houtong on 2017/9/4 0004.
 */

public class AutoFillSpaceTextView extends android.support.v7.widget.AppCompatTextView {
    private String text;
    private int textLength;
    private int measuredWidth;
    private float spaceWidth;
    private TextPaint mTexttePaint;
    private float textWidth;
    private int textHeight;
    private Rect mRect;


    public AutoFillSpaceTextView(Context context) {
        super(context);
        prePare();
    }

    public AutoFillSpaceTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        prePare();
    }

    public AutoFillSpaceTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        prePare();
    }

    private void prePare(){
        mRect = new Rect();
        mTexttePaint = getPaint();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        textLength = getText().length();
        text = getText().toString();
        measuredWidth = getWidth();
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        drawMyText(canvas);
    }

    private void drawMyText(Canvas canvas) {
        canvas.save();
        mTexttePaint.setTextSize(41);
        mTexttePaint.setStrokeWidth(1);
        mTexttePaint.setAntiAlias(true);
        mTexttePaint.setTypeface(Typeface.DEFAULT);
        canvas.drawText(text, 0, -5, mTexttePaint);
        mTexttePaint.measureText(text);
        textWidth = mTexttePaint.measureText(text);

        mTexttePaint.getTextBounds(text,0,textLength,mRect);
        textHeight = mRect.height();
        spaceWidth = (measuredWidth - textWidth) / (textLength - 1);

        canvas.restore();
        int measuredWidth = getMeasuredWidth();
        for (int i = 0; i < textLength; i++) {
            String s = text.substring(i, i + 1);
            if (i == 0) {
                canvas.drawText(s, 0, getHeight() - 13, mTexttePaint);

            } else {
                canvas.drawText(s, i * spaceWidth + mTexttePaint.measureText(s) * i,getHeight() - 13, mTexttePaint);
            }
        }
    }

}
