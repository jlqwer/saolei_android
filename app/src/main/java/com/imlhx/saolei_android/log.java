package com.imlhx.saolei_android;

import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import android.os.Environment;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class log extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public void  onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_log);
        //TextView textView = (TextView) findViewById(R.id.log);
        lxg_db helper = new lxg_db(log.this);
        SQLiteDatabase dbr = helper.getReadableDatabase();
        //LinearLayout log = (LinearLayout) getLayoutInflater().inflate(R.layout.activity_log, null);
        TableLayout tableLayout = (TableLayout)findViewById(R.id.log_table);
        Cursor cursor=dbr.rawQuery("Select * from "+helper.TABLE_NAME, null);
        try{
            if(cursor!=null&&cursor.moveToFirst()){
                TableRow row;
                TextView text_1 = null;
                TextView text_2 = null;
                TextView text_3 = null;
                TextView text_4 = null;
                TextView text_5 = null;
                ViewGroup p;
                int bc = Color.TRANSPARENT;
                do{
                    String id=Integer.toString(cursor.getInt(cursor.getColumnIndex(helper.ID)));
                    String rank=cursor.getString(cursor.getColumnIndex(helper.getRANK()));
                    String when_time =cursor.getString(cursor.getColumnIndex(helper.WHEN));
                    String use_time=cursor.getString(cursor.getColumnIndex(helper.USE_TIME));
                    String res=cursor.getString(cursor.getColumnIndex("res"));
                    row = new TableRow(getApplicationContext());
                    //id
                    text_1 = new TextView(getApplicationContext());
                    text_1.setText("\t"+id);
                    text_1.setTextColor(Color.BLUE);
                    text_1.setBackgroundColor(bc);
                    text_1.setTextSize(20);
                    text_1.setGravity(Gravity.RIGHT);
                    p = (ViewGroup) text_1.getParent();
                    if (p != null) {
                        p.removeAllViewsInLayout();
                    }
                    row.addView(text_1);
                    //
                    text_2 = new TextView(getApplicationContext());
                    text_2.setText("\t"+rank);
                    text_2.setTextColor(Color.BLACK);
                    text_2.setBackgroundColor(bc);
                    text_2.setTextSize(20);
                    text_2.setGravity(Gravity.CENTER);
                    p = (ViewGroup) text_2.getParent();
                    if (p != null) {
                        p.removeAllViewsInLayout();
                    }
                    row.addView(text_2);
                    //
                    text_3 = new TextView(getApplicationContext());
                    text_3.setText("\t"+when_time);
                    text_3.setTextColor(Color.BLACK);
                    text_3.setBackgroundColor(bc);
                    text_3.setTextSize(20);
                    text_3.setGravity(Gravity.CENTER);
                    p = (ViewGroup) text_3.getParent();
                    if (p != null) {
                        p.removeAllViewsInLayout();
                    }
                    row.addView(text_3);
                    //
                    text_4 = new TextView(getApplicationContext());
                    text_4.setText("\t"+use_time);
                    text_4.setTextColor(Color.BLACK);
                    text_4.setBackgroundColor(bc);
                    text_4.setTextSize(20);
                    text_4.setGravity(Gravity.CENTER);
                    p = (ViewGroup) text_4.getParent();
                    if (p != null) {
                        p.removeAllViewsInLayout();
                    }
                    row.addView(text_4);
                    //
                    text_5 = new TextView(getApplicationContext());
                    text_5.setText("\t"+res);
                    text_5.setTextColor(Color.BLACK);
                    text_5.setBackgroundColor(bc);
                    text_5.setTextSize(20);
                    text_5.setGravity(Gravity.CENTER);
                    p = (ViewGroup) text_5.getParent();
                    if (p != null) {
                        p.removeAllViewsInLayout();
                    }
                    row.addView(text_5);

                    tableLayout.addView(row);
                }while(cursor.moveToNext());

            }
        }catch (Exception e) {
            // TODO: handle exception
            TextView temp = (TextView)findViewById(R.id.textView1);
            temp.setText(e.toString());
        }

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
            Intent intent = new Intent();
            intent.setClass(log.this, game.class);
            Bundle bundle = new Bundle();
            bundle.putInt("h", 16);
            bundle.putInt("l", 16);
            bundle.putInt("lei",30);
            intent.putExtras(bundle);
            startActivity(intent);
            Toast.makeText(getApplicationContext(), "菜单：自定义 default:30", Toast.LENGTH_SHORT).show();
            // Handle the camera action
        } else if (id == R.id.game_log) {
            Toast.makeText(log.this,"当前已在游戏记录中",Toast.LENGTH_SHORT);
        } else if (id == R.id.game_about) {
            Intent intent3 = new Intent(log.this,about.class);
            startActivity(intent3);
        }else if (id == R.id.check_update) {
            AlertDialog.Builder check_update_res = new AlertDialog.Builder(log.this);
            final Check_update check_update = new Check_update();
            if(check_update.check_update(log.this)){
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
                        request.setDestinationInExternalFilesDir(log.this, Environment.DIRECTORY_DOWNLOADS, "");

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
