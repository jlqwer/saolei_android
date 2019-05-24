package com.imlhx.saolei_android;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class lxg_db extends SQLiteOpenHelper {

    static final String Database_name="lxg.db";
    static final int Database_Version=1;
    SQLiteDatabase db;
    public int id_this;
    Cursor cursor;

    static String TABLE_NAME = "game_time";
    static String ID = "_id";
    private static String RANK="rank";
    static String WHEN = "game_when";
    static String USE_TIME = "use_time";

    lxg_db(Context context) {
        super(context, Database_name, null, Database_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        // TODO Auto-generated method stub
        String sql="CREATE TABLE "+TABLE_NAME+"("
                +ID+" INTEGER primary key autoincrement,"
                +getRANK()+" text not null,"
                +WHEN+" text not null,"
                +USE_TIME+" text not null,"
                +"res"+" text not null);";
        DB.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub

    }

    public static String getRANK() {
        return RANK;
    }

    public static void setRANK(String rANK) {
        RANK = rANK;
    }

}
