package com.artv.android.core.display;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by mRogach on 26.08.2015.
 */
public class AlarmTurnOffReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context arg0, Intent arg1) {
        Toast.makeText(arg0, "Alarm received!", Toast.LENGTH_LONG).show();
//        shutDownDevice();
    }

    private final void shutDownDevice() {
        try {
            Process process = Runtime.getRuntime().exec("su");
            DataOutputStream outputStream = new DataOutputStream(
                    process.getOutputStream());
            outputStream.writeBytes("reboot -p" + "\n");
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}