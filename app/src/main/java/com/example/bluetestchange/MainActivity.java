package com.example.bluetestchange;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.net.FmNetApi_rxjava3;
import com.example.presenter.MacAddressPresenter;
import com.example.presenter.UserBehaiverPresenter;
import com.example.presenter.iview.LoginMvpView;
import com.example.widget.CircleWaveDivergenceView;
import com.ezdata.commonlib.core.mvp.MvpView;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;

import net.vidageek.mirror.dsl.Mirror;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoginMvpView {

    private final static int SEARCH_CODE = 0x123;
    private BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private static final String TAG = "MainActivity";

    //    private List<BluetoothDevice> mBlueList = new ArrayList<>();
    private List<MyBluetoothDevice> mBlueList = new ArrayList<>();
    private ListView lisetView;
    private TextView textView1;

    private TextView addBtn;
//    private Button getLocationBtn;

    private UserBehaiverPresenter macAddressPresenter;

    private Button nextBtn;
    private Button getLocationBtn;

    private CircleWaveDivergenceView search_device_view;

    @Override
    public void onStartRequest(int requestCode) {
        Log.d(TAG, "onStartRequest: ");
    }

    @Override
    public void onSuccess(int requestCode, Object o) {
        Log.d(TAG, "onSuccess: ");
        String msg = (String) o;
        switch (requestCode) {
            case 999:

                break;
            case 1000:
//            {"msg":"Redmi","code":0}
                xcqBuilder.setMessage("您在设备：" + msg + "处");
                xcqBuilder.show();

                break;
        }
    }

    @Override
    public void onErrorCode(int resultCode, String msg, int requestCode) {
        Log.d(TAG, "onErrorCode: " + resultCode);
        search_device_view.setSearching(false);
    }

    @Override
    public void onEndRequest(int requestCode) {
        Log.d(TAG, "onEndRequest: ");
        search_device_view.setSearching(false);
    }

    @Override
    public void onFailure(Throwable e) {
        Log.d(TAG, "onFailure: " + e);
        search_device_view.setSearching(false);

//        xcqBuilder.setMessage("您在设备：+"+"vivocative"+"处");
//        xcqBuilder.show();
        //dialogXcq.show();
    }

    @Override
    public void nameNull() {

    }

    @Override
    public void pwdNull() {

    }

    AlertDialog.Builder xcqBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lisetView = (ListView) findViewById(R.id.list_view);
        textView1 = (TextView) findViewById(R.id.textView1);
        search_device_view = (CircleWaveDivergenceView) findViewById(R.id.search_device_view);
        search_device_view.setWillNotDraw(false);

        addBtn = findViewById(R.id.addBtn);
        getLocationBtn = findViewById(R.id.connectBtn);
        nextBtn = findViewById(R.id.nextBtn);
        //connectBtn = findViewById(R.id.connectBtn);
        xcqBuilder = new AlertDialog.Builder(this);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //addMacAddresToRemote();
                Intent it = new Intent(MainActivity.this, AddMacActivity.class);
                startActivity(it);
            }
        });
        getLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search_device_view.setSearching(true);
                getLocation();
            }
        });
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(MainActivity.this, BlueListShowActivity.class);
                startActivity(it);
            }
        });


        //xcqBuilder.setTitle("警告");

//        xcqBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                System.out.println("点了确定");
//            }
//        });

        /**
         * 初始化presenter
         */
        macAddressPresenter = new UserBehaiverPresenter();
        macAddressPresenter.attachView(this);
        //requestData();

        XXPermissions.with(this)
                // 申请单个权限
                .permission(Permission.BLUETOOTH_SCAN)
                .permission(Permission.BLUETOOTH_CONNECT)
                .permission(Permission.BLUETOOTH_ADVERTISE)
                .permission(Permission.ACCESS_FINE_LOCATION)
                .permission(Permission.ACCESS_COARSE_LOCATION)
                // 申请多个权限
                //.permission(Permission.Group.BLUETOOTH)
                // 设置权限请求拦截器（局部设置）
                //.interceptor(new PermissionInterceptor())
                // 设置不触发错误检测机制（局部设置）
                //.unchecked()
                .request(new OnPermissionCallback() {

                    @Override
                    public void onGranted(List<String> permissions, boolean all) {
                        if (all) {
//                            toast("获取录音和日历权限成功");
//                        } else {
//                            toast("获取部分权限成功，但部分权限未正常授予");
                            Log.e(TAG, "onCreate: GPS是否可用：" + isGpsEnable(MainActivity.this));
                            //init();// todo xcq
                        }
                    }

                    @Override
                    public void onDenied(List<String> permissions, boolean never) {
//                        if (never) {
//                            toast("被永久拒绝授权，请手动授予录音和日历权限");
//                            // 如果是被永久拒绝就跳转到应用权限系统设置页面
//                            XXPermissions.startPermissionActivity(context, permissions);
//                        } else {
//                            toast("获取录音和日历权限失败");
//                        }
                    }
                });


