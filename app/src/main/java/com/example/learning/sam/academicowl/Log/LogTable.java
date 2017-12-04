package com.example.learning.sam.academicowl.Log;

import java.util.Calendar;

/**
 * Created by Sam on 12/3/2017.
 */

public class LogTable {

    //private variables
    static int _id;
    static Calendar _system_time;
    static String _activity;

    // Empty constructor
    public LogTable(){

    }
    // constructor

    public LogTable(int _id, Calendar _system_time, String _activity) {
        this._id = _id;
        this._system_time = _system_time;
        this._activity = _activity;
    }

    public static Calendar get_system_time() {
        return _system_time;
    }

    public void set_system_time(Calendar _system_time) {
        this._system_time = _system_time;
    }

    public static String get_activity() {
        return _activity;
    }

    public void set_activity(String _activity) {
        this._activity = _activity;
    }
}