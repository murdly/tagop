package com.akarbowy.tagop.presentation.posts.adapters.other;

import android.content.Context;
import android.widget.FrameLayout;

import com.akarbowy.tagop.R;

public class SeparatorView extends FrameLayout {

    public SeparatorView(Context context) {
        super(context);
        inflate(context, R.layout.item_post_separator, this);
    }
}
