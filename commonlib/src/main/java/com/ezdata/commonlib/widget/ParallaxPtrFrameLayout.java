package com.ezdata.commonlib.widget;

import android.content.Context;
import android.util.AttributeSet;

import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Description：上啦加载控件
 * Created by：Kyle
 * Date：2017/4/28
 */

public class ParallaxPtrFrameLayout extends PtrFrameLayout{
    private RefreshHeader header;
    public ParallaxPtrFrameLayout(Context context) {
        super(context);
        initViews();
    }

    public ParallaxPtrFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public ParallaxPtrFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initViews();
    }
    private void initViews() {
        header = new RefreshHeader(getContext());
        setHeaderView(header);
        addPtrUIHandler(header);
    }
}
