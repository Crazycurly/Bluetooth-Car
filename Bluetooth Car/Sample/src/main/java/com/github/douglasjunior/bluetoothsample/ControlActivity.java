package com.github.douglasjunior.bluetoothsample;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.douglasjunior.bluetoothclassiclibrary.BluetoothService;
import com.github.douglasjunior.bluetoothclassiclibrary.BluetoothStatus;
import com.github.douglasjunior.bluetoothclassiclibrary.BluetoothWriter;

import io.github.controlwear.virtual.joystick.android.JoystickView;

public class ControlActivity extends AppCompatActivity implements BluetoothService.OnBluetoothEventCallback, View.OnClickListener {

    private static final String TAG = "ControlActivity";

    private BluetoothService mService;
    private BluetoothWriter mWriter;

    private TextView mTextViewAngleLeft;
    private TextView mTextViewStrengthLeft;

     Button button_setting, button_debug;

     private int tmp_data=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

        mService = BluetoothService.getDefaultInstance();
        mWriter = new BluetoothWriter(mService);

        mTextViewAngleLeft = (TextView) findViewById(R.id.textView_angle_left);
        mTextViewStrengthLeft = (TextView) findViewById(R.id.textView_strength_left);

        button_setting = (Button) findViewById(R.id.button_setting);
        button_debug = (Button) findViewById(R.id.button_debug);
        button_setting.setOnClickListener(this);
        button_debug.setOnClickListener(this);

        JoystickView joystick = (JoystickView) findViewById(R.id.joystickView);
        joystick.setOnMoveListener(new JoystickView.OnMoveListener() {
            @Override
            public void onMove(int angle, int strength) {
                angle=360-angle;
                int dir = ((int) (angle + 22.5)%360 / 45) + 1;
                if(strength<35)
                    dir=0;
                send(dir);
                mTextViewAngleLeft.setText(angle + "°"+dir);
                mTextViewStrengthLeft.setText(strength + "%");

            }
        });
    }

    protected void send(int data){
        if(data !=tmp_data){
            mWriter.writeln(Integer.toString(data));
            tmp_data=data;
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        mService.setOnEventCallback(this);
    }

    @Override
    public void onDataRead(byte[] buffer, int length) {
        Log.d(TAG, "onDataRead: " + new String(buffer, 0, length));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mService.disconnect();
    }

    @Override
    public void onStatusChange(BluetoothStatus status) {
        Log.d(TAG, "onStatusChange: " + status);
    }

    @Override
    public void onDeviceName(String deviceName) {
        Log.d(TAG, "onDeviceName: " + deviceName);
    }

    @Override
    public void onToast(String message) {
        Log.d(TAG, "onToast");
    }

    @Override
    public void onDataWrite(byte[] buffer) {
        Log.d(TAG, "onDataWrite");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_debug:
                Intent intent_debug = new Intent(this, DeviceActivity.class);
                intent_debug.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent_debug);
                break;
            case R.id.button_setting:
                Intent intent_setting = new Intent(this, SettingActivity.class);
                intent_setting.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent_setting);
                break;
        }
    }
}
