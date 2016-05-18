package com.phillips.jake.formulaschedule.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jake on 6/11/2015.
 */
public class ScheduleSQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_SCHEDULE = "schedule";

    public static final String COULUMN_ID = "_id";
    public static final String COULUMN_COUNTRY = "country";
    public static final String COULUMN_FP1 = "fp1";
    public static final String COULUMN_FP2 = "fp2";
    public static final String COULUMN_FP3 = "fp3";
    public static final String COULUMN_QUALY = "qualy";
    public static final String COULUMN_RACE = "race";

    public static final String DATABASE_NAME = "schedule.db";
    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_CREATE = "create table "
            + TABLE_SCHEDULE + "("
            + COULUMN_ID + " integer primary key autoincrement, "
            + COULUMN_COUNTRY + " text not null, "
            + COULUMN_FP1 + " integer, "
            + COULUMN_FP2 + " integer, "
            + COULUMN_FP3 + " integer, "
            + COULUMN_QUALY + " integer, "
            + COULUMN_RACE + " integer);";

    public ScheduleSQLiteHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase database){
        database.execSQL(DATABASE_CREATE);
    }

    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion){
        database.execSQL("DROP TABLE IF EXISTS" + TABLE_SCHEDULE);
        onCreate(database);
    }
}
