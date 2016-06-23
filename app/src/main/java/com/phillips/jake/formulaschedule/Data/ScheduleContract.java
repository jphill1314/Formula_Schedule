package com.phillips.jake.formulaschedule.Data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by jphil on 6/23/2016.
 */
public class ScheduleContract {
    public static final String CONTENT_AUTHORITY = "com.phillips.jake.formulaschedule.app";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_SCHEDULE = "location";

    public static final class ScheduleEntry implements BaseColumns{
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_SCHEDULE).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SCHEDULE;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SCHEDULE;

        public static final String TABLE_NAME = "location";
        public static final String COLUMN_COUNTRY = "country";
        public static final String COLUMN_FP1 = "fp1";
        public static final String COLUMN_FP2 = "fp2";
        public static final String COLUMN_FP3 = "fp3";
        public static final String COLUMN_QUALY = "qualy";
        public static final String COLUMN_RACE = "race";

        public static Uri buildScheduleUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildCountryScheduleUri(String country){
            return CONTENT_URI.buildUpon().appendPath(country).build();
        }

        public static String getCountryFromUri(Uri uri){
            return uri.getPathSegments().get(1);
        }

    }

}
