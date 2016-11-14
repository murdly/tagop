package com.akarbowy.tagop.data.database;


import android.content.Context;
import android.support.annotation.NonNull;

import com.akarbowy.tagop.data.database.model.CommentModel;
import com.akarbowy.tagop.data.database.model.EmbedModel;
import com.akarbowy.tagop.data.database.model.PostDataMapper;
import com.akarbowy.tagop.data.database.model.PostModel;
import com.akarbowy.tagop.data.database.model.TagModel;
import com.akarbowy.tagop.data.network.model.Comment;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import timber.log.Timber;

@Singleton
public class LocalDataSource {

    private final DatabaseHelper helper;
    private Dao<TagModel, Integer> tagDao;
    private Dao<PostModel, Integer> postDao;
    private Dao<CommentModel, Integer> commentDao;
    private Dao<EmbedModel, Integer> embedDao;

    @Inject public LocalDataSource(Context context) {
        helper = new DatabaseHelper(context);
        try {
            tagDao = helper.getTagDao();
            postDao = helper.getPostDao();
            commentDao = helper.getCommentDao();
            embedDao = helper.getEmbedDao();
        } catch (SQLException e) {
            Timber.e(e.getMessage(), "Error when getting daos.");
        }
    }

    public void getHistory(GetSearchHistoryCallback callback) {
        try {
            callback.onDataLoaded(tagDao.queryForAll());
        } catch (SQLException e) {
            callback.onDataNotAvailable();
            Timber.e(e.getMessage(), "Error when querying for history tags.");
        }
    }

    public void deleteHistory() {
        try {
            tagDao.deleteBuilder().delete();
            // delete posts
        } catch (SQLException e) {
            Timber.i(e.getMessage(), "Error when deleting history tags.");
        }
    }

    public void saveTag(TagModel tag) {
        try {
            boolean nameNotExists = tagDao.queryForEq(TagModel.TAG_FIELD_NAME, tag.getName()).isEmpty();
            if (nameNotExists) {
                tagDao.create(tag);
            }
        } catch (SQLException e) {
            Timber.e(e.getMessage(), "Error when saving post.");
        }
    }

    public void loadPosts(@NonNull TagModel tag, int page, @NonNull LoadPostsCallback callback) {
        if (page > 1) {
            callback.onDataNotAvailable();
            return;
        }
        List<PostModel> postModels = new ArrayList<>();
        try {
            postModels = postDao.queryForEq(PostModel.POST_FIELD_TAG, tag.getName());
        } catch (SQLException e) {
            Timber.e(e.getMessage(), "Error when loading post from local source.");
        } finally {
            callback.onDataLoaded(postModels);
        }

    }

    public void savePosts(List<PostModel> posts) {
        for (PostModel post : posts) {
            try {
                for (Comment comment : post.comments) {
                    CommentModel commentModel = PostDataMapper.mapComment(post, comment);
                    int cc = commentDao.create(commentModel);
//                    Timber.i("Save status cc:%s", cc);
                }
                int ec = embedDao.create(post.embedModel);
                int pc = postDao.create(post);
//                Timber.i("Save status p:%s, e:%s", pc, ec);
            } catch (SQLException e) {
                Timber.e(e.getMessage(), "Error when saving post.");
            }
        }
    }

//    public void deletePost(PostModel postModel) {
//        postModel.commentsModel.clear();
//        try {
//            postDao.delete(postModel);
//        } catch (SQLException e) {
//            Timber.e(e.getMessage(), "Error when deleting post.");
//        }
//    }

    public void deletePostsWithTag(TagModel tag) {
        try {
            DeleteBuilder<PostModel, Integer> deleteBuilder = postDao.deleteBuilder();
            deleteBuilder.where().eq(PostModel.POST_FIELD_TAG, tag.getName());
            deleteBuilder.delete();
        } catch (SQLException e) {
            Timber.e(e.getMessage(), "Error when deleting post.");
        }
    }


    public interface LoadPostsCallback {
        void onDataLoaded(List<PostModel> data);

        void onDataNotAvailable();
    }

    public interface GetSearchHistoryCallback {
        void onDataLoaded(List<TagModel> data);

        void onDataNotAvailable();
    }
}
