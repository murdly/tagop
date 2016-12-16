package com.akarbowy.tagop.data;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.akarbowy.tagop.data.PostsPersistenceContract.CommentEntry;
import com.akarbowy.tagop.data.PostsPersistenceContract.PostEntry;
import com.akarbowy.tagop.data.PostsPersistenceContract.EmbedEntry;
import com.akarbowy.tagop.data.TagsPersistenceContract.TagEntry;
import com.akarbowy.tagop.data.model.CommentModel;
import com.akarbowy.tagop.data.model.EmbedModel;
import com.akarbowy.tagop.data.model.PostModel;
import com.akarbowy.tagop.data.model.TagModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import timber.log.Timber;


@Singleton
public class LocalDataSource implements DataSource {

    private final DatabaseHelper dbHelper;

    @Inject public LocalDataSource(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    @Override
    public void getTags(GetHistoryCallback callback) {
        List<TagModel> tags = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                TagEntry.COLUMN_NAME_ENTRY_ID,
                TagEntry.COLUMN_NAME_TITLE
        };

        try {
            Cursor c = db.query(
                    TagEntry.TABLE_NAME, projection, null, null, null, null, null);

            if (c != null && c.getCount() > 0) {
                while (c.moveToNext()) {
                    String itemId = c.getString(c.getColumnIndexOrThrow(TagEntry.COLUMN_NAME_ENTRY_ID));
                    String title = c.getString(c.getColumnIndexOrThrow(TagEntry.COLUMN_NAME_TITLE));
                    TagModel task = new TagModel(itemId, title);
                    tags.add(task);
                }
            }
            if (c != null) {
                c.close();
            }
        } finally {
//            db.close();
        }


        if (tags.isEmpty()) {
            callback.onDataNotAvailable();
        } else {
            callback.onDataLoaded(tags);
        }

    }

    @Override
    public void saveTag(TagModel tag) {
        if (tag == null) {
            Timber.i("Tag is null.");
            return;
        }

        if (isTagAlreadySaved(tag.getTitle())) {
            return;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TagEntry.COLUMN_NAME_ENTRY_ID, tag.getId());
        values.put(TagEntry.COLUMN_NAME_TITLE, tag.getTitle());

        db.insert(TagEntry.TABLE_NAME, null, values);

//        db.close();
    }

    private boolean isTagAlreadySaved(@NonNull String tagTitle) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {TagEntry.COLUMN_NAME_TITLE,};
        String selection = TagEntry.COLUMN_NAME_TITLE + " LIKE ?";
        String[] selectionArgs = {tagTitle};

        Cursor c = db.query(
                TagEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        boolean tagExists = c != null && c.getCount() > 0;
        if (c != null) {
            c.close();
        }

//        db.close();

        return tagExists;
    }

    @Override
    public void deleteTag(TagModel tag) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String selection = TagEntry.COLUMN_NAME_TITLE + " LIKE ?";
        String[] selectionArgs = {tag.getTitle()};

        db.delete(TagEntry.TABLE_NAME, selection, selectionArgs);

//        db.close();

        deleteTagPosts(tag);
    }

    @Override
    public void deleteTagPosts(TagModel tag) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            List<String> linkedPostsIds = getPostIds(tag.getTitle(), db);

            for (String postId : linkedPostsIds) {
                String postEmbedId = getPostEmbedId(postId);
                if (!postEmbedId.isEmpty()) {
                    db.delete(EmbedEntry.TABLE_NAME,
                            EmbedEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?",
                            new String[]{postEmbedId});
                }

                for (String commentEmbedId : getCommentEmbedIds(postId)) {
                    db.delete(EmbedEntry.TABLE_NAME,
                            EmbedEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?",
                            new String[]{commentEmbedId});

                }

                db.delete(CommentEntry.TABLE_NAME,
                        CommentEntry.COLUMN_NAME_POST_ENTRY_ID + " LIKE ?",
                        new String[]{postId});

                db.delete(PostsPersistenceContract.PostEntry.TABLE_NAME,
                        PostsPersistenceContract.PostEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?",
                        new String[]{postId});
            }
        } finally {
//            db.close();
        }
    }

    private List<String> getPostIds(@NonNull String tagTitle, SQLiteDatabase db) {
        List<String> postsIds = new ArrayList<>();

        String[] projection = {
                PostsPersistenceContract.PostEntry.COLUMN_NAME_ENTRY_ID,
        };

        String selection = PostEntry.COLUMN_NAME_TAG_ENTRY_TITLE + " LIKE ?";
        String[] selectionArgs = {tagTitle};

        Cursor c = db.query(
                PostEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                String postId = c.getString(c.getColumnIndexOrThrow(PostEntry.COLUMN_NAME_ENTRY_ID));
                postsIds.add(postId);
            }
        }

        if (c != null) {
            c.close();
        }

        return postsIds;
    }

    private String getPostEmbedId(@NonNull String postId) {
        String embedId = "";
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                PostEntry.COLUMN_NAME_ENTRY_ID,
        };

        String selection = PostEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?";
        String[] selectionArgs = {postId};

        try {
            Cursor c = db.query(
                    PostEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);

            if (c != null && c.getCount() > 0) {
                c.moveToFirst();
                embedId = c.getString(c.getColumnIndexOrThrow(PostEntry.COLUMN_NAME_ENTRY_ID));
            }

            if (c != null) {
                c.close();
            }

        } finally {
//            db.close();
        }


        return embedId;
    }

    private List<String> getCommentEmbedIds(@NonNull String postId) {
        List<String> embedsIds = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                CommentEntry.COLUMN_NAME_ENTRY_ID,
        };

        String selection = CommentEntry.COLUMN_NAME_POST_ENTRY_ID + " LIKE ?";
        String[] selectionArgs = {postId};

        Cursor c = db.query(
                CommentEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                String embedId = c.getString(c.getColumnIndexOrThrow(CommentEntry.COLUMN_NAME_ENTRY_ID));
                embedsIds.add(embedId);
            }
        }

        if (c != null) {
            c.close();
        }

