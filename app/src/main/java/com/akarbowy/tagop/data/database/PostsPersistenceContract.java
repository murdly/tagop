package com.akarbowy.tagop.data.database;


import android.content.ContentValues;
import android.provider.BaseColumns;

import com.akarbowy.tagop.data.database.model.CommentModel;
import com.akarbowy.tagop.data.database.model.EmbedModel;
import com.akarbowy.tagop.data.database.model.PostModel;

public class PostsPersistenceContract {

    private PostsPersistenceContract() {
    }

    public static abstract class PostEntry implements BaseColumns {
        public static final String TABLE_NAME = "post";
        public static final String COLUMN_NAME_ENTRY_ID = "entry_id";
        public static final String COLUMN_NAME_AUTHOR = "author";
        public static final String COLUMN_NAME_AUTHOR_AVATAR = "author_avatar";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_BODY = "body";
        public static final String COLUMN_NAME_URL = "url";
        public static final String COLUMN_NAME_VOTE_COUNT = "vote_count";
        public static final String COLUMN_NAME_COMMENT_COUNT = "comment_count";
        public static final String COLUMN_NAME_TAG_ENTRY_TITLE = "tag_entry_id";

        public static ContentValues put(PostModel post) {
            ContentValues values = new ContentValues();
            values.put(PostsPersistenceContract.PostEntry.COLUMN_NAME_ENTRY_ID, post.getPostId());
            values.put(PostEntry.COLUMN_NAME_AUTHOR, post.getAuthor());
            values.put(PostEntry.COLUMN_NAME_AUTHOR_AVATAR, post.getAuthorAvatar());
            values.put(PostEntry.COLUMN_NAME_DATE, post.getDate());
            values.put(PostEntry.COLUMN_NAME_BODY, post.getBody());
            values.put(PostEntry.COLUMN_NAME_URL, post.getUrl());
            values.put(PostEntry.COLUMN_NAME_VOTE_COUNT, post.getVoteCount());
            values.put(PostEntry.COLUMN_NAME_COMMENT_COUNT, post.getCommentCount());
            values.put(PostEntry.COLUMN_NAME_TAG_ENTRY_TITLE, post.getTag().getTitle());
            return values;
        }
    }

    public static abstract class EmbedEntry implements BaseColumns {
        public static final String TABLE_NAME = "embed";
        public static final String COLUMN_NAME_ENTRY_ID = "entry_id";
        public static final String COLUMN_NAME_TYPE = "type";
        public static final String COLUMN_NAME_PREVIEW_URL = "preview_url";
        public static final String COLUMN_NAME_URL = "url";

        public static ContentValues put(EmbedModel embed, Integer entryId) {
            ContentValues values = new ContentValues();
            values.put(PostsPersistenceContract.EmbedEntry.COLUMN_NAME_ENTRY_ID, entryId);
            values.put(EmbedEntry.COLUMN_NAME_TYPE, embed.getType());
            values.put(EmbedEntry.COLUMN_NAME_PREVIEW_URL, embed.getPreviewUrl());
            values.put(EmbedEntry.COLUMN_NAME_URL, embed.getUrl());
            return values;
        }
    }

    public static abstract class CommentEntry implements BaseColumns {
        public static final String TABLE_NAME = "comment";
        public static final String COLUMN_NAME_ENTRY_ID = "entry_id";
        public static final String COLUMN_NAME_AUTHOR = "author";
        public static final String COLUMN_NAME_AUTHOR_AVATAR = "author_avatar";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_BODY = "body";
        public static final String COLUMN_NAME_VOTE_COUNT = "vote_count";
        public static final String COLUMN_NAME_USER_VOTE = "user_vote";
        public static final String COLUMN_NAME_TYPE = "type";
        public static final String COLUMN_NAME_POST_ENTRY_ID = "post_entry_id";

        public static ContentValues put(CommentModel comment) {
            ContentValues values = new ContentValues();
            values.put(PostsPersistenceContract.CommentEntry.COLUMN_NAME_ENTRY_ID, comment.getCommentId());
            values.put(CommentEntry.COLUMN_NAME_AUTHOR, comment.getAuthor());
            values.put(CommentEntry.COLUMN_NAME_AUTHOR_AVATAR, comment.getAuthorAvatar());
            values.put(CommentEntry.COLUMN_NAME_DATE, comment.getDate());
            values.put(CommentEntry.COLUMN_NAME_BODY, comment.getBody());
            values.put(CommentEntry.COLUMN_NAME_VOTE_COUNT, comment.getVoteCount());
            values.put(CommentEntry.COLUMN_NAME_USER_VOTE, comment.getUserVote());
            values.put(CommentEntry.COLUMN_NAME_TYPE, comment.getType());
            values.put(CommentEntry.COLUMN_NAME_POST_ENTRY_ID, comment.getPostEntryId());
            return values;
        }
    }
}
