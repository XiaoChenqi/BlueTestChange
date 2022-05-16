package com.example.net;

import com.ezdata.commonlib.net.NetWork_OkHttp3;

import retrofit2.Retrofit;

//import retrofit.Retrofit;

public class MacAddressNetWork {

    private static MacAddressNetWork instance;//不带header的请求
    //private VideoNetApi videoNetApi;
    private FmNetApi_rxjava3 api3;

    public static MacAddressNetWork getInstance() {
        if (instance == null)
            instance = new MacAddressNetWork();
        return instance;
    }

    /**
     * 用于把 commoblib中的基础Retrofit，和 module的参数组成完成的 Retrofit
     */
    private MacAddressNetWork(){
        Retrofit partRetrofit = NetWork_OkHttp3.getInstance().getPartRetrofit();
        api3 = partRetrofit.create(FmNetApi_rxjava3.class);
    }

    public FmNetApi_rxjava3 getFmNetApi_rxjava3(){

        return api3;
    }
}
