package com.example.net;

import com.ezdata.commonlib.bean.BaseResponse;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;


public interface FmNetApi_rxjava3 {

    @POST("/system/mac/addApp")
    Observable<BaseResponse> addMacToRemote(@Body MacAddressInfo addressInfo);

    @POST("/system/mac/location")
    Observable<BaseResponse> getLocation(@Body List<MacAddressInfo> addressInfo);

    @Multipart
    @POST("/opencv/img")
    Observable<BaseResponse> uploadFile(@Part MultipartBody.Part fileBody);

    @Multipart
    @POST("/opencv/video")
    Observable<BaseResponse> uploadFileVideo(@Part MultipartBody.Part fileBody);


    @POST("/login")
    Observable<BaseResponse> login(@Body LoginBean bean);
    public class MacAddressInfo {
        public String realname;
        public String macAddress;
        public int signal;//信号强度
    }

    public class LoginBean {
        public String username;
        public String password;

    }

}
