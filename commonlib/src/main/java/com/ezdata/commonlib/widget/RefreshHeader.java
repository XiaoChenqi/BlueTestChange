package com.ezdata.commonlib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.ezdata.commonlib.R;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * Description：通知页面上拉加载的header
 * Created by：Kyle
 * Date：2017/4/28
 */

public class RefreshHeader extends FrameLayout implements PtrUIHandler {
    ImageView imageView;
    Animation progressAnimato;
    Context context;
    public RefreshHeader(Context context) {
        this(context, null, 0);
    }

    public RefreshHeader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        init();
    }
    private void init(){
        View view= LayoutInflater.from(getContext()).inflate(R.layout.header, this);
        imageView=(ImageView) view.findViewById(R.id.pb);
    }
    @Override
    public void onUIReset(PtrFrameLayout frame) {

    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        progressAnimato= AnimationUtils.loadAnimation(
                context, R.anim.loading_animation);
    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        if(progressAnimato!=null)
            imageView.startAnimation(progressAnimato);
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {
        if(progressAnimato!=null)
            progressAnimato.cancel();
    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {

    }
}
