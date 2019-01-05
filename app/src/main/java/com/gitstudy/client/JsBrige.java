package com.gitstudy.client;

import android.content.Context;
import android.os.Build;
import android.webkit.ValueCallback;
import android.webkit.WebView;

public class JsBrige {
    private Context mContext;
    private WebView mWebView;
    public JsBrige(Context context, WebView webView){
        this.mContext = context;
        this.mWebView = webView;
    }

    public void transJavaTojs(String script){
        if (mWebView == null)
            return;
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT){
            mWebView.evaluateJavascript(script, new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {

                }
            });
        }else {
            mWebView.loadUrl(script);
        }
    }
}
