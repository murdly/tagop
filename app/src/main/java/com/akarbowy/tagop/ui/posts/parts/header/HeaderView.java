package com.akarbowy.tagop.ui.posts.parts.header;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.akarbowy.tagop.R;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HeaderView extends RelativeLayout {

    protected MoreActionsBottomSheet moreActionsBottomSheet;

    @BindView(R.id.drawee_avatar) SimpleDraweeView avatarView;
    @BindView(R.id.text_author) TextView authorView;
    @BindView(R.id.text_date) TextView dateView;
    @BindView(R.id.view_more) ImageView moreView;

    public HeaderView(Context context) {
        super(context);
        ButterKnife.bind(this, inflate(context, R.layout.item_post_header, this));
        moreActionsBottomSheet = new MoreActionsBottomSheet(context);
    }

    @OnClick(R.id.view_more)
    public void onMoreClickListener() {
        moreActionsBottomSheet.show();
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

    public void setOnMoreActionsListener(MoreActionsBottomSheet.Callback listener) {
        moreActionsBottomSheet.setCallback(listener);
    }

}
