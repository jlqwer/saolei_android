package com.imlhx.saolei_android;

import android.widget.Button;
import android.os.Bundle;
import android.content.Intent;
import android.widget.ImageButton;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.game_diy) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, game.class);
            Bundle bundle = new Bundle();
            bundle.putInt("h", 16);
            bundle.putInt("l", 16);
            bundle.putInt("lei",30);
            intent.putExtras(bundle);
            startActivity(intent);
            Toast.makeText(getApplicationContext(), "菜单：自定义 default:30", Toast.LENGTH_SHORT).show();
            // Handle the camera action
        } else if (id == R.id.game_log) {
            Intent intent2 = new Intent(MainActivity.this,log.class);
            startActivity(intent2);
        } else if (id == R.id.game_about) {
            Intent intent3 = new Intent(MainActivity.this,about.class);
            startActivity(intent3);
        }else if (id == R.id.check_update) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
