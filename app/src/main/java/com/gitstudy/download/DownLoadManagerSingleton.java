package com.gitstudy.download;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DownLoadManagerSingleton{
    private static volatile DownLoadManagerSingleton mSingleton;
    private Context mContext;
    private DownLoadManagerSingleton(){

    }
    public static DownLoadManagerSingleton getSingleton(){
        if(mSingleton == null){
            synchronized (DownLoadManagerSingleton.class){
                if(mSingleton == null){
                    mSingleton = new DownLoadManagerSingleton();
                }
            }
        }
        return mSingleton;
    }

    public void downLoadPackage(Context context,String appName){
        if(context == null){
            return;
        }
        this.mContext = context;

//        mDownLoadBroadCastReceiver.setmRecevier(mDownLoadBroadCastReceiver);
        Log.i("downLoad","downLoadPackage");
        DownloadManager loadManager = (DownloadManager) context.getSystemService(context.DOWNLOAD_SERVICE);
//        Uri uri = Uri.parse("http://192.168.1.103/app-release.apk");
        Uri uri = Uri.parse("http://192.168.1.103/"+appName+".apk");
        DownloadManager.Request request = new DownloadManager.Request(uri);
//        request.setDestinationInExternalPublicDir("download","app-release.apk");
        request.setDestinationInExternalPublicDir("download",appName+".apk");
        request.setTitle("下载studyProject");
        request.setDescription("下载studyProject薪版本");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setMimeType("application/vnd.android.package-archive");
        // 设置为可被媒体扫描器找到
        request.allowScanningByMediaScanner();
        // 设置为可见和可管理
        request.setVisibleInDownloadsUi(true);
        long downLoadId = loadManager.enqueue(request);


        DownLoadBroadCastReceiver mDownLoadBroadCastReceiver = new DownLoadBroadCastReceiver();
        IntentFilter mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        context.registerReceiver(mDownLoadBroadCastReceiver,mIntentFilter);
        mRecevierMap.put(String.valueOf(downLoadId),mDownLoadBroadCastReceiver);
    }

    private Map<String,DownLoadBroadCastReceiver> mRecevierMap = new HashMap<>();

    public class DownLoadBroadCastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            intent.getAction();
            long myDownLoadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID,-1);
            if(mRecevierMap != null){
                List<String> keyLsit = new ArrayList<>();
                Iterator<String> iter = mRecevierMap.keySet().iterator();
                while(iter.hasNext()) {
                    String keyStr = iter.next();
                    keyLsit.add(keyStr);
                }
                for(int i = 0;i<keyLsit.size();i++){
                    if(Long.parseLong(keyLsit.get(i)) == myDownLoadId){
                        String serviceString = context.DOWNLOAD_SERVICE;
                        DownloadManager dManager = (DownloadManager) context.getSystemService(serviceString);
                        Intent install = new Intent(Intent.ACTION_VIEW);
                        Uri downLoadManagerFileUri = dManager.getUriForDownloadedFile(myDownLoadId);
                        install.setDataAndType(downLoadManagerFileUri, "application/vnd.android.package-archive");
                        install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(install);
                        if(mRecevierMap.get(keyLsit.get(i)) != null){
                            context.unregisterReceiver(mRecevierMap.get(keyLsit.get(i)));
                        }
                    }
                }
            }
        }
    }
}
