package com.phillips.jake.formulaschedule;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import com.phillips.jake.formulaschedule.Data.ScheduleContract;
import com.phillips.jake.formulaschedule.Data.ScheduleSQLiteHelper;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by jphil on 6/23/2016.
 */
public class TestDB extends AndroidTestCase {
    public static final String LOG_TAG = TestDB.class.getSimpleName();

    void deleteTheDatabase(){
        mContext.deleteDatabase(ScheduleSQLiteHelper.DATABASE_NAME);
    }

    public void setUp(){
        deleteTheDatabase();
    }

    public void testCreateDb(){
        final HashSet<String> tableNames = new HashSet<String>();
        tableNames.add(ScheduleContract.ScheduleEntry.TABLE_NAME);

        mContext.deleteDatabase(ScheduleSQLiteHelper.DATABASE_NAME);
        SQLiteDatabase db = new ScheduleSQLiteHelper(this.mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());

        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
        assertTrue("Error: This means that the database has not been created correctly", c.moveToFirst());

        do{
            tableNames.remove(c.getString(0));
        }while(c.moveToNext());

        assertTrue("Error: your database was created without schedule table", tableNames.isEmpty());

        c = db.rawQuery("PRAGMA table_info(" + ScheduleContract.ScheduleEntry.TABLE_NAME + ")", null);

        assertTrue("Error: unable to query the database for table info", c.moveToFirst());

        final HashSet<String> columnSet = new HashSet<String>();
        columnSet.add(ScheduleContract.ScheduleEntry._ID);
        columnSet.add(ScheduleContract.ScheduleEntry.COLUMN_COUNTRY);
        columnSet.add(ScheduleContract.ScheduleEntry.COLUMN_FP1);
        columnSet.add(ScheduleContract.ScheduleEntry.COLUMN_FP2);
        columnSet.add(ScheduleContract.ScheduleEntry.COLUMN_FP3);
        columnSet.add(ScheduleContract.ScheduleEntry.COLUMN_QUALY);
        columnSet.add(ScheduleContract.ScheduleEntry.COLUMN_RACE);

        int columnNameIndex = c.getColumnIndex("name");
        do{
            String columnName = c.getString(columnNameIndex);
            columnSet.remove(columnName);
        }while (c.moveToNext());

        assertTrue("Error: database doesn't have all of the columns", columnSet.isEmpty());
    }

    public void testScheduleTable(){
        ScheduleSQLiteHelper dbHelper = new ScheduleSQLiteHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = createScheduleValues();

        long rowId = db.insert(ScheduleContract.ScheduleEntry.TABLE_NAME, null, values);
        assertTrue(rowId != -1);


        Cursor cursor = db.query(
                ScheduleContract.ScheduleEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        assertTrue("Error: nothing returned from query", cursor.moveToFirst());

        validateRecord("entry failed to validate", cursor, createScheduleValues());

        assertFalse("Error: more than one record found", cursor.moveToNext());

        cursor.close();
        dbHelper.close();
    }



    //"United States", 1477065600, 1477080000, 1477152000, 1477162800, 1477252800
    static ContentValues createScheduleValues(){
        ContentValues values = new ContentValues();
        values.put(ScheduleContract.ScheduleEntry.COLUMN_COUNTRY, "United States");
        values.put(ScheduleContract.ScheduleEntry.COLUMN_FP1, 1477065600);
        values.put(ScheduleContract.ScheduleEntry.COLUMN_FP2, 1477080000);
        values.put(ScheduleContract.ScheduleEntry.COLUMN_FP3, 1477152000);
        values.put(ScheduleContract.ScheduleEntry.COLUMN_QUALY, 1477162800);
        values.put(ScheduleContract.ScheduleEntry.COLUMN_RACE, 1477252800);

        return values;
    }

    static void validateRecord(String error, Cursor values, ContentValues expected){
        Set<Map.Entry<String, Object>> valueSet = expected.valueSet();
        for(Map.Entry<String, Object> entry : valueSet){
            String columnName = entry.getKey();
            int idx = values.getColumnIndex(columnName);
            assertFalse("Column '" + columnName + "' not found." + error, idx == -1);
            String expectedvalue = entry.getValue().toString();
            assertEquals("Value '" + entry.getValue().toString() + "' did not match the expected value '" +
                    expectedvalue + "'. " + error, expectedvalue, values.getString(idx));
        }
    }
}
