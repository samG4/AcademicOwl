package com.example.learning.sam.academicowl.Log;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Sam on 12/3/2017.
 */

public class LogDatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "contactsManager";

    public LogDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    // Contacts table name
    private static final String TABLE_LOG = "log";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_CALENDAR = "calendar";
    private static final String KEY_ACTIVITY = "activity";



    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_LOG + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_CALENDAR + " TEXT,"
                + KEY_ACTIVITY + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOG);

        // Create tables again
        onCreate(db);
    }

    // Adding new contact
    public void insertLog( LogTable logTable) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CALENDAR, LogTable.get_system_time().toString()); // Contact Name
        values.put(KEY_ACTIVITY, LogTable.get_activity()); // Contact Phone Number

        // Inserting Row
        db.insert(TABLE_LOG, null, values);
        db.close(); // Closing database connection
    }

    // Getting All logs
    public List<LogTable> getLogs() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<LogTable> logList = new ArrayList<LogTable>();
        String query = "SELECT * FROM "+ TABLE_LOG;
        Cursor cursor = db.rawQuery(query,null);

        if(cursor.moveToFirst()){
            do{
                LogTable log =  new LogTable();
                Calendar cal = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
                try {
                    cal.setTime(sdf.parse(cursor.getString(1)));
                    log.set_system_time(cal);
                    log.set_activity(cursor.getString(2));
                }catch (Exception e){

                }
                logList.add(log);

            }while (cursor.moveToNext());
        }
        return logList;
    }

    // Getting contacts Count
    public int getLogCount() {
        return 0;
    }
    // Updating single log
    public int updateLog(LogTable logTable) {
        return 0;
    }

    // Deleting single log
    public void deleteLog(LogTable logTable) {}
}