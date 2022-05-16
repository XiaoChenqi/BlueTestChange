package com.example.bluetestchange;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.net.FmNetApi_rxjava3;
import com.example.presenter.UserBehaiverPresenter;
import com.example.presenter.iview.LoginMvpView;
import com.ezdata.commonlib.utils.ToastUtils;

public class LogonActivity extends AppCompatActivity implements LoginMvpView {

    Button btn_logon;
    EditText et_username;
    EditText et_password;
    private UserBehaiverPresenter macAddressPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logon);
        btn_logon =findViewById(R.id.btn_logon);
        et_username = findViewById(R.id.et_username);
        et_password =findViewById(R.id.et_password);
        btn_logon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();

            }
        });

        /**
         * 初始化presenter
         */
        macAddressPresenter = new UserBehaiverPresenter();
        macAddressPresenter.attachView(this);
    }

    private void login() {
        FmNetApi_rxjava3.LoginBean bean= new FmNetApi_rxjava3.LoginBean();
        bean.username = et_username.getText().toString();
        bean.password = et_password.getText().toString();
        macAddressPresenter.login(bean, 999);
    }

    @Override
    public void nameNull() {
        ToastUtils.show(this, "用户名不能为空", Toast.LENGTH_SHORT);
    }

    @Override
    public void pwdNull() {
        ToastUtils.show(this, "密码不能为空", Toast.LENGTH_SHORT);
    }

    @Override
    public void onStartRequest(int requestCode) {

    }

    @Override
    public void onSuccess(int requestCode, Object o) {
        Intent it  = new Intent(LogonActivity.this,MainActivity.class);
        startActivity(it);
    }

    @Override
    public void onErrorCode(int resultCode, String msg, int requestCode) {

    }

    @Override
    public void onEndRequest(int requestCode) {

    }

    @Override
    public void onFailure(Throwable e) {
        Intent it  = new Intent(LogonActivity.this,MainActivity.class);
        startActivity(it);
    }
}