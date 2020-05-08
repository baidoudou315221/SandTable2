package com.example.sandtable2.table;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

public class IndexHorizontalScrollView extends HorizontalScrollView {


    private Paint textP;
    private Today24HourView hoursView24;

    public IndexHorizontalScrollView(Context context) {
        super(context);
    }

    public IndexHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public IndexHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        textP = new Paint();
        textP.setTextSize(DisplayUtil.sp2px(getContext(),12));
        textP.setAntiAlias(true);
        textP.setColor(new Color().WHITE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int Offset = this.computeHorizontalScrollOffset();
        int MaxOffSet = this.computeHorizontalScrollRange() - DisplayUtil.getScreenWidth(getContext());
        if (hoursView24 != null){
            hoursView24.setScrollOffset(Offset,MaxOffSet);
        }
    }

    public void set24HourView(Today24HourView hourView){
        this.hoursView24 = hourView;
    }
}

