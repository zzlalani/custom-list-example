package com.zeeshanlalani.customlistexample.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.zeeshanlalani.customlistexample.Models.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 10/29/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "students_manager";

    // Contacts table name
    private static final String TABLE_STUDENTS = "students";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create = "CREATE TABLE " + TABLE_STUDENTS + "("
                + " id INTEGER PRIMARY KEY,"
                + " lastName TEXT,"
                + " firstName TEXT" + ")";
        sqLiteDatabase.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void saveStudent( int id, String firstName, String lastName ) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("firstName", firstName);
        values.put("lastName", lastName);

        // Inserting Row
        db.insert(TABLE_STUDENTS, null, values);
        db.close(); // Closing database connection
    }

    public List<Student> getStudents() {
        List<Student> students = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_STUDENTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Student student = new Student(cursor.getInt(0), cursor.getString(1) + " " + cursor.getString(2));
                students.add(student);
            } while (cursor.moveToNext());
        }

        return students;
    }
}
