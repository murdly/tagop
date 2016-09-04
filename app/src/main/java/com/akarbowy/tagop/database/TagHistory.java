package com.akarbowy.tagop.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tagop")
public class TagHistory {

    @DatabaseField(generatedId = true) Long id;

    @DatabaseField private String tagName;

    public TagHistory() {
    }
}
