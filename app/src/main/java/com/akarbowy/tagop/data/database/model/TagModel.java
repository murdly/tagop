package com.akarbowy.tagop.data.database.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tags")
public class TagModel {

    public static final String TAG_FIELD_NAME = "name";

    @DatabaseField(generatedId = true) Long id;

    @DatabaseField(columnName = TAG_FIELD_NAME) private String tagName;

    private boolean saveInHistory = false;

    public TagModel() {
    }

    public TagModel(String tag) {
        this.tagName = tag;
    }

    public TagModel(String tagName, boolean saveInHistory) {
        this.tagName = tagName;
        this.saveInHistory = saveInHistory;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return tagName;
    }

    public boolean isForSaving() {
        return saveInHistory;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TagModel that = (TagModel) o;

        return tagName.equals(that.tagName);

    }

    @Override public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + tagName.hashCode();
        return result;
    }

    @Override public String toString() {
        return String.format("%s-%s", id, tagName);
    }
}
