package com.github.douglasjunior.bluetoothsample;

import android.os.Bundle;

import com.github.douglasjunior.bluetoothclassiclibrary.BluetoothService;
import com.github.douglasjunior.bluetoothclassiclibrary.BluetoothStatus;
import com.github.douglasjunior.bluetoothclassiclibrary.BluetoothWriter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class SettingActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener,BluetoothService.OnBluetoothEventCallback {

    private static final String TAG = "SettingActivity";

    private BluetoothService mService;
    private BluetoothWriter mWriter;

    private SeekBar SeekBar_R, SeekBar_L;
    private TextView text_R, text_L;

    private int num_R = 100;
    private int num_L = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        mService = BluetoothService.getDefaultInstance();
        mWriter = new BluetoothWriter(mService);

        SeekBar_L = (SeekBar) findViewById(R.id.SeekBar_L);
        SeekBar_R = (SeekBar) findViewById(R.id.SeekBar_R);

        SeekBar_L.setOnSeekBarChangeListener(this);
        SeekBar_R.setOnSeekBarChangeListener(this);

        text_R = (TextView) findViewById(R.id.textView_R);
        text_L = (TextView) findViewById(R.id.textView_L);

        mWriter.writeln("u");
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.SeekBar_L:
                text_L.setText(Integer.toString(progress));
                break;
            case R.id.SeekBar_R:
                text_R.setText(Integer.toString(progress));
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        switch (seekBar.getId()) {
            case R.id.SeekBar_L:
                mWriter.writeln("l" + (seekBar.getProgress()));
                break;
            case R.id.SeekBar_R:
                mWriter.writeln("r" + (seekBar.getProgress()));
                break;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        mService.setOnEventCallback(this);
    }

    @Override
    public void onDataRead(byte[] buffer, int length) {
        String tmp = new String(buffer, 0, length);
        Log.d(TAG, "onDataRead: " + tmp);
        if (tmp.substring(0, 1).equals("r")) {
            tmp = tmp.replace("r", "").trim();
            num_R = Integer.parseInt(tmp);
            Log.d(TAG, "res:" + String.valueOf(num_R));
            text_R.setText(tmp);
            SeekBar_R.setProgress(num_R);
        } else if (tmp.substring(0, 1).equals("l")) {
            tmp = tmp.replace("l", "").trim();
            num_L = Integer.parseInt(tmp);
            Log.d(TAG, "res:" + String.valueOf(num_L));
            text_L.setText(tmp);
            SeekBar_L.setProgress(num_L);
        }
    }

    @Override
    public void onStatusChange(BluetoothStatus status) {

    }

    @Override
    public void onDeviceName(String deviceName) {

    }

    @Override
    public void onToast(String message) {

    }

    @Override
    public void onDataWrite(byte[] buffer) {

    }
}
