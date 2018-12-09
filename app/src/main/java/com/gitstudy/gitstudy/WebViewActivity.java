package com.gitstudy.gitstudy;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Set;

public class WebViewActivity extends AppCompatActivity{
    private WebView mWebView;
    private Button mJavaToJsBtn;
    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);
        mJavaToJsBtn = findViewById(R.id.javaTojs);
        mWebView = findViewById(R.id.webview_view);
        // 得到设置属性的对象
        WebSettings webSettings = mWebView.getSettings();
        // 使能JavaScript
        webSettings.setJavaScriptEnabled(true);
        // 支持中文，否则页面中中文显示乱码
        webSettings.setDefaultTextEncodingName("UTF-8");
        // 设置允许JS弹窗
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.loadUrl("file:///android_asset/webTest.html");
        mWebView.addJavascriptInterface(new JSInterface(), "test");
        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                AlertDialog.Builder b = new AlertDialog.Builder(WebViewActivity.this);
                b.setTitle("Alert");
                b.setMessage(message);
                b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.confirm();
                    }
                });
                b.setCancelable(false);
                b.create().show();
                return false;
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                return super.onJsConfirm(view, url, message, result);
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }
        });


//        transAndroidTojs();

        mJavaToJsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transAndroidTojs("javascript:showAndroidToast()");
            }
        });

        jsCallAndroidUrl();
    }

    @SuppressLint("JavascriptInterface")
    public void transAndroidTojs(String script){
        if (mWebView == null)
            return;
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT){
            mWebView.evaluateJavascript(script, new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {
                    String re = value;
                    Toast.makeText(WebViewActivity.this,"SDK > 19 transAndroidTojs success",Toast.LENGTH_LONG).show();
                }
            });
        }else {
            mWebView.loadUrl(script);
            Toast.makeText(WebViewActivity.this,"SDK < 19 transAndroidTojs success",Toast.LENGTH_LONG).show();

        }
    }

    /**
     * js-->android
     */
    private final class JSInterface {
        @SuppressLint("JavascriptInterface")
        @JavascriptInterface
        public void register(String userInfo) {
            Toast.makeText(WebViewActivity.this, "js-->android", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * js-->android
     */
    public void jsCallAndroidUrl(){
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                // 步骤2：根据协议的参数，判断是否是所需要的url
                // 一般根据scheme（协议格式） & authority（协议名）判断（前两个参数）
                //假定传入进来的 url = "js://webview?arg1=111&arg2=222"（同时也是约定好的需要拦截的）
                String url = "js://webview?arg1=111&arg2=222";
                Uri uri = Uri.parse(url);
                // 如果url的协议 = 预先约定的 js 协议
                // 就解析往下解析参数
                if ( uri.getScheme().equals("js")) {

                    // 如果 authority  = 预先约定协议里的 webview，即代表都符合约定的协议
                    // 所以拦截url,下面JS开始调用Android需要的方法
                    if (uri.getAuthority().equals("webview")) {

                        //  步骤3：
                        // 执行JS所需要调用的逻辑
                        Toast.makeText(WebViewActivity.this,"js调用了Android的方法",Toast.LENGTH_LONG).show();
                        // 可以在协议上带有参数并传递到Android上
                        HashMap<String, String> params = new HashMap<>();
                        Set<String> collection = uri.getQueryParameterNames();

                    }

                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
