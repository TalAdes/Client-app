package com.example.secondapp.models.backend;

import android.app.Service;
import android.bluetooth.BluetoothClass;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by liran on 21/01/2018.
 */

public class Services extends Service {
    public Services() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Thread thread = new Thread(){
            @Override
            public void run() {
                int i = 0;
                while(true){
                    try{
                        Thread.sleep(1000);
                        i++;
                    }
                    catch (InterruptedException error)
                    {

                    }
                    Intent intent = new Intent("com.secondapp.UPDATE");
                    Services.this.sendBroadcast(intent);
                }
            }
        };
        thread.start();

        return START_REDELIVER_INTENT;

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
