package com.example.presenter;

import com.example.model.MacAddressModel;
import com.example.model.UploadFileModel;
import com.example.net.FmNetApi_rxjava3;
import com.ezdata.commonlib.core.mvp.BasePresenter;
import com.ezdata.commonlib.core.mvp.MvpView;
import com.ezdata.commonlib.net.BaseNetCallback;

import okhttp3.MultipartBody;

public class UpLoadFilePresenter extends BasePresenter<MvpView> {

    private static UploadFileModel mModel;

    public synchronized static UploadFileModel initModel() {
        if (mModel == null) {
            mModel = new UploadFileModel();
        }
        return mModel;
    }

    public UpLoadFilePresenter() {
        initModel();
    }

    /**
     * 上传文件
     */
    public void uploadFile(MultipartBody.Part file, final int requestCode){

        mModel.uploadFile(file,new BaseNetCallback(getMvpView(),requestCode));

    }
    /**
     * 上传文件
     */
    public void uploadFileVideo(MultipartBody.Part file, final int requestCode){

        mModel.uploadFileVideo(file,new BaseNetCallback(getMvpView(),requestCode));

    }

}
