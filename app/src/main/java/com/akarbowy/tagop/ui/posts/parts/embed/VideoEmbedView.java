package com.akarbowy.tagop.ui.posts.parts.embed;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;

import com.akarbowy.tagop.R;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoEmbedView extends FrameLayout {

    @BindView(R.id.drawee_embed_image) SimpleDraweeView imageView;
    @BindView(R.id.video_play_frame) FrameLayout playFrame;

    public VideoEmbedView(Context context) {
        super(context);
        init(context);
    }

    public void init(Context context) {
        ButterKnife.bind(this, inflate(context, R.layout.item_post_embed_video, this));
        setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT));
    }

    public void setPreview(String url) {
        imageView.setImageURI(Uri.parse(url));
    }

    public void setOnPlayVideoClickListener(OnClickListener listener) {
        playFrame.setOnClickListener(listener);
    }
}
