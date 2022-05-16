package com.ezdata.commonlib.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("DrawAllocation")
public class MenuGroup extends LinearLayout {

    private int mCurrentItem = -1;

    public int getCurrentItem() {
        return mCurrentItem;
    }

    private List<MenuButton> buttons = new ArrayList<MenuButton>();
    private OnMenuItemClickListener onMenuItemClickListener;

    public void setOnMenuItemClickListener(
            OnMenuItemClickListener onMenuItemClickListener) {
        this.onMenuItemClickListener = onMenuItemClickListener;
    }

    public interface OnMenuItemClickListener {
        void onItemClick(int position, View v);
    }

    public MenuGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            buttons.clear();
            if (getChildCount() > 0) {
                for (i = 0; i < getChildCount(); i++) {
                    buttons.add((MenuButton) getChildAt(i));
                }
            }
            if (buttons.size() > 0) {
                if(mCurrentItem>=0){
                    gotoIndex(mCurrentItem);
                }
//                gotoIndex(0);
//                mCurrentItem = 0;
                for (i = 0; i < getChildCount(); i++) {
                    buttons.get(i).setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            gotoIndex(buttons.indexOf(v));
                        }
                    });
                }
            }
        }
    }

    public void gotoIndex(int position) {

        setItem(position);

        if (getChildCount() > position && position != mCurrentItem) {

            mCurrentItem = position;
            if (onMenuItemClickListener != null) {
                onMenuItemClickListener.onItemClick(mCurrentItem,
                        buttons.get(position));
            }

        }
    }

    //	public void gotoIndex(int position) {
//		//4的问题出在这里
//        if (position != 3)
//            setItem(position);
//        else
//            buttons.get(position).setChecked(false);
//        if (getChildCount() > position && position != mCurrentItem) {
//            if (position != 3) {
//                mCurrentItem = position;
//                if (onMenuItemClickListener != null) {
//                    onMenuItemClickListener.onItemClick(mCurrentItem,
//                            buttons.get(position));
//                }
//            } else {
//                if (onMenuItemClickListener != null) {
//                    onMenuItemClickListener.onItemClick(3,
//                            buttons.get(position));
//                }
//            }
//        }
//    }
    int i = 0;

    public void setItem(int position) {
        if (buttons.size() == 0) return;
        buttons.get(position).setChecked(true);
        for (int i = 0; i < getChildCount(); i++) {
            if (position != i) {
                buttons.get(i).setChecked(false);
            }
        }
    }

    public void setDefaultPosition(int position){
        mCurrentItem =  position;
    }
}
