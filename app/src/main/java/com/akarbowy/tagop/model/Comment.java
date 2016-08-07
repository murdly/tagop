package com.akarbowy.tagop.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by akarbowy on 07.08.16.
 */
public class Comment {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("author")
    @Expose
    public String author;
    @SerializedName("author_avatar")
    @Expose
    public String authorAvatar;
    @SerializedName("author_avatar_big")
    @Expose
    public String authorAvatarBig;
    @SerializedName("author_avatar_med")
    @Expose
    public String authorAvatarMed;
    @SerializedName("author_avatar_lo")
    @Expose
    public String authorAvatarLo;
    @SerializedName("date")
    @Expose
    public String date;
    @SerializedName("body")
    @Expose
    public String body;
    @SerializedName("source")
    @Expose
    public String source;
    @SerializedName("entry_id")
    @Expose
    public Integer entryId;
    @SerializedName("vote_count")
    @Expose
    public Integer voteCount;
    @SerializedName("user_vote")
    @Expose
    public Integer userVote;
    @SerializedName("embed")
    @Expose
    public Embed embed;
    @SerializedName("type")
    @Expose
    public String type;
}
