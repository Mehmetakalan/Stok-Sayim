package com.example.pethoalpar.zxingexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by landforce on 09.08.2014.
 */
public class Database extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "sqllite_database";//database adý

    private static final String TABLE_NAME = "barkodlar_listesi";
    private static String BARKOD_NO="barkod_no";
    private static String ADET="adet";


    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {  // Databesi oluþturuyoruz.Bu methodu biz çaðýrmýyoruz. Databese de obje oluþturduðumuzda otamatik çaðýrýlýyor.
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + BARKOD_NO + " TEXT,"
                + ADET + " TEXT," + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub
        arg0.execSQL("DROP TABLE IF EXIST " + TABLE_NAME);
    }
}