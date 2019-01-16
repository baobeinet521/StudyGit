package com.gitstudy.download;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DownLoadManagerSingleton2 {
    private static volatile DownLoadManagerSingleton2 mSingleton;
    private Context mContext;
    private DownLoadManagerSingleton2(){

    }
    public static DownLoadManagerSingleton2 getSingleton(){
        if(mSingleton == null){
            synchronized (DownLoadManagerSingleton2.class){
                if(mSingleton == null){
                    mSingleton = new DownLoadManagerSingleton2();
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


        DownLoadBroadCastReceiver downLoadBroadCastReceiver = new DownLoadBroadCastReceiver(downLoadId);
        IntentFilter mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        context.registerReceiver(downLoadBroadCastReceiver,mIntentFilter);
        mRecevierList.add(downLoadBroadCastReceiver);
    }

    private List<DownLoadBroadCastReceiver> mRecevierList = new ArrayList<>();

    public void unregisterReceiver(Context context, long id) {
        for (int i = 0; i < mRecevierList.size(); i++) {
            if (mRecevierList.get(i).id == id) {
                context.unregisterReceiver(mRecevierList.get(i));
            }
        }
    }

    public class DownLoadBroadCastReceiver extends BroadcastReceiver {
        private long id;

        public DownLoadBroadCastReceiver(long id){
            this.id = id;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            intent.getAction();
            long myDownLoadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID,-1);
            if (myDownLoadId == id) {
                String serviceString = context.DOWNLOAD_SERVICE;
                DownloadManager dManager = (DownloadManager) context.getSystemService(serviceString);
                Intent install = new Intent(Intent.ACTION_VIEW);
                Uri downLoadManagerFileUri = dManager.getUriForDownloadedFile(myDownLoadId);
                install.setDataAndType(downLoadManagerFileUri, "application/vnd.android.package-archive");
                install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(install);
                unregisterReceiver(context, id);
            }
        }
    }
}
