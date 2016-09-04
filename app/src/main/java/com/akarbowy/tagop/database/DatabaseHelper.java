package com.akarbowy.tagop.database;

public class DatabaseHelper /*extends OrmLiteSqliteOpenHelper */{

    /*private static final String DATABASE_NAME = "tagop";
    private static final int DATABASE_VERSION = 1;

    *//**
     * The data access object used to interact with the Sqlite database to do C.R.U.D operations.
     *//*
    private Dao<TagHistory, Long> historyDao;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION,
                *//**
                 * R.raw.ormlite_config is a reference to the ormlite_config.txt file in the
                 * /res/raw/ directory of this project
                 * *//*
                R.raw.ormlite_config);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, TagHistory.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource,
                          int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, TagHistory.class, false);
            onCreate(database, connectionSource);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Dao<TagHistory, Long> getHistoryDao() throws SQLException {
        if (historyDao == null) {
            historyDao = getDao(TagHistory.class);
        }
        return historyDao;
    }*/
}