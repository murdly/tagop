package com.akarbowy.tagop.ui.posts.parts.text;

import com.akarbowy.tagop.network.model.TagEntry;
import com.akarbowy.tagop.parto.Binder;
import com.akarbowy.tagop.ui.posts.SpannableBody;

public class TextSectionBinder implements Binder<TextSectionView> {
    private TagEntry tagEntry;
    private SpannableBody body;

    public TextSectionBinder(TagEntry viewObject) {
        tagEntry = viewObject;
    }

    @Override public void prepare(final TextSectionView view) {
        body = new SpannableBody().setHtmlBodyText(tagEntry.body)
                .setOnLinkClickListener(new SpannableBody.ClickableListener() {
                    @Override public void onSpoilerClick(SpannableBody unspoiled) {
                        view.setContent(unspoiled.getSpannable());
                    }
                }).create();
    }

    @Override public void bind(TextSectionView view) {
        view.setContent(body.getSpannable());
    }
}
