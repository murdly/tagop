package com.akarbowy.tagop.data.database.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "comments")
public class CommentModel {

    public static final String POST_MODEL_ID_FIELD_NAME = "post_model_id";

    @DatabaseField(generatedId = true) Integer id;
    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = POST_MODEL_ID_FIELD_NAME)
    private PostModel postModel;

    @DatabaseField Integer commentId;
    @DatabaseField public String author;
    @DatabaseField public String authorAvatar;
    @DatabaseField public String date;
    @DatabaseField public String body;
    @DatabaseField String source;
    @DatabaseField Integer entryId;
    @DatabaseField public Integer voteCount;
    @DatabaseField Integer userVote;
    @DatabaseField String type;

    @DatabaseField(foreign = true,
            foreignAutoRefresh = true,
            columnDefinition = "integer references embeds(id) on delete cascade")
            public EmbedModel embedModel;

    public CommentModel() {
    }

    public CommentModel(PostModel postModel) {
        this.postModel = postModel;
    }
}
