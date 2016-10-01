package com.akarbowy.tagop.ui.posts.parts.embed;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.akarbowy.tagop.R;
import com.akarbowy.tagop.utils.ViewPropertiesUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.stfalcon.frescoimageviewer.ImageViewer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EmbedView extends FrameLayout {

    @BindView(R.id.drawee_embed_image) SimpleDraweeView imageView;
    @BindView(R.id.video_play_frame) FrameLayout videoFrame;

    private String viewerUrl;

    public EmbedView(Context context) {
        super(context);
        init(context);
    }

    //TODO remove when group parts implemented
    public EmbedView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context) {
        ButterKnife.bind(this, inflate(context, R.layout.item_post_embed, this));
        setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT));
    }

    public void setPreview(String url) {
        imageView.setImageURI(Uri.parse(url));
    }

    public void setViewerUrl(String url) {
        this.viewerUrl = url;
    }

    public void setVideoFrameVisibility(boolean visible) {
        ButterKnife.apply(videoFrame, ViewPropertiesUtil.VISIBILITY, visible);
    }

    @OnClick(R.id.drawee_embed_image)
    public void onViewImage(View view) {
        new ImageViewer.Builder(view.getContext(), new String[]{viewerUrl}).show();
    }

    @OnClick(R.id.video_play_frame)
    public void onPlayVideo(View view) {
        view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(viewerUrl)));
    }
}
