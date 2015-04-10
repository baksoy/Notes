package com.thinkful.notes;

import android.provider.BaseColumns;

// Special class that defines the names of all the tables and columns.
public class NotesDBContract {
    private NotesDBContract() {}

    /* update version number when db changes */
    public static final String DATABASE_NAME = "notesdb";
    public static final int DATABASE_VERSION = 1;

    /* Inner class that defines Note table */
    public static abstract class Note implements BaseColumns {
        public static final String TABLE_NAME = "note";
        public static final String COLUMN_NAME_ID = "rowid";
        public static final String COLUMN_NAME_NOTE_TEXT = "note_text";
        public static final String COLUMN_NAME_STATUS = "status";
        public static final String COLUMN_NAME_NOTE_DATE = "note_date";
    }

    /* Inner class that defines Note_Tag table */
    public static abstract class Note_Tag implements BaseColumns {
        public static final String TABLE_NAME = "note_tag";
        public static final String COLUMN_NAME_NOTE_ID = "note_id";
        public static final String COLUMN_NAME_TAG_ID = "tag_id";
    }

    /* Inner class that defines Tag table */
    public static abstract class Tag implements BaseColumns {
        public static final String TABLE_NAME = "tag";
        public static final String COLUMN_NAME_TAG_NAME = "tag_name";
    }

    /*CREATE TABLE statements*/
    public static final String SQL_CREATE_NOTE = String.format(
            "CREATE TABLE %s (%s TEXT, %s TEXT, %s DATETIME)",
            Note.TABLE_NAME, Note.COLUMN_NAME_NOTE_TEXT, Note.COLUMN_NAME_STATUS, Note.COLUMN_NAME_NOTE_DATE);

    public static final String SQL_CREATE_TAG = String.format(
            "CREATE TABLE %s (%s TEXT)",
            Tag.TABLE_NAME, Tag.COLUMN_NAME_TAG_NAME);

    public static final String SQL_CREATE_NOTE_TAG = String.format(
            "CREATE TABLE %s (%s INTEGER, %s INTEGER)",
            Note_Tag.TABLE_NAME, Note_Tag.COLUMN_NAME_NOTE_ID, Note_Tag.COLUMN_NAME_TAG_ID);

    /*DROP TABLE statements*/
    public static final String SQL_DROP_NOTE = String.format(
            "DROP TABLE IF EXISTS %s",
            Note.TABLE_NAME);

    public static final String SQL_DROP_TAG = String.format(
            "DROP TABLE IF EXISTS %s",
            Tag.TABLE_NAME);

    public static final String SQL_DROP_NOTE_TAG = String.format(
            "DROP TABLE IF EXISTS %s",
            Note_Tag.TABLE_NAME);
}
