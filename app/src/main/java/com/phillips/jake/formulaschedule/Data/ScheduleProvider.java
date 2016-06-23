package com.phillips.jake.formulaschedule.Data;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

/**
 * Created by jphil on 6/23/2016.
 */
public class ScheduleProvider extends ContentProvider {
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private ScheduleSQLiteHelper mDBHelper;

    static final int SCHEDULE = 100;
    static final int SCHEDULE_COUNTRY = 101;

    private static final SQLiteQueryBuilder sQueryBuilder;

    static{
        sQueryBuilder = new SQLiteQueryBuilder();
        sQueryBuilder.setTables(
                ScheduleContract.ScheduleEntry.TABLE_NAME
        );
    }

    private static final String sCountrySelection =
            ScheduleContract.ScheduleEntry.TABLE_NAME +
                    "." + ScheduleContract.ScheduleEntry.COLUMN_COUNTRY + " = ?";

    static UriMatcher buildUriMatcher(){
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = ScheduleContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, ScheduleContract.PATH_SCHEDULE, SCHEDULE);
        matcher.addURI(authority, ScheduleContract.PATH_SCHEDULE + "/*" , SCHEDULE_COUNTRY);
        return matcher;
    }

    @Override
    public boolean onCreate(){
        mDBHelper = new ScheduleSQLiteHelper(getContext());
        return true;
    }

    @Override
    public String getType(Uri uri){
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case SCHEDULE:
                return ScheduleContract.ScheduleEntry.CONTENT_TYPE;
            case SCHEDULE_COUNTRY:
                return ScheduleContract.ScheduleEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder){
        Cursor retCursor;

        switch (sUriMatcher.match(uri)){
            case SCHEDULE_COUNTRY:
                retCursor = mDBHelper.getReadableDatabase().query(
                        ScheduleContract.ScheduleEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case SCHEDULE:
                retCursor = mDBHelper.getReadableDatabase().query(
                        ScheduleContract.ScheduleEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values){
        final SQLiteDatabase db = mDBHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match){
            case SCHEDULE_COUNTRY:
                long _id = db.insert(ScheduleContract.ScheduleEntry.TABLE_NAME, null, values);
                if(_id > 0){
                    returnUri = ScheduleContract.ScheduleEntry.buildScheduleUri(_id);
                }
                else{
                    throw new android.database.SQLException("Failed to insert row into: " + uri);
                }
                break;
            case SCHEDULE:
                _id = db.insert(ScheduleContract.ScheduleEntry.TABLE_NAME, null, values);
                if(_id > 0){
                    returnUri = ScheduleContract.ScheduleEntry.buildScheduleUri(_id);
                }
                else{
                    throw new android.database.SQLException("Failed to insert row into: " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs){
        final SQLiteDatabase db = mDBHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;

        if(null == selection){
            selection = "1";
        }

        switch (match){
            case SCHEDULE:
                rowsDeleted = db.delete(ScheduleContract.ScheduleEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if(rowsDeleted != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs){
        final SQLiteDatabase db = mDBHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match){
            case SCHEDULE:
                rowsUpdated = db.update(ScheduleContract.ScheduleEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if(rowsUpdated != 0){
            getContext().getContentResolver().notifyChange(uri, null);;
        }
        return rowsUpdated;
    }

    public int bulkInsert(Uri uri, ContentValues[] values){
        final SQLiteDatabase db = mDBHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);

        switch (match){
            case SCHEDULE:
                db.beginTransaction();
                int returnCount = 0;
                try{
                    for(ContentValues value: values){
                        long _id = db.insert(ScheduleContract.ScheduleEntry.TABLE_NAME, null, value);
                        if(_id != -1){
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally{
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }
    }

    @Override
    @TargetApi(11)
    public void shutdown(){
        mDBHelper.close();
        super.shutdown();
    }
}
