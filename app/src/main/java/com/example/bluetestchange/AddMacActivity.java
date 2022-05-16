package com.example.bluetestchange;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.net.FmNetApi_rxjava3;
import com.example.presenter.MacAddressPresenter;
import com.example.presenter.UserBehaiverPresenter;
import com.example.presenter.iview.LoginMvpView;
import com.ezdata.commonlib.core.mvp.MvpView;
import com.ezdata.commonlib.utils.DialogUtils;
import com.ezdata.commonlib.utils.ToastUtils;

public class AddMacActivity extends AppCompatActivity implements LoginMvpView {

    Button btn_logon;
    EditText macAddressEt;
    EditText macNameEt;
    private UserBehaiverPresenter macAddressPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mac);
        btn_logon = findViewById(R.id.btn_logon);
        macAddressEt =findViewById(R.id.macAddressEt);
        macNameEt = findViewById(R.id.macNameEt);
        btn_logon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMacAddresToRemote();
            }
        });

        /**
         * 初始化presenter
         */
        macAddressPresenter = new UserBehaiverPresenter();
        macAddressPresenter.attachView(this);
    }
    private void addMacAddresToRemote() {
//        MainActivity.MyBluetoothDevice device = mBlueList.get(0);
        FmNetApi_rxjava3.MacAddressInfo macAddressInfo = new FmNetApi_rxjava3.MacAddressInfo();
//
//        //信号强度。
//        int rssiStr = device.getRssi();
//        //设备名称
        String deviceName = macNameEt.getText().toString();
//        //设备的蓝牙地（地址为17位，都为大写字母-该项貌似不可能为空）
        String deviceAddress = macAddressEt.getText().toString();

        macAddressInfo.macAddress = deviceAddress;
        macAddressInfo.realname  =deviceName;
        macAddressPresenter.addMacToRemote(macAddressInfo,999);
    }
    private String TAG = "xcq";
    @Override
    public void onStartRequest(int requestCode) {
        Log.d(TAG, "onStartRequest: ");
        DialogUtils.startLoad(this, "正在写入设备……");
    }

    @Override
    public void onSuccess(int requestCode, Object o) {
        Log.d(TAG, "onSuccess: ");


        switch (requestCode) {
            case 999:
                ToastUtils.show(this, "蓝牙设备添加成功", Toast.LENGTH_SHORT);
                AddMacActivity.this.finish();
                break;

        }
    }

    @Override
    public void onErrorCode(int resultCode, String msg, int requestCode) {
        Log.d(TAG, "onErrorCode: " + msg);
        ToastUtils.show(this, msg, Toast.LENGTH_SHORT);
    }

    @Override
    public void onEndRequest(int requestCode) {
        Log.d(TAG, "onEndRequest: ");
        DialogUtils.stopLoad();
    }

    @Override
    public void onFailure(Throwable e) {
        Log.d(TAG, "onFailure: " + e);
    }

    @Override
    public void nameNull() {
        ToastUtils.show(this, "蓝牙设备名称不能为空", Toast.LENGTH_SHORT);
    }

    @Override
    public void pwdNull() {
        ToastUtils.show(this,"蓝牙地址不能为空", Toast.LENGTH_SHORT);
    }
    @Override
    protected void onPause() {
        super.onPause();
        DialogUtils.stopLoad();
    }
}