package com.example.mukesh.classmonitor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by mukesh on 2/13/2016.
 */
public class TimetableDb extends SQLiteOpenHelper {


    private static final String TAG = "SQLiteHelper";

    /*------------------- Databae Information ----------------------*/
    private static final String DATABASE_NAME = "timetable.db";
    private static final int DATABASE_VERSION = 1;


    /*------------------- Tables ----------------------*/
    public static final String TABLE_COURSES = "Courses";


    /*------------------- Columns for Table Courses ----------------------*/
    public static final String COLUMN_NAME = "Title";
    public static final String COLUMN_NAME_ENTRY_ID = "Entryid";
    public static final String COLUMN_location_X = "Location_x";
    public static final String COLUMN_location_Y = "Location_y";
    public static final String COLUMN_timestamp = "Timestamp";
    public static final String COLUMN_DATE ="Date";
    public static final String COLUMN_TIME ="Time";

    private static final String CREATE_TABLE_COURSES = "CREATE TABLE " + TABLE_COURSES + "(" +
            COLUMN_NAME_ENTRY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_NAME + " VARCHAR," +
            COLUMN_location_X + " DOUBLE," +
            COLUMN_location_Y + " DOUBLE," +
            COLUMN_timestamp + " LONG," +
            COLUMN_DATE + " VARCHAR," +
            COLUMN_TIME + " VARCHAR" +
            ")";

    //timeTableDbHelper mDbHelper = new timeTableDbHelper(getContext());

    // If you change the database schema, you must increment the database version.

    public TimetableDb(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, CREATE_TABLE_COURSES);
        db.execSQL(CREATE_TABLE_COURSES);
    }


    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        //       db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public long createCourse(Course course) {

        SQLiteDatabase database = this.getWritableDatabase();
        //Log.d(TAG, "Creating course in database");
        ContentValues values = new ContentValues();
        //Log.d(TAG,"Putting Values"); //Putting Values
        values.put(COLUMN_NAME, course.getCourseTitle());
        values.put(COLUMN_location_X, course.getLatitude());
        values.put(COLUMN_location_Y, course.getLongitude());
        //values.put(COLUMN_NAME_ENTRY_ID, course.getid());
        values.put(COLUMN_timestamp, course.getTimestamp());
        values.put(COLUMN_DATE,course.getDate());
        values.put(COLUMN_TIME,course.getTime());
        long insertId = database.insert(TABLE_COURSES, null, values);
        database.close();
        Log.d(TAG, "InsertId = " + insertId);

        return insertId;

    }



    public Course getACourse()
    {
        Course course = new Course();
        SQLiteDatabase db = getReadableDatabase();
        long current = System.currentTimeMillis();
        String query = "SELECT * FROM courses WHERE timestamp > " + current +" ORDER BY timestamp LIMIT 1";
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst())
        {
            course.setLatitude(cursor.getDouble(cursor.getColumnIndex(COLUMN_location_X)));
            course.setLongitude(cursor.getDouble(cursor.getColumnIndex(COLUMN_location_Y)));
            course.setCourseTitle(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
            course.setTimestamp(cursor.getLong(cursor.getColumnIndex(COLUMN_NAME)));
            course.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_DATE)));
            course.setTime(cursor.getString(cursor.getColumnIndex(COLUMN_TIME)));
        }
    return course ;
    }
    public ArrayList<Course> getCourses() {
        ArrayList<Course> courses = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM courses ORDER BY timestamp";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Course course = new Course();
                course.setCourseTitle(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                course.setLatitude(cursor.getDouble(cursor.getColumnIndex(COLUMN_location_X)));
                course.setLongitude(cursor.getDouble(cursor.getColumnIndex(COLUMN_location_Y)));
                course.setTimestamp(cursor.getLong(cursor.getColumnIndex(COLUMN_timestamp)));
                course.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_DATE)));
                course.setTime(cursor.getString(cursor.getColumnIndex(COLUMN_TIME)));
                courses.add(course);
            } while (cursor.moveToNext());
        }
        return courses;
    }

}
