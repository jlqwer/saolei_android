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

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import mehdi.sakout.aboutpage.*;




public class about extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
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


*/
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.game_diy) {
            Intent intent = new Intent();
            intent.setClass(about.this, game.class);
            Bundle bundle = new Bundle();
            bundle.putInt("h", 16);
            bundle.putInt("l", 16);
            bundle.putInt("lei",30);
            intent.putExtras(bundle);
            startActivity(intent);
            Toast.makeText(getApplicationContext(), "菜单：自定义 default:30", Toast.LENGTH_SHORT).show();
            // Handle the camera action
        } else if (id == R.id.game_log) {
            Intent intent2 = new Intent(about.this,log.class);
            startActivity(intent2);
        } else if (id == R.id.game_about) {
            Intent intent3 = new Intent(about.this,about.class);
            startActivity(intent3);
        }else if (id == R.id.check_update) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
