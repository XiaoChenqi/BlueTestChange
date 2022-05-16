package com.example.model.imodel;

import com.example.net.FmNetApi_rxjava3;
import com.ezdata.commonlib.net.INetCallback;

import java.util.List;

public interface IAddressModel {

    /**
     * 保存mac地址
     * @param bean
     * @param callback
     */
    void addMacAddressToRemote(FmNetApi_rxjava3.MacAddressInfo bean, INetCallback callback);

    /**
     * 获取定位信息
     * @param bean
     * @param callback
     */
    void getMacAddressLocation(List<FmNetApi_rxjava3.MacAddressInfo> bean, INetCallback callback);

    void login(FmNetApi_rxjava3.LoginBean bean, INetCallback callback);
}
