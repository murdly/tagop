package com.akarbowy.tagop.presentation.posts.adapters.text;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.akarbowy.tagop.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TextSectionView extends FrameLayout {

    @BindView(R.id.text_content) TextView contentView;

    public TextSectionView(Context context) {
        super(context);
        ButterKnife.bind(this, inflate(context, R.layout.item_post_text_section, this));
    }


    public void setContent(String content) {
        contentView.setText(content);
    }

}
