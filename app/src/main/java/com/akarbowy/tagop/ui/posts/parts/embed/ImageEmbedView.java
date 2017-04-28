package com.akarbowy.tagop.ui.posts.parts.embed;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;

import com.akarbowy.tagop.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.animated.base.AnimatedDrawable;
import com.facebook.imagepipeline.image.ImageInfo;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageEmbedView extends FrameLayout {

    @BindView(R.id.drawee_embed_image) SimpleDraweeView imageView;

    private String previewUrl;
    private ValueAnimator animator;

    public ImageEmbedView(Context context) {
        super(context);
        init(context);
    }

    public void init(Context context) {
        ButterKnife.bind(this, inflate(context, R.layout.item_post_embed_image, this));
        setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT));
    }

    public void setPreviewUrl(String url) {
        this.previewUrl = url;
        showPreview(url);
    }

    public void setOnPreviewClickListener(OnClickListener listener) {
        imageView.setOnClickListener(listener);
    }

    private void showPreview(String url) {
        imageView.setImageURI(Uri.parse(url));
    }

    public void startGif(final String url) {
        if (animator != null && animator.isRunning()) {
            animator.end();
            return;
        }

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(Uri.parse(url))
                .setControllerListener(new BaseControllerListener<ImageInfo>() {
                    @Override public void onFinalImageSet(String id, @Nullable ImageInfo imageInfo, @Nullable Animatable animatable) {
                        super.onFinalImageSet(id, imageInfo, animatable);
                        if (animatable != null) {
                            animator = ((AnimatedDrawable) animatable).createValueAnimator();
                            animator.addListener(new Animator.AnimatorListener() {
                                @Override public void onAnimationStart(Animator animator) {

                                }

                                @Override public void onAnimationEnd(Animator animator) {
                                    showPreview(previewUrl);
                                }

                                @Override public void onAnimationCancel(Animator animator) {

                                }

                                @Override public void onAnimationRepeat(Animator animator) {

                                }
                            });

                            animator.setRepeatCount(0);
                            animator.start();
                        }
                    }
                })
                .build();

        imageView.setController(controller);
    }

}
