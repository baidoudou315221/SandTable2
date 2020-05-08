package com.example.sandtable2.table;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.sandtable2.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2016/10/19.
 */
public class Today24HourView extends View{
    private int mWidth,mHeight;
    private static final int ITEM_SIZE = 24;//24小时
    private static final int ITEM_WIDTH = 140;//每个item宽度
    private static final int MARGIN_LEFT_ITEM = 100;//左右预留宽度
    private static final int MARGIN_RIGHT_ITEM = 100;
    private int tempBaseTop,tempBaseBottom;//温度折线的上下坐标Y
    private static final int bottomTextHeight = 60;
    private Paint bitmapPaint, linePaint, pointPaint, dashLinePaint;
    private TextPaint textPaint;//各种paint
    private List<HourItem> listItems;
    private int maxScrollOffset = 0;//滚动条最长滚动距离
    private int scrollOffset = 0; //滚动条偏移量
    private int currentItemIndex = 0; //当前滚动的位置所对应的item下标


    private static int[] TEMP = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0};

    private static final int[] WEATHER_RES = {-1, -1, -1, -1, -1
            , -1, -1, -1, -1, -1
            , -1, -1, -1, -1, -1
            , -1, -1, -1, -1, -1
            , -1, -1, -1, -1};

    public Today24HourView(Context context) {
        this(context,null);
    }

    public Today24HourView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public Today24HourView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

