package com.example.lab3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyOpener extends SQLiteOpenHelper {

    protected final static String db_name = "MessagesDB";
    protected final static int version = 1;
    public final static String table_name = "Messages";
    public final static String col_message = "Message";
    public final static String col_id = "_id";
    public final static String col_type = "Type";


    public MyOpener(@Nullable Context context) {
        super(context, db_name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + table_name + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        col_type + " text, " + col_message + " text);"
                  );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + table_name);
        onCreate(db);
    }
}
