package com.akarbowy.tagop.data.database.model;

public class CommentModel {


     Integer commentId;
     public String author;
     public String authorAvatar;
     public String date;
     public String body;
     String source;
     Integer postEntryId;
     public Integer voteCount;
     Integer userVote;
     String type;

     EmbedModel embed;

    public CommentModel() {
    }


    public EmbedModel getEmbed() {
        return embed;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public String getAuthor() {
        return author;
    }

    public String getAuthorAvatar() {
        return authorAvatar;
    }

    public String getDate() {
        return date;
    }

    public String getBody() {
        return body;
    }

    public String getSource() {
        return source;
    }

    public Integer getPostEntryId() {
        return postEntryId;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public Integer getUserVote() {
        return userVote;
    }

    public String getType() {
        return type;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setAuthorAvatar(String authorAvatar) {
        this.authorAvatar = authorAvatar;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setPostEntryId(Integer postEntryId) {
        this.postEntryId = postEntryId;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public void setUserVote(Integer userVote) {
        this.userVote = userVote;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setEmbed(EmbedModel embed) {
        this.embed = embed;
    }
}
