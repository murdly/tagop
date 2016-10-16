package com.akarbowy.tagop.ui.posts.parts.text;

import com.akarbowy.partdefiner.Binder;
import com.akarbowy.tagop.data.network.model.TagEntry;
import com.akarbowy.tagop.ui.posts.parts.SpannableBody;

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

    @Override public void unbind(TextSectionView view) {

    }
}
