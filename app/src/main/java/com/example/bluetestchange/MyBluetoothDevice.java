package com.example.bluetestchange;


import android.bluetooth.BluetoothDevice;

public  class MyBluetoothDevice {
    private BluetoothDevice bluetoothDevice;
    private int rssi;//用于计算距离，信号强度

    public MyBluetoothDevice(BluetoothDevice bluetoothDevice, int rssi) {
        this.bluetoothDevice = bluetoothDevice;
        this.rssi = rssi;
    }

    public BluetoothDevice getBluetoothDevice() {
        return bluetoothDevice;
    }

    public void setBluetoothDevice(BluetoothDevice bluetoothDevice) {
        this.bluetoothDevice = bluetoothDevice;
    }

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }
}
