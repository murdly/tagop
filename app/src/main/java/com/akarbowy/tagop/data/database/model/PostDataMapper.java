package com.akarbowy.tagop.data.database.model;


import android.support.annotation.NonNull;

import com.akarbowy.tagop.data.network.model.Comment;
import com.akarbowy.tagop.data.network.model.Embed;
import com.akarbowy.tagop.data.network.model.Post;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class PostDataMapper {

    public static List<PostModel> map(@NonNull List<Post> posts, @NonNull TagModel tag) {
        List<PostModel> modelsList;

        if (posts.isEmpty()) {
            modelsList = Collections.emptyList();
        } else {
            modelsList = new ArrayList<>();
            for (Post post : posts) {
                modelsList.add(map(post, tag));
            }
        }

        return modelsList;
    }

    private static PostModel map(@NonNull Post post, @NonNull TagModel tag) {
        PostModel model = new PostModel(post.id);
        model.setTag(tag);
        model.setAuthor(post.author);
        model.setAuthorAvatar(post.authorAvatar);
        model.setDate(post.date);
        model.setBody(post.body);
        model.setUrl(post.url);
        model.setVoteCount(post.voteCount);
        model.setCommentCount(post.commentCount);

        List<CommentModel> commentModelList = new ArrayList<>();
        for (Comment comment : post.comments) {
            commentModelList.add(mapComment(comment));
        }
        model.setComments(commentModelList);
        if (post.embed != null) {
            model.setEmbed(mapEmbed(post.embed));
        }
        return model;
    }

    public static CommentModel mapComment(Comment comment) {
        CommentModel model = new CommentModel();
        model.commentId = comment.id;
        model.author = comment.author;
        model.authorAvatar = comment.authorAvatar;
        model.date = comment.date;
        model.body = comment.body;
        model.postEntryId = comment.entryId;
        model.voteCount = comment.voteCount;
        model.userVote = comment.userVote;
        model.type = comment.type;
        if (comment.embed != null) {
            model.embed = mapEmbed(comment.embed);
        }

        return model;
    }

    private static EmbedModel mapEmbed(Embed embed) {
        EmbedModel model = new EmbedModel();
        model.setType(embed.type);
        model.setPreviewUrl(embed.previewUrl);
        model.setUrl(embed.url);
        return model;
    }

//    public static List<Post> mapPostModels(@NonNull List<PostModel> models, @NonNull String tag) {
//        List<Post> postsList;
//
//        if (models.isEmpty()) {
//            postsList = Collections.emptyList();
//        } else {
//            postsList = new ArrayList<>();
//            for (PostModel model : models) {
//                postsList.add(mapPostModel(model, tag));
//            }
//        }
//
//        return postsList;
//    }

    /*private static Post mapPostModel(@NonNull PostModel model, @NonNull String tag) {
        Post p = new Post();
        p.postId = model.postId;
//        p.tag = tag;
        p.author = model.author;
        p.authorAvatar = model.authorAvatar;
        p.date = model.date;
        p.body = model.body;
        if (model.source != null) {
            p.source = model.source;
        }
        p.url = model.url;
        p.voteCount = model.voteCount;
        p.commentCount = model.commentCount;
//        p.comments = modeltoComments(model.comments);
        if (model.embedModel != null) {
            p.embed = modelToEmbed(model.embedModel);
        }
        return p;
    }*/

    /*private static List<Comment> modeltoComments(List<CommentModel> models) {
        List<Comment> comments = new ArrayList<>();
        for (CommentModel m : models) {
            Comment c = new Comment();
            c.postId = m.postId;
            c.author = m.author;
            c.authorAvatar = m.authorAvatar;
            c.date = m.date;
            c.body = m.body;
            c.source = m.source;
            c.entryId = m.entryId;
            c.voteCount = m.voteCount;
            c.userVote = m.userVote;
            c.type = m.type;
            c.embed = modelToEmbed(m.embedModel);
            comments.add(c);
        }

        return comments;
    }

    private static Embed modelToEmbed(EmbedModel model) {
        Embed embed = new Embed();
        embed.type = model.type;
        embed.previewUrl = model.previewUrl;
        embed.url = model.url;
        embed.source = model.source;
        return embed;
    }*/
}
