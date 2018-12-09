package com.gitstudy.gitstudy;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.nacy.baselibrary.Utils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    public static String TAG = "MainActivity";
    private Button click_btn;
    private Button sort_btn;
    private Integer[] data= {8,8,9,6,7,0,-1,3};
    private List<Integer> listDataOne= new ArrayList<>();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        click_btn = findViewById(R.id.click_btn);
        sort_btn = findViewById(R.id.sort_btn);
        //测试一下
        click_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"点击按钮",Toast.LENGTH_LONG).show();
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,WebViewActivity.class);
                startActivity(intent);
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


}
