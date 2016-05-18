package com.phillips.jake.formulaschedule.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;

import com.phillips.jake.formulaschedule.ListViewDetails;

import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jake on 6/11/2015.
 */
public class ScheduleDataSource {

    private SQLiteDatabase database;
    private ScheduleSQLiteHelper dbHelper;
    private String[] allColumns = {ScheduleSQLiteHelper.COULUMN_ID, ScheduleSQLiteHelper.COULUMN_COUNTRY,
            ScheduleSQLiteHelper.COULUMN_FP1, ScheduleSQLiteHelper.COULUMN_FP2, ScheduleSQLiteHelper.COULUMN_FP3,
            ScheduleSQLiteHelper.COULUMN_QUALY, ScheduleSQLiteHelper.COULUMN_RACE};

    public ScheduleDataSource(Context context){
        dbHelper = new ScheduleSQLiteHelper(context);
    }

    public void open() throws SQLException{
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public ListViewDetails createDatabaseItem(String country, int fp1, int fp2, int fp3, int qualy, int race){
        ContentValues values = new ContentValues();

        int beginDaylightSave = 1459036800;
        int endDaylightSave = 1477785600;

        values.put(ScheduleSQLiteHelper.COULUMN_COUNTRY, country);
        if(fp1 > beginDaylightSave && fp1 < endDaylightSave){
            values.put(ScheduleSQLiteHelper.COULUMN_FP1, fp1 - 3600);
        }
        else{
            values.put(ScheduleSQLiteHelper.COULUMN_FP1, fp1);
        }

        if(fp2 > beginDaylightSave && fp2 < endDaylightSave){
            values.put(ScheduleSQLiteHelper.COULUMN_FP2, fp2 - 3600);
        }
        else{
            values.put(ScheduleSQLiteHelper.COULUMN_FP2, fp2);
        }

        if(fp3 > beginDaylightSave && fp3 < endDaylightSave){
            values.put(ScheduleSQLiteHelper.COULUMN_FP3, fp3 - 3600);
        }
        else{
            values.put(ScheduleSQLiteHelper.COULUMN_FP3, fp3);
        }

        if(qualy > beginDaylightSave && qualy < endDaylightSave){
            values.put(ScheduleSQLiteHelper.COULUMN_QUALY, qualy - 3600);
        }
        else{
            values.put(ScheduleSQLiteHelper.COULUMN_QUALY, qualy);
        }

        if(race > beginDaylightSave && race < endDaylightSave){
            values.put(ScheduleSQLiteHelper.COULUMN_RACE, race - 3600);
        }
        else{
            values.put(ScheduleSQLiteHelper.COULUMN_RACE, race);
        }

        long insertId = database.insert(ScheduleSQLiteHelper.TABLE_SCHEDULE, null, values);
        Cursor cursor = database.query(ScheduleSQLiteHelper.TABLE_SCHEDULE, allColumns,
                ScheduleSQLiteHelper.COULUMN_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        ListViewDetails details = cursorToDetails(cursor);
        cursor.close();
        return details;
    }

    public ArrayList<ListViewDetails> getAllComments(){
        ArrayList<ListViewDetails> schedule = new ArrayList<ListViewDetails>();

        Cursor cursor = database.query(ScheduleSQLiteHelper.TABLE_SCHEDULE, allColumns, null,
                null, null, null, null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            ListViewDetails details = cursorToDetails(cursor);
            schedule.add(details);
            cursor.moveToNext();
        }

        cursor.close();
        return schedule;
    }



    private ListViewDetails cursorToDetails(Cursor cursor){
        ListViewDetails details = new ListViewDetails(cursor.getString(1), cursor.getInt(2), cursor.getInt(3),
                cursor.getInt(4), cursor.getInt(5), cursor.getInt(6), cursor.getInt(0));
        return details;
    }

}
