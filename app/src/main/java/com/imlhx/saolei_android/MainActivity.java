package com.imlhx.saolei_android;

import android.app.DownloadManager;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.content.Intent;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;
import android.widget.ImageButton;
import android.view.View;
import android.view.View.OnClickListener;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ImageButton lbtn = (ImageButton) findViewById(R.id.buttonl);
        lbtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, game.class);
                Bundle bundle = new Bundle();
                bundle.putInt("h", 9);
                bundle.putInt("l", 9);
                bundle.putInt("lei", 10);
                bundle.putString("title", "扫雷-初级(9*9)");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        ImageButton mbtn=(ImageButton)findViewById(R.id.buttonm);
        mbtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, game.class);
                Bundle bundle = new Bundle();
                bundle.putInt("h", 16);
                bundle.putInt("l", 16);
                bundle.putInt("lei",40);
                bundle.putString("title", "扫雷-中级(16*16)");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        ImageButton hbtn = (ImageButton)findViewById(R.id.buttonh);
        hbtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, game.class);
                Bundle bundle = new Bundle();
                bundle.putInt("h", 21);
                bundle.putInt("l", 16);
                bundle.putInt("lei",90);
                bundle.putString("title", "扫雷-高级(20*16)");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }
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



           LinearLayout diy =(LinearLayout)getLayoutInflater().inflate(R.layout.activity_diy,null);

            AlertDialog.Builder dialog =new AlertDialog.Builder(MainActivity.this);
                    dialog.setTitle("自定义").setMessage("请输入雷数").setView(diy);
            dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    EditText editText = (EditText)diy.findViewById(R.id.diynum);
                    Toast.makeText(MainActivity.this, (CharSequence) editText,Toast.LENGTH_LONG).show();
                    /*
                     String   sss =editText.getText().toString();
                      int lll= Integer.valueOf(sss);
                    if(lll>10 && lll<99){
                        Intent intent = new Intent();
                        intent.setClass(MainActivity.this, game.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("h", 16);
                        bundle.putInt("l", 16);
                        bundle.putInt("lei",lll);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }else {
                        Toast.makeText(MainActivity.this,"请输入一个10-99的数字",Toast.LENGTH_SHORT).show();
                    }*/
                }
            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            dialog.create();
            dialog.show();
           // Handle the camera action
        } else if (id == R.id.game_log) {
            Intent intent2 = new Intent(MainActivity.this,log.class);
            startActivity(intent2);
        } else if (id == R.id.game_about) {
            Intent intent3 = new Intent(MainActivity.this,about.class);
            startActivity(intent3);
        }else if (id == R.id.check_update) {
            AlertDialog.Builder check_update_res = new AlertDialog.Builder(MainActivity.this);
            final Check_update check_update = new Check_update();
            if(check_update.check_update(MainActivity.this)){
                check_update_res.setTitle("检查更新");
                check_update_res.setMessage("检测到新版本,是否更新?\n"+check_update.newvurl);
                check_update_res.setPositiveButton("确定", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface d, int arg1) {
                        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(check_update.newvurl));
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
                        request.setTitle("扫雷");
                        request.setDescription("正在下载新版本");
                        DownloadManager downManager = (DownloadManager)getSystemService(about.DOWNLOAD_SERVICE);
                        request.setDestinationInExternalFilesDir(MainActivity.this, Environment.DIRECTORY_DOWNLOADS, "");

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

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
