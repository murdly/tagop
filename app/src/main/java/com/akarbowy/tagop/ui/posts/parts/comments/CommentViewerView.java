package com.akarbowy.tagop.ui.posts.parts.comments;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;

import com.akarbowy.tagop.R;
import com.akarbowy.tagop.data.database.model.CommentModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentViewerView extends RelativeLayout {

    @BindView(R.id.recycler_comments) RecyclerView commentsRecycler;

    private CommentsAdapter adapter;

    public CommentViewerView(Context context) {
        super(context);

        ButterKnife.bind(this, inflate(context, R.layout.view_comment_viewer, this));
        adapter = new CommentsAdapter();
        commentsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        commentsRecycler.setHasFixedSize(true);
        commentsRecycler.setAdapter(adapter);
    }

    public void setComments(List<CommentModel> comments) {
        adapter.setItems(new ArrayList<>(comments));
    }
}