//    public void setTemp(int[] data){
//        TEMP = data;
//    }

    private void init() {
        mWidth = MARGIN_LEFT_ITEM + MARGIN_RIGHT_ITEM + ITEM_SIZE * ITEM_WIDTH;
        mHeight = 800;
        tempBaseTop = (800 - bottomTextHeight)/4;
        tempBaseBottom = (800 - bottomTextHeight)*2/3;

//        initHourItems(TEMP);
        initPaint();
    }

    public void initHourItems(int[] temp) {
        TEMP = temp;
        listItems = new ArrayList<>();
        for(int i=0;i<ITEM_SIZE;i++){
            String time;
            if (i<10){
                time = "0" + i + ":00";
            }else{
                time = i + ":00";
            }
            int left =MARGIN_LEFT_ITEM  +  i * ITEM_WIDTH;
            int right = left + ITEM_WIDTH - 1;
            int top = mHeight -bottomTextHeight;
            int bottom =  mHeight - bottomTextHeight;
            Rect rect = new Rect(left, top, right, bottom);//绘制长方形
            Point point = calculateTempPoint(left, right, TEMP[i]);//自定义的方法

            HourItem hourItem = new HourItem();
            hourItem.windyBoxRect = rect;
            hourItem.time = time;
            hourItem.temperature = TEMP[i];
            hourItem.tempPoint = point;
            hourItem.res = WEATHER_RES[i];

            listItems.add(hourItem);
        }
    }

    private void initPaint() {
        pointPaint = new Paint();//画点
        pointPaint.setColor(new Color().WHITE);
        pointPaint.setAntiAlias(true);
        pointPaint.setTextSize(8);

        linePaint = new Paint();//画线
        linePaint.setColor(new Color().WHITE);
        linePaint.setAntiAlias(true);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(5);

        dashLinePaint = new Paint();
        dashLinePaint.setColor(new Color().WHITE);
        PathEffect effect = new DashPathEffect(new float[]{5, 5, 5, 5}, 1);//用来控制绘制轮廓(线条)的方式，参数分别为偶数下表对应实线宽度，技术索引对应空白线宽度，偏移量
        dashLinePaint.setPathEffect(effect);
        dashLinePaint.setStrokeWidth(3);
        dashLinePaint.setAntiAlias(true);
        dashLinePaint.setStyle(Paint.Style.STROKE);

        textPaint = new TextPaint();//画字
        textPaint.setTextSize(DisplayUtil.sp2px(getContext(), 12));
        textPaint.setColor(new Color().WHITE);
        textPaint.setAntiAlias(true);

        bitmapPaint = new Paint();
        bitmapPaint.setAntiAlias(true);
    }

    private Point calculateTempPoint(int left, int right, int temp) {
        double minHeight = tempBaseTop;
        double maxHeight = tempBaseBottom;
        double tempY = maxHeight - (temp - getMin(TEMP))* 1.0/(getMax(TEMP) - getMin(TEMP)) * (maxHeight - minHeight);
        Point point = new Point((left + right)/2, (int)tempY);
        return point;
    }

    private int getMin(int num[]) {
        int min = num[0];
        for(int j=1;j<num.length;j++){
            if(num[j]<min)
                min = num[j];
        }
        return min;
    }

    private int getMax(int numb[]) {
        int max = numb[0];
        for (int j=0;j<numb.length;j++){
            if (numb[j]>max){
                max = numb[j];
            }
        }
        return max;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for(int i=0; i<listItems.size(); i++){
            Point point = listItems.get(i).tempPoint;
            //画温度的点
            onDrawTemp(canvas, i);
            onDrawLine(canvas, i);
            onDrawText(canvas, i);
        }
        //底部水平的白线
        linePaint.setColor(new Color().WHITE);
        canvas.drawLine(0, mHeight - bottomTextHeight, mWidth, mHeight - bottomTextHeight, linePaint);

    }

    private void onDrawTemp(Canvas canvas, int i) {
        HourItem item = listItems.get(i);
        Point point = item.tempPoint;
        canvas.drawCircle(point.x, point.y, 10, pointPaint);

        if(currentItemIndex == i) {
            //计算提示文字的运动轨迹
            int Y = getTempBarY();
            //画出背景图片
            Drawable drawable = ContextCompat.getDrawable(getContext(), R.mipmap.hour_24_float);
            drawable.setBounds(getScrollBarX(),
                    Y - DisplayUtil.dip2px(getContext(), 24),
                    getScrollBarX() + ITEM_WIDTH,
                    Y - DisplayUtil.dip2px(getContext(), 4));
            drawable.draw(canvas);
            //画出温度提示
            int res = findCurrentRes(i);
            int offset = ITEM_WIDTH/2;
            if(res == -1)
                offset = ITEM_WIDTH;
            Rect targetRect = new Rect(getScrollBarX(), Y - DisplayUtil.dip2px(getContext(), 24)
                    , getScrollBarX() + offset, Y - DisplayUtil.dip2px(getContext(), 4));
            Paint.FontMetricsInt fontMetrics = textPaint.getFontMetricsInt();
            int baseline = (targetRect.bottom + targetRect.top - fontMetrics.bottom - fontMetrics.top) / 2;
            textPaint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(item.temperature + "°", targetRect.centerX(), baseline, textPaint);
        }
    }

    private int findCurrentRes(int i) {
        if(listItems.get(i).res != -1)
            return listItems.get(i).res;
        for(int k=i; k>=0; k--){
            if(listItems.get(k).res != -1)
                return listItems.get(k).res;
        }
        return -1;
    }

    private void onDrawLine(Canvas canvas, int i) {
        linePaint.setColor(new Color().YELLOW);
        linePaint.setStrokeWidth(3);
        Point point = listItems.get(i).tempPoint;
        if(i != 0){
            Point pointPre = listItems.get(i-1).tempPoint;
            Path path = new Path();
            path.moveTo(pointPre.x, pointPre.y);
            if(i % 2 == 0)
                path.cubicTo((pointPre.x+point.x)/2, (pointPre.y+point.y)/2-7, (pointPre.x+point.x)/2, (pointPre.y+point.y)/2+7, point.x, point.y);
            else
                path.cubicTo((pointPre.x+point.x)/2, (pointPre.y+point.y)/2+7, (pointPre.x+point.x)/2, (pointPre.y+point.y)/2-7, point.x, point.y);
            canvas.drawPath(path, linePaint);
        }
    }

    //绘制底部时间
    private void onDrawText(Canvas canvas, int i) {
        //此处的计算是为了文字能够居中
        Rect rect = listItems.get(i).windyBoxRect;
        Rect targetRect = new Rect(rect.left, rect.bottom, rect.right, rect.bottom + bottomTextHeight);
        Paint.FontMetricsInt fontMetrics = textPaint.getFontMetricsInt();
        int baseline = (targetRect.bottom + targetRect.top - fontMetrics.bottom - fontMetrics.top) / 2;
        textPaint.setTextAlign(Paint.Align.CENTER);

        String text = listItems.get(i).time;
        canvas.drawText(text, targetRect.centerX(), baseline, textPaint);
    }

    //设置scrollerView的滚动条的位置，通过位置计算当前的时段
    public void setScrollOffset(int offset, int maxScrollOffset){
        Log.d("------>","maxScrollOffSet" + maxScrollOffset);
        this.maxScrollOffset = maxScrollOffset;
        scrollOffset = offset;
        int index = calculateItemIndex(offset);
        currentItemIndex = index;
        invalidate();
    }

    private int calculateItemIndex(int offset){
//        Log.d(TAG, "maxScrollOffset = " + maxScrollOffset + "  scrollOffset = " + scrollOffset);
        int x = getScrollBarX();
        int sum = MARGIN_LEFT_ITEM  - ITEM_WIDTH/2;
        for(int i=0; i<ITEM_SIZE; i++){
            sum += ITEM_WIDTH;
            if(x < sum)
                return i;
        }
        return ITEM_SIZE - 1;
    }

    //计算温度提示文字的运动轨迹
    private int getTempBarY(){
        int x = getScrollBarX();
        int sum = MARGIN_LEFT_ITEM ;
        Point startPoint = null, endPoint;
        int i;
        for(i=0; i<ITEM_SIZE; i++){
            sum += ITEM_WIDTH;
            if(x < sum) {
                startPoint = listItems.get(i).tempPoint;
                break;
            }
        }
        if(i+1 >= ITEM_SIZE || startPoint == null)
            return listItems.get(ITEM_SIZE-1).tempPoint.y;
        endPoint = listItems.get(i+1).tempPoint;

        Rect rect = listItems.get(i).windyBoxRect;
        int y = (int)(startPoint.y + (x - rect.left)*1.0/ITEM_WIDTH * (endPoint.y - startPoint.y));
        return y;
    }
    private int getScrollBarX(){
        int x = (ITEM_SIZE - 1) * ITEM_WIDTH * scrollOffset / maxScrollOffset;
        x = x + MARGIN_LEFT_ITEM;
        return x;
    }

}

