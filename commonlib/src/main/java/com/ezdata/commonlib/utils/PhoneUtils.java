package com.ezdata.commonlib.utils;

import android.content.Context;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2017/3/21.
 */

public class PhoneUtils {

    private static PhoneUtils mPhoneUtils;
    private Context mContext;

    private PhoneUtils(Context c) {
        mContext = c.getApplicationContext ();
    }

    public static PhoneUtils getInstance (Context context) {
        if (mPhoneUtils == null)
            synchronized (PhoneUtils.class) {
                if (mPhoneUtils == null) {
                    mPhoneUtils = new PhoneUtils (context);
                }
            }
        return mPhoneUtils;
    }

    //    public static String getMacAddress(Context context) {
//        WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
//        String mac = wm.getConnectionInfo().getMacAddress();
//        return mac == null ? "" : mac;
//    }
    public float getDensity () {
        return mContext.getResources ().getDisplayMetrics ().density;
    }

    /**
     * @return 返回屏幕的宽度
     */
    public int getScreenW () {
        return mContext.getResources ().getDisplayMetrics ().widthPixels;
    }

    public int getScreenH () {
        return mContext.getResources ().getDisplayMetrics ().heightPixels;
    }

    public int dp2px (int value) {
        return (int) (0.5f + getDensity () * value);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px (Context context, float dpValue) {
        return Math.round (context.getResources ().getDisplayMetrics ().density * dpValue);
    }

    /**
     * convert px to its equivalent dp
     *
     * 将px转换为与之相等的dp
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale =  context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    /**
     * convert dp to its equivalent px
     *
     * 将dp转换为与之相等的px
     */
    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }


    /**
     * convert px to its equivalent sp
     *
     * 将px转换为sp
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }


    /**
     * convert sp to its equivalent px
     *
     * 将sp转换为px
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public int getStatusBarHeight () {
        Class<?> c;
        Object obj;
        Field field;
        int x, statusBar = 0;
        try {
            c = Class.forName ("com.android.internal.R$dimen");
            obj = c.newInstance ();
            field = c.getField ("status_bar_height");
            x = Integer.parseInt (field.get (obj).toString ());
            statusBar = mContext.getResources ().getDimensionPixelSize (x);
        } catch (Exception e1) {
            e1.printStackTrace ();
        }
        return statusBar;
    }
}
