package com.imlhx.saolei_android;

import java.io.BufferedInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.DialogInterface;
import com.google.gson.*;
import android.content.Context;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

public class Check_update {
    String newvurl="";
    public boolean check_update(Context context){
        String version_server="https://blog.imlhx.com/android/checkupdate";
        String res="";


        try {
            res= HttpRequest(version_server);
            Gson gson = new Gson();
            res=res.substring(1,res.length()-1);
            res=res.replace("\\", "");
            updatejson rres = gson.fromJson(res, updatejson.class);

            if(rres.getStatus()==1){
                newvurl=rres.getUrl();
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            res=e.toString();
            Toast.makeText(context, res, Toast.LENGTH_LONG).show();
            return false;
        }
    }
    public String HttpRequest(String urlstr) throws Exception {
        String param="?t=d41d8cd98f00b204e9800998ecf8427e&version=1.0.1";
        URL url = new URL(urlstr+param);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        // 开始读取远程服务器的响应数据。
        BufferedInputStream bis = new BufferedInputStream(urlConnection.getInputStream());
        String res="";
        byte[] buffer = new byte[1024 * 10];
        int count = 0;
        while (true) {
            count = bis.read(buffer);
            if (count == -1) {
                break;
            }

            res+=(new String(buffer, 0, count, "UTF-8"));
        }

        bis.close();
        return res;
    }
}
