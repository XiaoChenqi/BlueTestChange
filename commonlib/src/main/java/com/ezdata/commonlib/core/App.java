package com.ezdata.commonlib.core;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import androidx.multidex.MultiDex;
import android.util.Log;
import android.widget.Toast;

import com.ezdata.commonlib.utils.ToastUtils;
import com.ezdata.commonlib.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.orhanobut.logger.Logger;

/**
 * Created by MSI-PC on 2018/4/4.
 */

public class App extends Application {

    public static boolean bLog = true;
    public Gson gson;


    private static App instance;

    public static App getInstance() {
        return instance;
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Utils.init(this);
        initGson();
        registerActivityLifecycleCallbacks(alCbs);//注册全局监听activity生命周期的回调

    }


//    @Override
//    public void onCaughtException(Thread thread, Throwable throwable, boolean isSafeMode) {
//        //super.onCaughtException(thread, throwable, isSafeMode);
//        throwable.printStackTrace();
//        //过滤这个可以看到崩溃内容System.err
//        ToastUtils.show(instance,"捕获到崩溃异常，已阻止", Toast.LENGTH_LONG);
//    }

    private void initGson() {
        gson = new GsonBuilder().create();
    }

    public static void d(String s) {
        if (bLog)
            Logger.d(s);
    }

    public static void e(String s) {
        if (bLog)
            Logger.e(s);
    }

    /**
     * 显示行数，方法名，类名
     *
     * @param s
     */
    public static void d_loc(String s) {
        final StackTraceElement[] stack = new Throwable().getStackTrace();
        final StackTraceElement ste = stack[1];

        if (bLog) {

            Logger.d(String.format("[%s][%s] \n [%d]%s", ste.getClassName(), ste.getMethodName(),
                    ste.getLineNumber(), s));
        }
    }

    @Override
    public void onTrimMemory(int level) {
        App.d("onTrimMemory" + level);
        super.onTrimMemory(level);
    }

    @Override
    public void onLowMemory() {
        App.d("onLowMemory");
        super.onLowMemory();
    }

    private String TAG2 = "home测试";
    /**
     * 全局activity生命周期的一些监控
     */
    private Application.ActivityLifecycleCallbacks alCbs = new Application.ActivityLifecycleCallbacks() {

        @Override
        public void onActivityCreated(Activity activity, Bundle bundle) {
            //TODO 初始化toolbar或者底部菜单，避免使用baseActivity，这样更优雅
            //说明：此处可以操作第三方没有继承baseactivity的activity，可以避免一些第三方引起的冲突
            //Utils.setNavigationBarVisible(activity,false);//导航菜单不可见
        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {
            Log.d(TAG2, "onActivityResumed: ");

        }

        @Override
        public void onActivityPaused(Activity activity) {
            //TODO 打包之前要修改回来，删除近期任务的其他任务，只保留这个app的任务
//            ActivityManager activityManager = (ActivityManager) getApplicationContext()
//                    .getSystemService(Context.ACTIVITY_SERVICE);
//            Log.d(TAG2, "onActivityPaused:DISABLE_STATUS: "+DISABLE_STATUS);
//            if(DISABLE_STATUS){
//                assert activityManager != null;//断言
//                if(activityManager!=null){
//
//                    Log.d(TAG2, "包名:"+ activity.getLocalClassName());
//                    Log.d(TAG2, "onActivityPaused: activitymanger不为空");
//                    //TODO 获取activity包名
//                    activityManager.moveTaskToFront(activity.getTaskId(), 0);
//                }else{
//                    //TODO 获取activity包名
//                    Log.d(TAG2, "包名:"+ activity.getLocalClassName());
//                    Log.d(TAG2, "onActivityPaused: activitymanger为空");
//                }
//
//            }
        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }
    };

}
