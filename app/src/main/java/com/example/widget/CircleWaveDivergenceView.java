package com.example.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.bluetestchange.BuildConfig;
import com.example.bluetestchange.R;

import androidx.annotation.Nullable;

/**
 * Created by 王帝 on 2017/12/11.
 */

public class CircleWaveDivergenceView extends View {
    public static final String TAG = "SearchDevicesView";
    public static final boolean D  = BuildConfig.DEBUG;
    public Context context;
    @SuppressWarnings("unused")
    private long TIME_DIFF = 1500;
    private float offsetArgs = 0;
    private boolean isSearching = false;
    private Bitmap bitmap;
    private Bitmap bitmap1;
    private Bitmap bitmap2;

    public boolean isSearching() {
        return isSearching;
    }

    public void setSearching(boolean isSearching) {
        this.isSearching = isSearching;
        offsetArgs = 0;
        invalidate();
    }

    public CircleWaveDivergenceView(Context context) {
        super(context);
        this.context = context;
        initBitmap();
    }

    public CircleWaveDivergenceView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initBitmap();
    }

    public CircleWaveDivergenceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initBitmap();
    }
    private void initBitmap(){
        if(bitmap == null){
            bitmap = Bitmap.createBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.gplus_search_bg));
        }
        if(bitmap1 == null){
            bitmap1 = Bitmap.createBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.locus_round_click));
        }
        if(bitmap2 == null){
            bitmap2 = Bitmap.createBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.gplus_search_args));
        }
    }
    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap, getWidth() / 2 - bitmap.getWidth() / 2, getHeight() / 2 - bitmap.getHeight() / 2, null);
        if(isSearching){
            Rect rMoon = new Rect(getWidth()/2-bitmap2.getWidth(),getHeight()/2,getWidth()/2,getHeight()/2+bitmap2.getHeight());
            canvas.rotate(offsetArgs,getWidth()/2,getHeight()/2);
            canvas.drawBitmap(bitmap2,null,rMoon,null);
            offsetArgs = offsetArgs + 3;
        }else{
            canvas.drawBitmap(bitmap2,  getWidth() / 2  - bitmap2.getWidth() , getHeight() / 2, null);
        }
        canvas.drawBitmap(bitmap1,  getWidth() / 2 - bitmap1.getWidth() / 2, getHeight() / 2 - bitmap1.getHeight() / 2, null);
        if(isSearching) invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                handleActionDownEvenet(event);
                return true;
            case MotionEvent.ACTION_MOVE:
                return true;
            case MotionEvent.ACTION_UP:
                return true;
        }
        return super.onTouchEvent(event);
    }

    private void handleActionDownEvenet(MotionEvent event){
        RectF rectF = new RectF(getWidth() / 2 - bitmap1.getWidth() / 2,
                getHeight() / 2 - bitmap1.getHeight() / 2,
                getWidth() / 2 + bitmap1.getWidth() / 2,
                getHeight() / 2 + bitmap1.getHeight() / 2);

        if(rectF.contains(event.getX(), event.getY())){
            if(D) Log.d(TAG, "click search device button");
            if(!isSearching()) {
                setSearching(true);
            }else{
                setSearching(false);
            }
        }
    }
}
