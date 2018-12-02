package com.gitstudy.gitstudy

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //测试一下
        click_btn.setOnClickListener(View.OnClickListener {
            Toast.makeText(this,"点击按钮",Toast.LENGTH_LONG).show()
        })


    }
}
