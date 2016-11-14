package com.akarbowy.tagop.data.database.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "embeds")
public class EmbedModel {

    @DatabaseField(generatedId = true) int id;
    @DatabaseField public String type;
    @DatabaseField public String previewUrl;
    @DatabaseField public String url;
    @DatabaseField public String source;

    public EmbedModel() {
    }
}
