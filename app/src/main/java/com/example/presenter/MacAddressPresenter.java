package com.example.presenter;

import com.example.model.MacAddressModel;
import com.example.net.FmNetApi_rxjava3;
import com.ezdata.commonlib.core.mvp.BasePresenter;
import com.ezdata.commonlib.core.mvp.MvpView;
import com.ezdata.commonlib.net.BaseNetCallback;


import java.util.List;

public class MacAddressPresenter extends BasePresenter<MvpView> {

    private static MacAddressModel macAddressModel;

    public synchronized static MacAddressModel initModel() {
        if (macAddressModel == null) {
            macAddressModel = new MacAddressModel();
        }
        return macAddressModel;
    }

    public MacAddressPresenter() {
        initModel();
    }

    /**
     * 添加mac地址到远程仓库
     */
    public void addMacToRemote(FmNetApi_rxjava3.MacAddressInfo bean, final int requestCode){

        macAddressModel.addMacAddressToRemote(bean,new BaseNetCallback(getMvpView(),requestCode));

    }


}
