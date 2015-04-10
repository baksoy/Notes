package com.thinkful.notes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


// This class helps you create your database and manage its version.
public class NotesDBHelper extends SQLiteOpenHelper {
    private String dbName;
    private int oldVersion;
    private int newVersion;

    private static NotesDBHelper instance = null;

    private NotesDBHelper(Context context) {
        super(context, NotesDBContract.DATABASE_NAME, null, NotesDBContract.DATABASE_VERSION);
    }

    public static NotesDBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new NotesDBHelper(context);
        }
        return instance;
    }


    public void onCreate(SQLiteDatabase db) {
        db.execSQL(NotesDBContract.SQL_CREATE_NOTE);
        db.execSQL(NotesDBContract.SQL_CREATE_TAG);
        db.execSQL(NotesDBContract.SQL_CREATE_NOTE_TAG);

    }

    /* In an application that is in use you may not want to drop the tables at onUpgrade().
    Instead, ALTER TABLE would be more appropriate to preserve any data that may already be
    in the table. */

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(NotesDBContract.SQL_DROP_NOTE);
        db.execSQL(NotesDBContract.SQL_DROP_TAG);
        db.execSQL(NotesDBContract.SQL_DROP_NOTE_TAG);
        onCreate(db);
    }
}
