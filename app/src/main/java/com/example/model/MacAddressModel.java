package com.example.model;

import com.example.model.imodel.IAddressModel;
import com.example.net.FmNetApi_rxjava3;
import com.example.net.MacAddressNetWork;
import com.ezdata.commonlib.core.mvp.BaseObserver;
import com.ezdata.commonlib.net.INetCallback;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MacAddressModel implements IAddressModel {
    @Override
    public void addMacAddressToRemote(FmNetApi_rxjava3.MacAddressInfo bean, INetCallback callback) {
        MacAddressNetWork.getInstance().getFmNetApi_rxjava3().addMacToRemote(bean)
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
    public void getMacAddressLocation(List<FmNetApi_rxjava3.MacAddressInfo> bean, INetCallback callback) {
        MacAddressNetWork.getInstance().getFmNetApi_rxjava3().getLocation(bean)
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
    public void login(FmNetApi_rxjava3.LoginBean bean, INetCallback callback) {
        MacAddressNetWork.getInstance().getFmNetApi_rxjava3().login(bean)
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
