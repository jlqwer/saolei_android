package com.imlhx.saolei_android;

import android.app.DownloadManager;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.os.Environment;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.xml.datatype.DatatypeConstants.Field;



public class game extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    static int[][] lmap;
    static int[][] vist;
    static int h;
    static int l;
    static int lei;
    static int setf;
    static int fnum;
    static int curl;
    static int ff;
    Chronometer chronometer;
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    lxg_db helper;
    Cursor cursor;
    SQLiteDatabase db;
    public int id_this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.game_9_9);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Bundle bundle = this.getIntent().getExtras();
        setTitle(bundle.getString("title"));
        h=bundle.getInt("h");
        l=bundle.getInt("l");
        lei=bundle.getInt("lei");
        CheckBox sf;
        if(h==9){
            setContentView(R.layout.game_9_9);
            sf=(CheckBox)findViewById(R.id.sf_l);
        }else if(h==16){
            setContentView(R.layout.game_16_16);
            sf=(CheckBox)findViewById(R.id.sf_m);
        }else{
            setContentView(R.layout.game_16_20);
            sf=(CheckBox)findViewById(R.id.sf_h);
        }
        setmap(h,l,lei);
        chronometer = (Chronometer) findViewById(R.id.t0);
        chronometer.start();
        sf.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean c) {
                // TODO Auto-generated method stub
                if(c){
                    setf=1;
                }else{
                    setf=0;
                }
            }
        });
        helper=new lxg_db(game.this);
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
    public int nametoid(int a,int b){
        String idstr="";
        if(h==9){
            idstr= "l_"+Integer.toString(a)+"_"+Integer.toString(b);
        }else if(h==16){
            idstr= "m_"+Integer.toString(a)+"_"+Integer.toString(b);
        }else{
            idstr= "h_"+Integer.toString(a)+"_"+Integer.toString(b);
        }
        int RId = getResources().getIdentifier(idstr, "id",getPackageName());
        return RId;
    }

    public void kb(int a,int b){
        if(lmap[a][b]!=0 || vist[a][b]!=0 ){
            if(lmap[a][b]!=-1 && vist[a][b]==0 ){
                int RId = nametoid(a, b);
                ImageButton v =(ImageButton) findViewById(RId);
                lclick(v);
            }
            return;
        }
        int RId = nametoid(a, b);
        ImageButton v =(ImageButton) findViewById(RId);
        if(l==9){
            ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.n0_35));
        }else
        {
            ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.n0_20));
        }
        vist[a][b]=1;
        ff++;
        int[] gox={-1,-1,0,1,1,1,0,-1};
        int[] goy={0,1,1,1,0,-1,-1,-1};
        for(int k=0;k<8;k++){
            int aa=a+gox[k];
            int bb=b+goy[k];
            if(aa>=0&&aa<h&&bb>=0&&bb<l){
                kb(aa,bb);
                //Toast.makeText(game.this,Integer.toString(aa)+" "+Integer.toString(bb), Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void lclick(View v){
        String vstr=(String)((ImageButton)v).getTag();
        String[] hl = vstr.split("_");
        //Toast.makeText(game.this,hl[0], Toast.LENGTH_SHORT).show();
        int hh = Integer.valueOf(hl[0]);
        int ll = Integer.valueOf(hl[1]);

        if(setf==1){
            if(vist[hh][ll]==2){
                vist[hh][ll]=0;
                fnum--;
                if(lmap[hh][ll]==-1){
                    curl++;
                }
                if(l==9){
                    ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.l_button_35));
                }else{
                    ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.l_button_20));
                }
            }else if(vist[hh][ll]==0){
                if(fnum>=lei && curl!=0){
                    Toast.makeText(game.this,"旗子数量已用完", Toast.LENGTH_SHORT).show();
                }else{
                    if(lmap[hh][ll]==-1){
                        curl--;
                    }
                    vist[hh][ll]=2;
                    fnum++;
                    if(l==9){
                        ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.f_35));
                    }else{
                        ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.f_20));
                    }

                }
                if(curl==lei){
                    win();
                }
            }
            wlie_num();
        }
        else if(vist[hh][ll]==0){
            switch (lmap[hh][ll]) {
                case 0:
                    if(l==9){
                        ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.n0_35));
                    }else
                    {
                        ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.n0_20));
                    }
                    kb(hh,ll);
                    ff--;
                    break;
                case 1:
                    if(l==9) {
                        ((ImageButton) v).setImageDrawable(getResources().getDrawable(R.drawable.n1_35));
                    }else{
                        ((ImageButton) v).setImageDrawable(getResources().getDrawable(R.drawable.n1_20));
                    }
                    break;
                case 2:
                    if(l==9) {
                        ((ImageButton) v).setImageDrawable(getResources().getDrawable(R.drawable.n2_35));
                    }else{
                        ((ImageButton) v).setImageDrawable(getResources().getDrawable(R.drawable.n2_20));
                    }
                    break;
                case 3:
                    if(l==9) {
                        ((ImageButton) v).setImageDrawable(getResources().getDrawable(R.drawable.n3_35));
                    }else{
                        ((ImageButton) v).setImageDrawable(getResources().getDrawable(R.drawable.n3_20));
                    }
                    break;
                case 4:
                    if(l==9) {
                        ((ImageButton) v).setImageDrawable(getResources().getDrawable(R.drawable.n4_35));
                    }else{
                        ((ImageButton) v).setImageDrawable(getResources().getDrawable(R.drawable.n4_20));
                    }
                    break;
                case 5:
                    if(l==9) {
                        ((ImageButton) v).setImageDrawable(getResources().getDrawable(R.drawable.n5_35));
                    }else{
                        ((ImageButton) v).setImageDrawable(getResources().getDrawable(R.drawable.n5_20));
                    }
                    break;
                case 6:
                    if(l==9) {
                        ((ImageButton) v).setImageDrawable(getResources().getDrawable(R.drawable.n6_35));
                    }else{
                        ((ImageButton) v).setImageDrawable(getResources().getDrawable(R.drawable.n6_20));
                    }
                    break;
                case 7:
                    if(l==9) {
                        ((ImageButton) v).setImageDrawable(getResources().getDrawable(R.drawable.n7_35));
                    }else{
                        ((ImageButton) v).setImageDrawable(getResources().getDrawable(R.drawable.n7_20));
                    }
                    break;
                case 8:
                    if(l==9) {
                        ((ImageButton) v).setImageDrawable(getResources().getDrawable(R.drawable.n8_35));
                    }else{
                        ((ImageButton) v).setImageDrawable(getResources().getDrawable(R.drawable.n8_20));
                    }
                    break;
                case 9:
                    if(l==9) {
                        ((ImageButton) v).setImageDrawable(getResources().getDrawable(R.drawable.n9_35));
                    }else{
                        ((ImageButton) v).setImageDrawable(getResources().getDrawable(R.drawable.n9_20));
                    }
                    break;
                case -1:
                    if(l==9){
                        ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.lei_35));
                    }else
                    {
                        ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.lei_20));
                    }
                    ff--;
                    Toast.makeText(game.this, "游戏结束", Toast.LENGTH_SHORT).show();
                    chronometer.stop();
                    save_log(df.format(new Date()),chronometer.getText().toString(),"失败");
                    gameover();
                    break;
                default:
                    Toast.makeText(game.this, "error", Toast.LENGTH_SHORT).show();
                    break;
            }
            vist[hh][ll]=1;
            ff++;
            if(ff==(l*h)-lei ){
                win();
            }
        }


    }
    public void win(){

        Toast.makeText(game.this, "胜利", Toast.LENGTH_SHORT).show();
        chronometer.stop();
        gameover();
        save_log(df.format(new Date()),chronometer.getText().toString(),"胜利");

    }
    public void wlie_num(){
        TextView lei_num= (TextView)findViewById(R.id.lei_num);
        lei_num.setText(Integer.toString(lei-fnum));
    }

    public void save_log(String m,String user_time,String res){
        ContentValues values = new ContentValues();
        String CUR_RANK="";
        if(h==9){
            CUR_RANK="低级";
        }else if(h==16){
            CUR_RANK="中级";
        }else{
            CUR_RANK="高级";
        }
        values.put(helper.getRANK(), CUR_RANK);
        values.put(helper.WHEN, m);
        values.put(helper.USE_TIME, user_time);
        values.put("res", res);
        SQLiteDatabase dbw=helper.getWritableDatabase();
        dbw.insert(helper.TABLE_NAME, null, values);
        dbw.close();
    }

    public void gameover(){
        for(int i=0;i<h;i++){
            for(int j=0;j<l;j++){
                if(vist[i][j]==0 || vist[i][j]==2){
                    vist[i][j]=1;
                    if(lmap[i][j]==-1){
                        int RId = nametoid(i, j);
                        ImageButton v =(ImageButton) findViewById(RId);
                        //Toast.makeText(getApplicationContext(),v.toString(), Toast.LENGTH_LONG);
                        if(l==9){
                            ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.lei_35));
                        }else
                        {
                            ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.lei_20));
                        }

                    }
                }
            }
        }
    }
    public int getl(int m,int n){
        int cl=0;
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                cl+=lmap[i][j];
            }
        }
        return -cl;
    }
    public void setmap(int m,int n,int lei){
        lmap= new int[m][n];
        vist= new int[m][n];
        setf=0;
        fnum=0;
        curl=0;
        ff=0;
        int[] gox={-1,-1,0,1,1,1,0,-1};
        int[] goy={0,1,1,1,0,-1,-1,-1};
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                lmap[i][j]=0;
                vist[i][j]=0;
            }
        }
        Random random = new Random();
        for(int i=0;i<lei;i++){
            lmap[random.nextInt(m)][random.nextInt(n)]=-1;
        }
        while(getl(m,n)<lei){
            lmap[random.nextInt(m)][random.nextInt(n)]=-1;
        }
        int ii=0;
        int jj=0;
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(lmap[i][j]==-1){
                    for(int k=0;k<8;k++){
                        ii=i+gox[k];
                        jj=j+goy[k];
                        if(ii>=0&&ii<m&&jj>=0&&jj<n){
                            if(lmap[ii][jj]!=-1){
                                lmap[ii][jj]++;
                            }
                        }
                    }
                }
            }
        }
        wlie_num();
        android.widget.Toast.makeText(game.this, "初始化完成", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getApplicationContext(), "请从主页进入自定义模式！", Toast.LENGTH_SHORT).show();
            // Handle the camera action
        } else if (id == R.id.game_log) {
            Intent intent2 = new Intent(game.this,log.class);
            startActivity(intent2);
        } else if (id == R.id.game_about) {
            Intent intent3 = new Intent(game.this,about.class);
            startActivity(intent3);
        }else if (id == R.id.check_update) {
            AlertDialog.Builder check_update_res = new AlertDialog.Builder(game.this);
            final Check_update check_update = new Check_update();
            if(check_update.check_update(game.this)){
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
                        request.setDestinationInExternalFilesDir(game.this, Environment.DIRECTORY_DOWNLOADS, "");

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
