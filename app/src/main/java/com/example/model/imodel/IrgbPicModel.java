package com.example.model.imodel;

import com.example.net.FmNetApi_rxjava3;
import com.ezdata.commonlib.net.INetCallback;

import java.util.List;

import okhttp3.MultipartBody;

public interface IrgbPicModel {


    /**
     * 上传文件
     * @param file
     * @param callback
     */
    void uploadFile(MultipartBody.Part file, INetCallback callback);
    void uploadFileVideo(MultipartBody.Part file, INetCallback callback);
}
