package com.imlhx.saolei_android;

import java.io.BufferedInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.Context;
import android.content.DialogInterface;
import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import mehdi.sakout.aboutpage.*;


import com.google.gson.*;

public class about extends AppCompatActivity{
    String newvurl="";
    private RelativeLayout relativeLayout;
    private Toolbar toolbar;
    public void  onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        initViews();
        Element  qq=new Element();
        qq.setTitle("联系作者");
        qq.setIconDrawable(R.drawable.icon_qq);
        qq.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                if(isQQClientAvailable(about.this)){
                    final String qqUrl = "mqqwpa://im/chat?chat_type=wpa&uin=1062821199&version=1";
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(qqUrl)));
                }else{
                    Toast.makeText(about.this,"请安装QQ客户端",Toast.LENGTH_SHORT).show();
                }
            }
        });
        Element  version=new Element();
        version.setTitle("Version 1.0");
        version.setIconDrawable(R.drawable.icon_version);
        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setImage(R.drawable.lei_60)//图片
                .setDescription("扫雷 ---- 只是一个 Android 课设")//介绍
                .addItem(version)
                .addItem(qq)
                .addWebsite("https://www.imlhx.com")
                .addGitHub("jlqwer")//github
                .create();

        relativeLayout.addView(aboutPage);


        /*
        //about.this.setTitle("关于");
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        TextView about_ver_update = (TextView)findViewById(R.id.about_update);
        TextView about_author = (TextView)findViewById(R.id.about_author);
        about_author.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                openurl("https://www.imlhx.com/");
            }
        });
        about_ver_update.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Builder check_update_res = new AlertDialog.Builder(about.this);
                if(check_update()){
                    check_update_res.setTitle("检查更新");
                    check_update_res.setMessage("检测到新版本,是否更新?"+newvurl);
                    check_update_res.setPositiveButton("确定", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface d, int arg1) {
                            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(newvurl));
                            request.setNotificationVisibility(Request.VISIBILITY_VISIBLE);
                            request.setTitle("扫雷");
                            request.setDescription("正在下载新版本");
                            DownloadManager downManager = (DownloadManager)getSystemService(about.DOWNLOAD_SERVICE);
                            request.setDestinationInExternalFilesDir(about.this, Environment.DIRECTORY_DOWNLOADS, "");

                            long id = downManager.enqueue(request);
                            d.cancel();

                        }
                    });
                    check_update_res.setNegativeButton("取消", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface d, int arg1) {
                            // TODO Auto-generated method stub
                            d.cancel();
                        }
                    });
                    check_update_res.create();
                    check_update_res.show();

                }else{
                    check_update_res.setTitle("检查更新");
                    check_update_res.setMessage("当前版本已是最新！");
                    check_update_res.setPositiveButton("确定", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(final DialogInterface d, final int arg1) {
                            // TODO Auto-generated method stub

                            d.cancel();
                        }
                    });
                    check_update_res.create();
                    check_update_res.show();
                }
            }
        });
        */
    }
    public static boolean isQQClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            default:

        }
        return true;
    }
    private void initViews(){
        relativeLayout= (RelativeLayout) findViewById(R.id.relativeLayout);
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
    /*
    public void openurl(String url){
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
    public boolean check_update(){
        String version_server="http://blog.imlhx.com/android/checkupdate";
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
            Toast.makeText(getApplicationContext(), res, Toast.LENGTH_LONG).show();
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
*/
}
