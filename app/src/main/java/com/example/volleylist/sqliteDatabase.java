package com.example.volleylist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class sqliteDatabase extends SQLiteOpenHelper {

    public static final String databaseName = "employee_db";
    public static final String tableName = "employee_tbl";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "USER_ID";
    public static final String COL_3 = "TITLE";
    public static final String COL_4 = "BODY";
    public static final String COL_5 = "DATE";

    public sqliteDatabase(@Nullable Context context) {
        super(context, databaseName, null, 2);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + tableName +"(ID INTEGER PRIMARY KEY AUTOINCREMENT,USER_ID INTEGER,TITLE TEXT,BODY TEXT,DATE TEXT) "  );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS "+ tableName);
        onCreate(db);

    }

    public long insertData(String title , String body , String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
//        contentValues.put(COL_2 , user_id);
        contentValues.put(COL_3 , title);
        contentValues.put(COL_4 , body);
        contentValues.put(COL_5 , date);
        long result = db.insert(tableName,null,contentValues);
        return result;
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + tableName , null);
        return data;

    }

    public void deleteData(){
        SQLiteDatabase db = this.getWritableDatabase();
         db.delete(tableName,null,null);
    }
    public boolean updateData(String id , String title , String body , String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_3 , title);
        contentValues.put(COL_4 , body);
        contentValues.put(COL_5 , date);
        db.update(tableName,contentValues,"ID = ?",new String[] {id});
        return true;
    }

    public Integer deleteRows(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(tableName,"ID = ?",new String[]{id});
    }
}
