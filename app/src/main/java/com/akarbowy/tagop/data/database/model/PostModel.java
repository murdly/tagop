package com.akarbowy.tagop.data.database.model;


import java.util.ArrayList;
import java.util.List;

public class PostModel {

    private Integer postId;
    private TagModel tag;

    private String author;
    private String authorAvatar;
    private String date;
    private String body;
    private String url;
    private Integer voteCount;
    private Integer commentCount;
    private List<CommentModel> comments = new ArrayList<>();
    private EmbedModel embedModel;


    public PostModel(Integer postId) {
        this.postId = postId;
    }

    public List<CommentModel> getComments() {
        return comments;
    }

    public void setComments(List<CommentModel> comments) {
        this.comments = comments;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthorAvatar() {
        return authorAvatar;
    }

    public void setAuthorAvatar(String authorAvatar) {
        this.authorAvatar = authorAvatar;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public EmbedModel getEmbed() {
        return embedModel;
    }

    public TagModel getTag() {
        return tag;
    }

    public void setTag(TagModel tag) {
        this.tag = tag;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setEmbed(EmbedModel embedModel) {
        this.embedModel = embedModel;
    }
}
