package com.imlhx.saolei_android;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.sax.RootElement;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class log extends Activity {
    public void  onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    }
}
