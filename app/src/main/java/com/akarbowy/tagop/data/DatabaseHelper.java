package com.akarbowy.tagop.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "Tagop.db";

    private static final String TEXT_TYPE = " TEXT";

    private static final String BOOLEAN_TYPE = " INTEGER";

    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_TAGS =
            "CREATE TABLE " + TagsPersistenceContract.TagEntry.TABLE_NAME + " (" +
                    TagsPersistenceContract.TagEntry._ID + TEXT_TYPE + " PRIMARY KEY," +
                    TagsPersistenceContract.TagEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
                    TagsPersistenceContract.TagEntry.COLUMN_NAME_TITLE + TEXT_TYPE +
                    " )";

    private static final String SQL_CREATE_POSTS =
            "CREATE TABLE " + PostsPersistenceContract.PostEntry.TABLE_NAME + " (" +
                    PostsPersistenceContract.PostEntry._ID + TEXT_TYPE + " PRIMARY KEY," +
                    PostsPersistenceContract.PostEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
                    PostsPersistenceContract.PostEntry.COLUMN_NAME_AUTHOR + TEXT_TYPE + COMMA_SEP +
                    PostsPersistenceContract.PostEntry.COLUMN_NAME_AUTHOR_AVATAR + TEXT_TYPE + COMMA_SEP +
                    PostsPersistenceContract.PostEntry.COLUMN_NAME_DATE + TEXT_TYPE + COMMA_SEP +
                    PostsPersistenceContract.PostEntry.COLUMN_NAME_BODY + TEXT_TYPE + COMMA_SEP +
                    PostsPersistenceContract.PostEntry.COLUMN_NAME_URL + TEXT_TYPE + COMMA_SEP +
                    PostsPersistenceContract.PostEntry.COLUMN_NAME_VOTE_COUNT + TEXT_TYPE + COMMA_SEP +
                    PostsPersistenceContract.PostEntry.COLUMN_NAME_COMMENT_COUNT + TEXT_TYPE + COMMA_SEP +
                    PostsPersistenceContract.PostEntry.COLUMN_NAME_TAG_ENTRY_TITLE + TEXT_TYPE  +
                    " )";

    private static final String SQL_CREATE_EMBEDS =
            "CREATE TABLE " + PostsPersistenceContract.EmbedEntry.TABLE_NAME + " (" +
                    PostsPersistenceContract.EmbedEntry._ID + TEXT_TYPE + " PRIMARY KEY," +
                    PostsPersistenceContract.EmbedEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
                    PostsPersistenceContract.EmbedEntry.COLUMN_NAME_TYPE + TEXT_TYPE + COMMA_SEP +
                    PostsPersistenceContract.EmbedEntry.COLUMN_NAME_PREVIEW_URL + TEXT_TYPE + COMMA_SEP +
                    PostsPersistenceContract.EmbedEntry.COLUMN_NAME_URL + TEXT_TYPE  +
                    " )";

    private static final String SQL_CREATE_COMMENTS =
            "CREATE TABLE " + PostsPersistenceContract.CommentEntry.TABLE_NAME + " (" +
                    PostsPersistenceContract.CommentEntry._ID + TEXT_TYPE + " PRIMARY KEY," +
                    PostsPersistenceContract.CommentEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
                    PostsPersistenceContract.CommentEntry.COLUMN_NAME_AUTHOR + TEXT_TYPE + COMMA_SEP +
                    PostsPersistenceContract.CommentEntry.COLUMN_NAME_AUTHOR_AVATAR + TEXT_TYPE + COMMA_SEP +
                    PostsPersistenceContract.CommentEntry.COLUMN_NAME_DATE + TEXT_TYPE + COMMA_SEP +
                    PostsPersistenceContract.CommentEntry.COLUMN_NAME_BODY + TEXT_TYPE + COMMA_SEP +
                    PostsPersistenceContract.CommentEntry.COLUMN_NAME_VOTE_COUNT + TEXT_TYPE + COMMA_SEP +
                    PostsPersistenceContract.CommentEntry.COLUMN_NAME_USER_VOTE + TEXT_TYPE + COMMA_SEP +
                    PostsPersistenceContract.CommentEntry.COLUMN_NAME_TYPE + TEXT_TYPE + COMMA_SEP +
                    PostsPersistenceContract.CommentEntry.COLUMN_NAME_POST_ENTRY_ID + TEXT_TYPE  +
                    " )";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TAGS);
        db.execSQL(SQL_CREATE_EMBEDS);
        db.execSQL(SQL_CREATE_POSTS);
        db.execSQL(SQL_CREATE_COMMENTS);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + PostsPersistenceContract.CommentEntry.TABLE_NAME);
        db.execSQL("DROP TABLE " + PostsPersistenceContract.PostEntry.TABLE_NAME);
        db.execSQL("DROP TABLE " + PostsPersistenceContract.EmbedEntry.TABLE_NAME);
        db.execSQL("DROP TABLE " + TagsPersistenceContract.TagEntry.TABLE_NAME);
    }

}