package com.gitstudy.viewutils;

import android.util.Log;

public class Singleton {
    private static final String TAG="singleton";
    private static  Singleton mInstance;
    private Singleton (){
    }
    public static synchronized Singleton getInstance(){
        Log.i(TAG,"Singleton getInstance");
        if(mInstance == null){
            mInstance = new Singleton();
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mInstance;
    }

    public static void test(){
        Log.i(TAG,"Singleton test");
    }
}