//        db.close();

        return embedsIds;
    }

    @Override
    public void deleteAllTags() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(EmbedEntry.TABLE_NAME, null, null);
        db.delete(TagEntry.TABLE_NAME, null, null);
        db.delete(PostEntry.TABLE_NAME, null, null);
        db.delete(CommentEntry.TABLE_NAME, null, null);

//        db.close();
    }

    @Override
    public void loadPosts(@NonNull TagModel tag, int page, @NonNull GetPostsCallback callback) {
        if (page > 1) {
            callback.onDataNotAvailable();
            return;
        }

        List<PostModel> posts = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                PostEntry.COLUMN_NAME_ENTRY_ID,
                PostEntry.COLUMN_NAME_AUTHOR,
                PostEntry.COLUMN_NAME_AUTHOR_AVATAR,
                PostEntry.COLUMN_NAME_DATE,
                PostEntry.COLUMN_NAME_BODY,
                PostEntry.COLUMN_NAME_URL,
                PostEntry.COLUMN_NAME_VOTE_COUNT,
                PostEntry.COLUMN_NAME_COMMENT_COUNT
        };
        String selection = PostEntry.COLUMN_NAME_TAG_ENTRY_TITLE + " LIKE ?";
        String[] selectionArgs = {tag.getTitle()};

        Cursor c = db.query(
                PostEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                String postId = c.getString(c.getColumnIndexOrThrow(PostEntry.COLUMN_NAME_ENTRY_ID));
                String author = c.getString(c.getColumnIndexOrThrow(PostEntry.COLUMN_NAME_AUTHOR));
                String authorAvatar = c.getString(c.getColumnIndexOrThrow(PostEntry.COLUMN_NAME_AUTHOR_AVATAR));
                String date = c.getString(c.getColumnIndexOrThrow(PostEntry.COLUMN_NAME_DATE));
                String body = c.getString(c.getColumnIndexOrThrow(PostEntry.COLUMN_NAME_BODY));
                String url = c.getString(c.getColumnIndexOrThrow(PostEntry.COLUMN_NAME_URL));
                String voteCount = c.getString(c.getColumnIndexOrThrow(PostEntry.COLUMN_NAME_VOTE_COUNT));
                String commentCount = c.getString(c.getColumnIndexOrThrow(PostEntry.COLUMN_NAME_COMMENT_COUNT));

                PostModel post = new PostModel(Integer.valueOf(postId));
                post.setTag(tag);
                post.setAuthor(author);
                post.setAuthorAvatar(authorAvatar);
                post.setDate(date);
                post.setBody(body);
                post.setUrl(url);
                post.setVoteCount(Integer.valueOf(voteCount));
                post.setCommentCount(Integer.valueOf(commentCount));

                if (post.getCommentCount() > 0) {
                    post.setComments(getComments(postId));
                }

                List<EmbedModel> embeds = getEmbeds(postId);
                if (!embeds.isEmpty()) {
                    post.setEmbed(embeds.get(0));
                }
                posts.add(post);
            }
        }
        if (c != null) {
            c.close();
        }

