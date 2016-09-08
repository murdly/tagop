package com.akarbowy.tagop.presentation.posts.adapters.header;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.akarbowy.tagop.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HeaderView extends LinearLayout {

    @BindView(R.id.image_avatar) ImageView avatarView;
    @BindView(R.id.text_author) TextView authorView;
    @BindView(R.id.text_date) TextView dateView;

    public HeaderView(Context context) {
        super(context);
        ButterKnife.bind(this, inflate(context, R.layout.item_post_header, this));
    }

    public void setAvatar() {

    }

    public void setTitle(String author) {
        authorView.setText(author);
    }

    public void setDate(String date) {
        dateView.setText(date);
    }
}
