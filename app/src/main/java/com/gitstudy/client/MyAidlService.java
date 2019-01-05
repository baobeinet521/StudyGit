package com.gitstudy.client;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.gitstudy.gitstudy.CalculateInterface;

public class MyAidlService extends Service{

    private static final String TAG = "MyAidlService";
    public MyAidlService() {
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBindler;
    }

    public CalculateInterface.Stub mBindler = new CalculateInterface.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public double doCalculate(double a, double b) throws RemoteException {
            Log.e("Calculate", "远程计算中");
            return a+b;
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