//        Log.e(TAG, "onCreate: GPS是否可用：" + isGpsEnable(this));
//        init();
    }

    private void addMacAddresToRemote() {
        MyBluetoothDevice device = mBlueList.get(0);
        FmNetApi_rxjava3.MacAddressInfo macAddressInfo = new FmNetApi_rxjava3.MacAddressInfo();

        //信号强度。
        int rssiStr = device.getRssi();
        //设备名称
        String deviceName = device.getBluetoothDevice().getName();
        //设备的蓝牙地（地址为17位，都为大写字母-该项貌似不可能为空）
        String deviceAddress = device.getBluetoothDevice().getAddress();

        macAddressInfo.macAddress = deviceAddress;
        macAddressInfo.realname = deviceName;
        macAddressPresenter.addMacToRemote(macAddressInfo, 999);
    }

    private void getLocation() {

        init();

//        MyBluetoothDevice device = mBlueList.get(0);
//        FmNetApi_rxjava3.MacAddressInfo macAddressInfo = new FmNetApi_rxjava3.MacAddressInfo();
//
//        //信号强度。
//        int rssiStr = device.getRssi();
//        //设备名称
//        String deviceName = device.getBluetoothDevice().getName();
//        //设备的蓝牙地（地址为17位，都为大写字母-该项貌似不可能为空）
//        String deviceAddress = device.getBluetoothDevice().getAddress();
//
//        macAddressInfo.macAddress = deviceAddress;
//        macAddressInfo.realname  =deviceName;


        /***/
//        List<FmNetApi_rxjava3.MacAddressInfo> addressInfos = new ArrayList<>();
//        for (MyBluetoothDevice bDevice : mBlueList) {
//            FmNetApi_rxjava3.MacAddressInfo addressInfo = new FmNetApi_rxjava3.MacAddressInfo();
//            addressInfo.realname = bDevice.getBluetoothDevice().getName();
//            addressInfo.macAddress = bDevice.getBluetoothDevice().getAddress();
//            addressInfo.signal = bDevice.getRssi();
//            addressInfos.add(addressInfo);
//        }
//        macAddressPresenter.getMacAddressLocation(addressInfos, 1000);
    }

    /**
     * huoqu fuwuqi de weizhi
     */
    public void update() {
        List<FmNetApi_rxjava3.MacAddressInfo> addressInfos = new ArrayList<>();
        for (MyBluetoothDevice bDevice : mBlueList) {
            FmNetApi_rxjava3.MacAddressInfo addressInfo = new FmNetApi_rxjava3.MacAddressInfo();
            addressInfo.realname = bDevice.getBluetoothDevice().getName();
            addressInfo.macAddress = bDevice.getBluetoothDevice().getAddress();
            addressInfo.signal = bDevice.getRssi();
            addressInfos.add(addressInfo);
        }
        macAddressPresenter.getMacAddressLocation(addressInfos, 1000);
    }

    //gps是否可用(有些设备可能需要定位)
    public static final boolean isGpsEnable(final Context context) {
        LocationManager locationManager
                = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            return true;
        }
        return false;
    }

    /**
     * 判断蓝牙是否开启
     */
    private void init() {
        // 判断手机是否支持蓝牙
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "设备不支持蓝牙", Toast.LENGTH_SHORT).show();
            finish();
        }
        // 判断是否打开蓝牙
        if (!mBluetoothAdapter.isEnabled()) {
            //弹出对话框提示用户是后打开
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, SEARCH_CODE);
        } else {
            // 不做提示，强行打开
            mBluetoothAdapter.enable();
        }
        startDiscovery();
        Log.e(TAG, "startDiscovery: 开启蓝牙");
    }

    /**
     * 注册异步搜索蓝牙设备的广播
     */
    private void startDiscovery() {
        // 找到设备的广播
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        // 注册广播
        registerReceiver(receiver, filter);
        // 搜索完成的广播
        IntentFilter filter1 = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        // 注册广播
        registerReceiver(receiver, filter1);
        Log.e(TAG, "startDiscovery: 注册广播");
        startScanBluth();
    }

    /**
     * 广播接收器
     */
    private final BroadcastReceiver receiver = new BroadcastReceiver() {


        @RequiresApi(api = Build.VERSION_CODES.S)
        @Override
        public void onReceive(Context context, Intent intent) {
            // 收到的广播类型
            String action = intent.getAction();
            // 发现设备的广播
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // 从intent中获取设备
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // 没否配对
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {

                    //信号强度。
                    short rssi = intent.getExtras().getShort(BluetoothDevice.EXTRA_RSSI);
                    //device.getUuids()
                    Log.d("肖晴", "获取到的设备rssi：" + rssi + "--" + device.getAddress() + "--" + device.getName());

                    MyBluetoothDevice myBlueDevice = new MyBluetoothDevice(device, rssi);


                    if (!mBlueList.contains(myBlueDevice)) {//todo xcq
                        mBlueList.add(myBlueDevice);
                    }
                    //textView1.setText("附近设备：" + mBlueList.size() + "个\u3000\u3000本机蓝牙地址：" + getBluetoothAddress());
                    textView1.setText("附近设备：" + mBlueList.size() + "个");
                    MyAdapter adapter = new MyAdapter(MainActivity.this, mBlueList);
                    lisetView.setAdapter(adapter);

                    Log.e(TAG, "onReceive: " + mBlueList.size());
                    Log.e(TAG, "onReceive: " + (device.getName() + ":" + device.getAddress() + " ：" + "m" + "\n"));
                }
                // 搜索完成
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                // 关闭进度条
                //progressDialog.dismiss();
                Log.e(TAG, "onReceive: 搜索完成");
                update();
            }
        }
    };


    private ProgressDialog progressDialog;
    //private Dialog dialogXcq = new Dialog(this);

    /**
     * 搜索蓝牙的方法
     */
    private void startScanBluth() {
        // 判断是否在搜索,如果在搜索，就取消搜索
        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }
        // 开始搜索
        mBluetoothAdapter.startDiscovery();
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
        }
        progressDialog.setMessage("正在搜索，请稍后！");
        //progressDialog.show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (receiver != null) {
            //取消注册,防止内存泄露（onDestroy被回调代不代表Activity被回收？：具体回收看系统，由GC回收，同时广播会注册到系统
            //管理的ams中，即使activity被回收，reciver也不会被回收，所以一定要取消注册），
            unregisterReceiver(receiver);
        }
    }

    /**
     * 获取本机蓝牙地址
     */
    private String getBluetoothAddress() {
        try {
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            Field field = bluetoothAdapter.getClass().getDeclaredField("mService");
            // 参数值为true，禁用访问控制检查
            field.setAccessible(true);
            Object bluetoothManagerService = field.get(bluetoothAdapter);
            if (bluetoothManagerService == null) {
                return null;
            }
            Method method = bluetoothManagerService.getClass().getMethod("getAddress");
            Object address = method.invoke(bluetoothManagerService);
            if (address != null && address instanceof String) {
                return (String) address;
            } else {
                return null;
            }
            //抛一个总异常省的一堆代码...
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取本机蓝牙地址2
     */
    private String getBluetoothAddress2() {
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        Object bluetoothManageService = new Mirror().on(adapter).get().field("mService");
        if (bluetoothManageService == null)
            return null;
        Object address = new Mirror().on(bluetoothManageService).invoke().method("getAddress").withoutArgs();
        if (address != null && address instanceof String) {
            return (String) address;
        } else {
            return null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SEARCH_CODE) {
            startDiscovery();
        }
        Log.e(TAG, "onActivityResult: " + requestCode);
        Log.e(TAG, "onActivityResult: " + resultCode);
        Log.e(TAG, "onActivityResult: " + requestCode);
    }
}