//        db.close();

        if (posts.isEmpty()) {
            callback.onDataNotAvailable();
        } else {
            callback.onDataLoaded(posts, false);
        }

    }

    private List<CommentModel> getComments(@NonNull String originId) {
        List<CommentModel> comments = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                CommentEntry.COLUMN_NAME_ENTRY_ID,
                CommentEntry.COLUMN_NAME_AUTHOR,
                CommentEntry.COLUMN_NAME_AUTHOR_AVATAR,
                CommentEntry.COLUMN_NAME_DATE,
                CommentEntry.COLUMN_NAME_BODY,
                CommentEntry.COLUMN_NAME_VOTE_COUNT,
                CommentEntry.COLUMN_NAME_USER_VOTE,
                CommentEntry.COLUMN_NAME_TYPE,
                CommentEntry.COLUMN_NAME_POST_ENTRY_ID
        };
        String selection = CommentEntry.COLUMN_NAME_POST_ENTRY_ID + " LIKE ?";
        String[] selectionArgs = {originId};

        Cursor c = db.query(
                CommentEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                String commentId = c.getString(c.getColumnIndexOrThrow(CommentEntry.COLUMN_NAME_ENTRY_ID));
                String author = c.getString(c.getColumnIndexOrThrow(CommentEntry.COLUMN_NAME_AUTHOR));
                String authorAvatar = c.getString(c.getColumnIndexOrThrow(CommentEntry.COLUMN_NAME_AUTHOR_AVATAR));
                String date = c.getString(c.getColumnIndexOrThrow(CommentEntry.COLUMN_NAME_DATE));
                String body = c.getString(c.getColumnIndexOrThrow(CommentEntry.COLUMN_NAME_BODY));
                String voteCount = c.getString(c.getColumnIndexOrThrow(CommentEntry.COLUMN_NAME_VOTE_COUNT));
                String userVote = c.getString(c.getColumnIndexOrThrow(CommentEntry.COLUMN_NAME_USER_VOTE));
                String type = c.getString(c.getColumnIndexOrThrow(CommentEntry.COLUMN_NAME_TYPE));

                CommentModel model = new CommentModel();
                model.setCommentId(Integer.valueOf(commentId));
                model.setAuthor(author);
                model.setAuthorAvatar(authorAvatar);
                model.setDate(date);
                model.setBody(body);
                model.setVoteCount(Integer.valueOf(voteCount));
                model.setUserVote(Integer.valueOf(userVote));
                model.setType(type);

                List<EmbedModel> embeds = getEmbeds(commentId);
                if (!embeds.isEmpty()) {
                    model.setEmbed(embeds.get(0));
                }
                model.setPostEntryId(Integer.valueOf(originId));
                comments.add(model);
            }
        }
        if (c != null) {
            c.close();
        }

//        db.close();

        return comments;
    }

    private List<EmbedModel> getEmbeds(@NonNull String originId) {
        List<EmbedModel> embeds = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                EmbedEntry.COLUMN_NAME_ENTRY_ID,
                EmbedEntry.COLUMN_NAME_TYPE,
                EmbedEntry.COLUMN_NAME_PREVIEW_URL,
                EmbedEntry.COLUMN_NAME_URL
        };
        String selection = EmbedEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?";
        String[] selectionArgs = {originId};

        Cursor c = db.query(
                EmbedEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                String id = c.getString(c.getColumnIndexOrThrow(EmbedEntry.COLUMN_NAME_ENTRY_ID));
                String type = c.getString(c.getColumnIndexOrThrow(EmbedEntry.COLUMN_NAME_TYPE));
                String previewUrl = c.getString(c.getColumnIndexOrThrow(EmbedEntry.COLUMN_NAME_PREVIEW_URL));
                String url = c.getString(c.getColumnIndexOrThrow(EmbedEntry.COLUMN_NAME_URL));

                EmbedModel embedModel = new EmbedModel(id);
                embedModel.setType(type);
                embedModel.setPreviewUrl(previewUrl);
                embedModel.setUrl(url);
                embeds.add(embedModel);
            }
        }
        if (c != null) {
            c.close();
        }

//        db.close();

        return embeds;
    }

    @Override
    public void savePosts(List<PostModel> posts) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            for (PostModel post : posts) {
                for (CommentModel c : post.getComments()) {
                    if (c.getEmbed() != null) {
                        db.insert(PostsPersistenceContract.EmbedEntry.TABLE_NAME, null,
                                PostsPersistenceContract.EmbedEntry.put(c.getEmbed(), c.getCommentId()));
                    }
                    db.insert(CommentEntry.TABLE_NAME, null,
                            CommentEntry.put(c));
                }

                if (post.getEmbed() != null) {
                    db.insert(PostsPersistenceContract.EmbedEntry.TABLE_NAME, null,
                            PostsPersistenceContract.EmbedEntry.put(post.getEmbed(), post.getPostId()));
                }
                db.insert(PostsPersistenceContract.PostEntry.TABLE_NAME, null,
                        PostsPersistenceContract.PostEntry.put(post));

            }
        } finally {
//            db.close();

        }
    }

    @Override public void allowCache(boolean allow) {

    }

}
