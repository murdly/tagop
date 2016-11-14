package com.akarbowy.tagop.data.database.model;


import com.akarbowy.tagop.data.network.model.Comment;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.List;

@DatabaseTable(tableName = "posts")
public class PostModel {

    public static final String POST_FIELD_TAG = "for_tag";

    @DatabaseField(columnName = POST_FIELD_TAG) String tag;

    @DatabaseField(id = true) Integer id;
    @DatabaseField public String author;
    @DatabaseField public String authorAvatar;
    @DatabaseField public String date;
    @DatabaseField public String body;
    @DatabaseField String source;
    @DatabaseField public String url;
    @DatabaseField public Integer voteCount;
    @DatabaseField public Integer commentCount;
    // model.comments needs to be created via dao first (due to ForeignCollection)
    @ForeignCollectionField public ForeignCollection<CommentModel> commentsModel;
    @DatabaseField(foreign = true,
            foreignAutoRefresh = true,
            columnDefinition = "integer references embeds(id) on delete cascade") public EmbedModel embedModel;

    public List<Comment> comments;


    public PostModel() {
    }

    public PostModel(Integer id) {
        this.id = id;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }


    public List<CommentModel> getComments() {
        return new ArrayList<>();
    }
}
