package com.ezdata.commonlib.core;

import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.ezdata.commonlib.R;

/**
 * Created by MSI-PC on 2018/5/25.
 */

public abstract class BaseToolBarActivity extends BaseNoHandlerActivity {

    public Toolbar mToolbar;
    public TextView titleTv;
    public RelativeLayout backRl;

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        this.initToolbarHelper();
    }

    /**
     * init the toolbar
     */
    protected void initToolbarHelper() {
        if (this.mToolbar == null) {
            mToolbar = findView(R.id.toolBar);
            titleTv = findViewById(R.id.titleTv);
            backRl = findViewById(R.id.backRl);
        }
    }
    public void setTitle(String title){
        if(mToolbar!=null)
            mToolbar.setTitle("");
        if(titleTv!=null)
            titleTv.setText(title);

    }
    public void setTitle(int id){
        setTitle(getString(id));
    }
    protected void showBack() {

        backRl.setVisibility(View.VISIBLE);
        backRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onToolbarBack();
            }
        });
    }
    protected abstract void onToolbarBack();
}
