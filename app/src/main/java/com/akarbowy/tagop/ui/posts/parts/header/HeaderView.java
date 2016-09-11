package com.akarbowy.tagop.ui.posts.parts.header;

import android.content.Context;
import android.net.Uri;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.akarbowy.tagop.R;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HeaderView extends LinearLayout {

    @BindView(R.id.drawee_avatar) SimpleDraweeView avatarView;
    @BindView(R.id.text_author) TextView authorView;
    @BindView(R.id.text_date) TextView dateView;

    public HeaderView(Context context) {
        super(context);
        ButterKnife.bind(this, inflate(context, R.layout.item_post_header, this));
    }

    public void setAvatar(String url) {
        avatarView.setImageURI(Uri.parse(url));
    }

    public void setTitle(String author) {
        authorView.setText(author);
    }

    public void setDate(String date) {
        dateView.setText(date);
    }
}
