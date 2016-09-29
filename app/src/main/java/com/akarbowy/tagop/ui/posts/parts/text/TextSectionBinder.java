package com.akarbowy.tagop.ui.posts.parts.text;

import android.text.Spannable;

import com.akarbowy.tagop.network.model.TagEntry;
import com.akarbowy.tagop.parto.Binder;
import com.akarbowy.tagop.ui.posts.parts.SpannableBodyBuilder;

public class TextSectionBinder implements Binder<TextSectionView> {
    private TagEntry tagEntry;
    private Spannable body;

    public TextSectionBinder(TagEntry viewObject) {
        tagEntry = viewObject;
    }

    @Override public void prepare(final TextSectionView view) {
        final SpannableBodyBuilder bodyBuilder = new SpannableBodyBuilder(tagEntry.body);
        body = bodyBuilder.setOnLinkClickListener(new SpannableBodyBuilder.ClickableListener() {
            @Override public void onSpoilerClick(Spannable unspoiled) {
                body = unspoiled;
                view.setContent(body);
            }
        }).build();
    }

    @Override public void bind(TextSectionView view) {
        view.setContent(body);
    }
}
