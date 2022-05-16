package com.example;

import com.example.utils.FM;
import com.ezdata.commonlib.core.App;

public class MacAddressApp extends App {

    public static final long ONE_KB = 1024L;
    public static final long ONE_MB = ONE_KB * 1024L;
    public static final long CACHE_DATA_MAX_SIZE = ONE_MB * 3L;

    private static final String CACHE_PATH = "com.fm.res";

    @Override
    public void onCreate() {
        super.onCreate();
        //initReservoir();
        //TODO 需要增加推送
        //TODO 测试代码，增加阿里路由
        //initARouter();

        FM.init(MacAddressApp.getInstance())
                //.withIcon(new FontResModule())      //初始化字体图标
                //.withIcon(new FontAwesomeModule())  //初始化字体图标
                .withFmRes(CACHE_PATH)          //媒体文件存储地址
                .configure(true);
    }
//    private void initARouter() {
//        //if (Utils.isAppDebug()) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
//        ARouter.openLog();     // 打印日志
//        ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
//        //}
//        ARouter.init(this); // 尽可能早，推荐在Application中初始化
//    }

    /**
     * 初始化数据库轻量级框架
     */
//    private void initReservoir() {
//        try {
//            Reservoir.init(this, CACHE_DATA_MAX_SIZE, gson);
//        } catch (Exception e) {
//            //failure
//            e.printStackTrace();
//        }
//    }
}
