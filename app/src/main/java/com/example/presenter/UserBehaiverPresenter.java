package com.example.presenter;

import android.text.TextUtils;

import com.example.model.MacAddressModel;
import com.example.net.FmNetApi_rxjava3;
import com.example.presenter.iview.LoginMvpView;
import com.ezdata.commonlib.core.mvp.BasePresenter;
import com.ezdata.commonlib.net.BaseNetCallback;

import java.util.List;


/**
 * Created by MSI-PC on 2018/6/29.
 */

public class UserBehaiverPresenter extends BasePresenter<LoginMvpView> {

    private static MacAddressModel macAddressModel;

    public synchronized static MacAddressModel initModel() {
        if (macAddressModel == null) {
            macAddressModel = new MacAddressModel();
        }
        return macAddressModel;
    }

    public UserBehaiverPresenter() {
        initModel();
    }

    /**
     * 登录
     * @param name
     * @param pwd
     * @param requestCode
     */
    public void login(String name,String pwd,final int requestCode){

        if(TextUtils.isEmpty(name)){
            getMvpView().nameNull();
            return;
        }else if (TextUtils.isEmpty(pwd)){
            getMvpView().pwdNull();
            return;
        }
        //userModel.login(name,pwd,new BaseNetCallback(getMvpView(),requestCode));
    }

    /**
     * 添加mac地址到远程仓库
     */
    public void addMacToRemote(FmNetApi_rxjava3.MacAddressInfo bean, final int requestCode){
//        if(TextUtils.isEmpty(bean.realname)){
//            getMvpView().nameNull();
//            return;
//        }else
//
        if (TextUtils.isEmpty(bean.macAddress)){
            getMvpView().pwdNull();
            return;
        }
        macAddressModel.addMacAddressToRemote(bean,new BaseNetCallback(getMvpView(),requestCode));

    }

//    /**
//     * 注销
//     * @param requestCode
//     */
//    public void logout(final int requestCode){
//        userModel.logout(new BaseNetCallback(getMvpView(),requestCode));
//    }

    /**
     * 添加mac地址到远程仓库
     */
    public void getMacAddressLocation(List<FmNetApi_rxjava3.MacAddressInfo> bean, final int requestCode){

        macAddressModel.getMacAddressLocation(bean,new BaseNetCallback(getMvpView(),requestCode));

    }
    public void login(FmNetApi_rxjava3.LoginBean bean, final int requestCode){
        if(TextUtils.isEmpty(bean.username)){
            getMvpView().nameNull();
            return;
        }else if (TextUtils.isEmpty(bean.password)){
            getMvpView().pwdNull();
            return;
        }
        macAddressModel.login(bean,new BaseNetCallback(getMvpView(),requestCode));

    }
}
