package com.gitstudy.client;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.gitstudy.gitstudy.CalculateInterface;
import com.nacy.baselibrary.Utils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    public static String TAG = "MainActivity";
    private Button click_btn;
    private Button sort_btn;
    private Integer[] data= {8,8,9,6,7,0,-1,3};
    private List<Integer> listDataOne= new ArrayList<>();
    private CalculateInterface mService;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        click_btn = findViewById(R.id.click_btn);
        sort_btn = findViewById(R.id.sort_btn);

        Bundle args = new Bundle();
        Intent intent = new Intent("com.example.calculate.CalculateService");
        intent.putExtras(args);
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
        //测试一下
        click_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
<<<<<<< HEAD:app2/src/main/java/com/gitstudy/client/MainActivity.java
                Toast.makeText(MainActivity.this,"远程计算",Toast.LENGTH_LONG).show();
                try {
                   double result =  mService.doCalculate(3,4);
                   Log.i("远程计算",result+"");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
=======
                Toast.makeText(MainActivity.this,"点击按钮",Toast.LENGTH_LONG).show();
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,WebViewActivity.class);
				//你说呢
                startActivity(intent);
>>>>>>> d866210dd1e71562080d5a86ad265606e716556a:app/src/main/java/com/gitstudy/gitstudy/MainActivity.java
            }
        });

//        AddListData mAddListData = new AddListData();
//        listDataOne.addAll(mAddListData.addListData(listData));
        sort_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer[] reult = Utils.sort(data);
                for (int i = 0; i < reult.length;i++){
                    Log.i(TAG," 冒泡排序  " +reult[i]);
                }
            }
        });
    }

public ServiceConnection mServiceConnection = new ServiceConnection() {
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        mService = CalculateInterface.Stub.asInterface(service);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }

    @Override
    public void onBindingDied(ComponentName name) {

    }

    @Override
    public void onNullBinding(ComponentName name) {

    }
};

}
