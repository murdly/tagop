package com.akarbowy.tagop.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.akarbowy.tagop.R;
import com.akarbowy.tagop.data.database.model.CommentModel;
import com.akarbowy.tagop.data.database.model.EmbedModel;
import com.akarbowy.tagop.data.database.model.PostModel;
import com.akarbowy.tagop.data.database.model.TagModel;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "tagop";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, TagModel.class);
            TableUtils.createTableIfNotExists(connectionSource, EmbedModel.class);
            TableUtils.createTableIfNotExists(connectionSource, CommentModel.class);
            TableUtils.createTableIfNotExists(connectionSource, PostModel.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource,
                          int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, PostModel.class, false);
            TableUtils.dropTable(connectionSource, CommentModel.class, false);
            TableUtils.dropTable(connectionSource, EmbedModel.class, false);
            TableUtils.dropTable(connectionSource, TagModel.class, false);
            onCreate(database, connectionSource);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Dao<TagModel, Integer> getTagDao() throws SQLException {
        return getDao(TagModel.class);
    }

    public Dao<PostModel, Integer> getPostDao() throws SQLException {
        return getDao(PostModel.class);
    }

    public Dao<CommentModel, Integer> getCommentDao() throws SQLException {
        return getDao(CommentModel.class);
    }

    public Dao<EmbedModel, Integer> getEmbedDao() throws SQLException {
        return getDao(EmbedModel.class);
    }
}