package com.gitstudy.download;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;

public class DownLoadBroadCastReceiver extends BroadcastReceiver{

    private DownLoadBroadCastReceiver mRecevier;

    public void setmRecevier(DownLoadBroadCastReceiver mRecevier) {
        this.mRecevier = mRecevier;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        intent.getAction();
        long myDownLoadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID,-1);
        SharedPreferences sPrefereences = context.getSharedPreferences("downloadplato",0);
        long downLoadId = sPrefereences.getLong("plato", 0);
        if(downLoadId == myDownLoadId){
            String serviceString = context.DOWNLOAD_SERVICE;
            DownloadManager dManager = (DownloadManager) context.getSystemService(serviceString);
            Intent install = new Intent(Intent.ACTION_VIEW);
            Uri downLoadManagerFileUri = dManager.getUriForDownloadedFile(myDownLoadId);
            install.setDataAndType(downLoadManagerFileUri, "application/vnd.android.package-archive");
            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(install);
            if(mRecevier != null){
                context.unregisterReceiver(mRecevier);
            }

        }

    }
}
