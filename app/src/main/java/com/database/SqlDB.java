package com.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.model.Stud;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by krishan on 12/7/17.
 */

public class SqlDB extends SQLiteOpenHelper {


    private static final String DB_NAME = "DB";
    private static final int DB_VERSION = 1;
    private static SqlDB sInstanse;
    public SqlDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static synchronized SqlDB getInstanse(Context context) {
        if (sInstanse == null) {
            sInstanse = new SqlDB(context);
        }
        return sInstanse;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table stus" + "(id integer primary key, name text, roll text, age text, row integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP table if exists stus");
            onCreate(db);
        }
    }

    public void insertStus(String names, String rolls, String ages, String row) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues v = new ContentValues();
        v.put("name", names);
        v.put("roll", rolls);
        v.put("age", ages);
        v.put("age", ages);
        v.put("row", row);
        db.insertOrThrow("stus", null, v);
    }

    public List<Stud> getAllStudInfo(int pos) {
        List<Stud> mList = new ArrayList<>();
        String selectdata = null;
        switch (pos) {
            case 0:
                selectdata = "Select * from stus";
                break;
            case 1:
                selectdata = "Select * from stus order by row ASC";
                break;
            case 2:
                selectdata = "Select * from stus order by row DESC";
                break;

        }
        Cursor c = this.getReadableDatabase().rawQuery(selectdata, null);
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            Stud s = new Stud();
            s.setName(c.getString(1));
            s.setRoll(c.getString(2));
            s.setAge(c.getString(3));
            s.setRow(c.getString(4));

            mList.add(s);
            c.moveToNext();
        }
        return mList;
    }

    public List<Stud> getRangeIngo(String start, String end) {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Stud> rangeList = new ArrayList<>();
        String selectRange = "Select * from stus where id BETWEEN '" + start + "' AND '" + end + "' ";
        Cursor c = db.rawQuery(selectRange, null);
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            Stud s = new Stud();
            s.setName(c.getString(1));
            s.setRoll(c.getString(2));
            s.setAge(c.getString(3));
            s.setRow(c.getString(4));
            rangeList.add(s);
            c.moveToNext();
        }

        return rangeList;
    }

    public List<Stud> getSameName(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Stud> s = new ArrayList<>();

//        String findName = "Select * from stus where name = '"+name+"' order by row DESC";
        String findName = "Select * from stus where name = '" + name + "'";
        Cursor c = db.rawQuery(findName, null);
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            Stud stud = new Stud(c.getString(1), c.getString(2), c.getString(3), c.getString(4));
            s.add(stud);
            c.moveToNext();
        }
        return s;
    }

    public void deleteRow(String pos) {
//        this.getWritableDatabase().delete("stus", "row=?", new String[]{pos});
        this.getWritableDatabase().delete("stus", "row='"+pos+"'", null);
    }

}
