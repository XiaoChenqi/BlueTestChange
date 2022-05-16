package com.example.model;

import com.example.model.imodel.IrgbPicModel;
import com.example.net.FmNetApi_rxjava3;
import com.example.net.MacAddressNetWork;
import com.ezdata.commonlib.core.mvp.BaseObserver;
import com.ezdata.commonlib.net.INetCallback;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;

public class UploadFileModel implements IrgbPicModel {

    @Override
    public void uploadFile(MultipartBody.Part file, INetCallback callback) {
        MacAddressNetWork.getInstance().getFmNetApi_rxjava3().uploadFile(file)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        if (callback != null)
                            callback.startRequest();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver(callback));
    }

    @Override
    public void uploadFileVideo(MultipartBody.Part file, INetCallback callback) {
        MacAddressNetWork.getInstance().getFmNetApi_rxjava3().uploadFileVideo(file)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        if (callback != null)
                            callback.startRequest();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver(callback));
    }
}
