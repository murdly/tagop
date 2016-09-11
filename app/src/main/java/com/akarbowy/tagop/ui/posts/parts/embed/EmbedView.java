package com.akarbowy.tagop.ui.posts.parts.embed;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.akarbowy.tagop.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.stfalcon.frescoimageviewer.ImageViewer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * extending the view with RelativeLayout is a fix to "auto" match parent width
 */
public class EmbedView extends LinearLayout {

    @BindView(R.id.drawee_embed_image) SimpleDraweeView imageView;

    private String viewerUrl;

    public EmbedView(Context context) {
        super(context);
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

    @OnClick(R.id.drawee_embed_image)
    public void onViewImage(View view) {
        new ImageViewer.Builder(view.getContext(), new String[]{viewerUrl}).show();
    }
}
