package com.phillips.jake.formulaschedule.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.phillips.jake.formulaschedule.Data.ScheduleContract.ScheduleEntry;

/**
 * Created by Jake on 6/11/2015.
 */
public class ScheduleSQLiteHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "schedule.db";
    public static final int DATABASE_VERSION = 2;

    public static final String DATABASE_CREATE = "create table "
            + ScheduleEntry.TABLE_NAME + "("
            + ScheduleEntry._ID + " integer primary key autoincrement, "
            + ScheduleEntry.COLUMN_COUNTRY + " text not null, "
            + ScheduleEntry.COLUMN_FP1 + " integer, "
            + ScheduleEntry.COLUMN_FP2 + " integer, "
            + ScheduleEntry.COLUMN_FP3 + " integer, "
            + ScheduleEntry.COLUMN_QUALY + " integer, "
            + ScheduleEntry.COLUMN_RACE + " integer);";

    public ScheduleSQLiteHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase database){
        database.execSQL(DATABASE_CREATE);
    }

    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion){
        database.execSQL("DROP TABLE IF EXISTS" + ScheduleEntry.TABLE_NAME);
        onCreate(database);
    }
}
