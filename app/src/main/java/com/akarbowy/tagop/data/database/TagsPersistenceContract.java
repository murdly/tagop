package com.akarbowy.tagop.data.database;


import android.provider.BaseColumns;

public class TagsPersistenceContract {

    private TagsPersistenceContract() {
    }

    public static abstract class TagEntry implements BaseColumns {
        public static final String TABLE_NAME = "tag";
        public static final String COLUMN_NAME_ENTRY_ID = "entry_id";
        public static final String COLUMN_NAME_TITLE = "title";
    }
}
