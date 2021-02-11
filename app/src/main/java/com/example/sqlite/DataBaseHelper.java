package com.example.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.strictmode.SqliteObjectLeakedViolation;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {

    public final static String DATABASE_NAME = "MyStudent_db";
    public final static String TABLE_NAME = "mystudent_table";
    public final static String COL_1 = "Id";
    public final static String COL_2 = "NAME";
    public final static String COL_3 = "EMAIL";
    public final static String COL_4 = "COURSE_COUNT";

    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 01);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "
                + TABLE_NAME +
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NAME TEXT , " +
                "EMAIL TEXT ," +
                " COURSE_COUNT INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String name, String email, String course_count) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_2, name);
        contentValues.put(COL_3, email);
        contentValues.put(COL_4, course_count);

        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }

    }

    public boolean updateData(String id , String name, String email, String course_count){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_1, id);
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, email);
        contentValues.put(COL_4, course_count);

        db.update(TABLE_NAME,contentValues,"ID=?",new String[]{id});
        return true;
    }

    public Cursor getData(String id){

        SQLiteDatabase database = this.getWritableDatabase();
        String query = "SELECT * FROM "+TABLE_NAME+" WHERE ID='"+id+"'";
        Cursor cursor = database.rawQuery(query,null);
        return cursor;

    }

    public  Integer deleteData(String id){

        SQLiteDatabase database = this.getWritableDatabase();
        return database.delete(TABLE_NAME,"ID=?", new String[]{id});


    }

    public Cursor getAllData (){

        SQLiteDatabase database= this.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        return cursor;

    }
}



















