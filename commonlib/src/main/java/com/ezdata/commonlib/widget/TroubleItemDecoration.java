package com.ezdata.commonlib.widget;

import android.content.Context;
import android.graphics.Rect;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.ezdata.commonlib.R;


/**
 * Created by ADMIN on 2017/11/15.
 */

public class TroubleItemDecoration extends RecyclerView.ItemDecoration {
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
//        outRect.set(0,0,0,);

        if (parent != null) {
//                int childIndex=parent.getChildPosition(view);//deprecated
            int childIndex = parent.getChildAdapterPosition(view);
            RecyclerView.Adapter adapter = parent.getAdapter();
            if (adapter != null) {
                int childCount = adapter.getItemCount();
                if (childIndex == 0) {//the first one，第一个，左边缘间距
                    outRect.bottom = 0;//12dp;
                }else {
                    outRect.bottom = dip2px(view.getContext(), view.getContext().getResources().getDimension(R.dimen.dp_6));
                }

            }
        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        return Math.round(context.getResources().getDisplayMetrics().density * dpValue);
    }
}
