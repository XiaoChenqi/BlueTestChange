package com.ezdata.commonlib.net;

import com.ezdata.commonlib.Constants.Constant;
import com.ezdata.commonlib.core.App;
import com.ezdata.commonlib.net.download.DownloadProgressListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit.RxJavaCallAdapterFactory;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class NetWork_OkHttp3 implements DownloadProgressListener {

    private static NetWork_OkHttp3 instance;

    /**
     * 没有把返回类型构造进去的 Retrofit，
     * 构建了超时时间，baseurl，拦截器（第三方），头文件等的 Retrofit
     */
    private Retrofit partRetrofit;
    private DownloadProgressListener listener;

    private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

    public static NetWork_OkHttp3 getInstance() {
        if (instance == null)
            instance = new NetWork_OkHttp3();
        return instance;
    }

    public static final int TIMEOUT = 1800;//todo 超时时间

    public NetWork_OkHttp3() {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                //不一定需要添加
                //添加统一的接口参数，例如：
                //100个接口都需要传userid和token的时候，可以在这里配置
                HttpUrl originalHttpUrl = chain.request().url();
                HttpUrl url = originalHttpUrl.newBuilder()
//                        .addQueryParameter("platformKey", "app_video_request") 每个接口的公共参数
                        .build();
                Request newRequest = chain.request().newBuilder()
                        //.addHeader("platformKey", "test_xcq") 每个接口的公共header
                        .url(url)
                        .build();
                Response response = chain.proceed(newRequest);
                return response;
            }
        };

        OkHttpClient okHttp3Client = new OkHttpClient().newBuilder()
                .cookieJar(new CookieJar() {
            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                cookieStore.put(url.host(), cookies);
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                List<Cookie> cookies = cookieStore.get(url.host());
                return cookies != null ? cookies : new ArrayList<Cookie>();
            }
        })
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)//设置连接超时时间
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)//设置写入超时时间
                .addInterceptor(loggingInterceptor)//添加日志拦截器
                .addInterceptor(interceptor)
                .build();


        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constant.BASE_URL)
                .addCallAdapterFactory(
                        RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(
                        App.getInstance().gson))
                .client(okHttp3Client)
                .build();
        partRetrofit = retrofit;

    }

    public Retrofit getPartRetrofit() {
        return partRetrofit;
    }

    @Override
    public void update(long bytesRead, long contentLength, boolean done) {

    }
}
