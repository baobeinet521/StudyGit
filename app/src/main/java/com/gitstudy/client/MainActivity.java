package com.gitstudy.client;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.gitstudy.patch.PatchManipulateImp;
import com.gitstudy.patch.PermissionUtils;
import com.gitstudy.patch.RobustCallBackSample;
import com.meituan.robust.PatchExecutor;
import com.meituan.robust.patch.annotaion.Add;
import com.meituan.robust.patch.annotaion.Modify;
import com.nacy.baselibrary.Utils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static String TAG = "MainActivity";
    private Button click_btn;
    private Button sort_btn;
    private Button handler1Btn;
    private Button handler2Btn;
    private Button patch;
    private Integer[] data = {8, 8, 9, 6, 7, 0, -1, 3};
    private List<Integer> listDataOne = new ArrayList<>();

    public Handler mHandler1 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            Log.i(TAG, "mHandler1    " + what);
        }
    };

    public Handler mHandler2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            Log.i(TAG, "mHandler2    " + what);
        }
    };

    @Modify
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        click_btn = findViewById(R.id.click_btn);
        sort_btn = findViewById(R.id.sort_btn);
        handler1Btn = findViewById(R.id.handler1_btn);
        handler2Btn = findViewById(R.id.handler2_btn);
        patch = findViewById(R.id.patch);

        //测试一下
        click_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "点击按钮", Toast.LENGTH_LONG).show();
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, WebViewActivity.class);
                startActivity(intent);
            }
        });

//        AddListData mAddListData = new AddListData();
//        listDataOne.addAll(mAddListData.addListData(listData));
        sort_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer[] reult = Utils.sort(data);
                for (int i = 0; i < reult.length; i++) {
                    Log.i(TAG, " 冒泡排序  " + reult[i]);
                }
            }
        });
        handler1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message mMessage = Message.obtain();
                mMessage.what = 1;
                mHandler1.sendEmptyMessage(mMessage.what);
            }
        });
        handler2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBackGround();
                Message mMessage = Message.obtain();
                mMessage.what = 2;
                mHandler2.sendEmptyMessage(mMessage.what);
            }
        });

        //beigin to patch
        patch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isGrantSDCardReadPermission()) {
                    runRobust();
                } else {
                    requestPermission();
                }
            }
        });
    }

    //增加方法
    @Add
    public void setBackGround() {
        handler2Btn.setBackgroundColor(getResources().getColor(R.color.green_color));
        Toast.makeText(MainActivity.this, "点击handler2", Toast.LENGTH_LONG).show();
    }

    //    ipublic ListNode addTwoNumbers(ListNode l1, ListNode l2) {
//        ListNode result = new ListNode(0);
//        ListNode first = l1, second = l2, tmpNode = result;
//        int tmp = 0;
//
//        while(first != null || second != null){
//            int firstValue ,secondValue;
//            if(first == null){firstValue = 0;}
//            else{firstValue = first.val;}
//
//            if(second == null){secondValue = 0;}
//            else{secondValue = second.val;}
//
//            tmp = tmp + firstValue + secondValue;
//            tmpNode.next = new ListNode(tmp % 10);
//            tmp = tmp / 10;
//
//            tmpNode = tmpNode.next;
//            if(first != null){first = first.next;}
//            if(second != null){second = second.next;}
//        }
//        if(tmp != 0){
//            tmpNode.next = new ListNode(tmp % 10);
//        }
//        return result.next;
//    }
    private boolean isGrantSDCardReadPermission() {
        return PermissionUtils.isGrantSDCardReadPermission(this);
    }

    private void requestPermission() {
        PermissionUtils.requestSDCardReadPermission(this, REQUEST_CODE_SDCARD_READ);
    }

    private static final int REQUEST_CODE_SDCARD_READ = 1;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_SDCARD_READ:
                handlePermissionResult();
                break;

            default:
                break;
        }
    }

    private void handlePermissionResult() {
        if (isGrantSDCardReadPermission()) {
            runRobust();
        } else {
            Toast.makeText(this, "failure because without sd card read permission", Toast.LENGTH_SHORT).show();
        }

    }

    private void runRobust() {
        new PatchExecutor(getApplicationContext(), new PatchManipulateImp(), new RobustCallBackSample()).start();
    }
}
