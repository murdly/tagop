package com.akarbowy.tagop.ui.posts;

import android.annotation.SuppressLint;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.View;

public class SpannableBody {

    private String htmlBodyText;
    private ClickableListener listener;
    private SpannableStringBuilder spannable;
    private SpanComposer composer;

    public SpannableBody() {
        composer = new SpanComposer(new SpanFactory());
    }

    public SpannableBody setHtmlBodyText(String htmlBodyText) {
        this.htmlBodyText = htmlBodyText;
        return this;
    }

    public SpannableBody setOnLinkClickListener(ClickableListener listener) {
        this.listener = listener;
        return this;
    }

    public SpannableBody create() {
        spannable = composer.compose(htmlBodyText);
        return this;
    }

    public SpannableBody unspoiler(SpoilerSpan span) {
        spannable = composer.composeUnspoiled(spannable, span);
        return this;
    }

    public Spannable getSpannable() {
        return spannable;
    }

    public interface ClickableListener {
        void onSpoilerClick(SpannableBody unspoiled);
    }

    class SpanFactory {
        SpoilerSpan createSpoilerSpan(String hiddenText) {return new SpoilerSpan(hiddenText);}

        LocalUrlSpan createLocalUrlSpan(String url) {return new LocalUrlSpan(url);}
    }

    @SuppressLint("ParcelCreator")
    class LocalUrlSpan extends URLSpan {
        final int startCharOffset = 1;
        String text;

        LocalUrlSpan(String url) {
            super("localUrlSpan://" + url);
        }

        public void setText(String text) {
            this.text = text;
        }

        @Override public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setUnderlineText(false);
            ds.setFakeBoldText(true);
        }
    }

    class SpoilerSpan extends ClickableSpan {
        final String foregroundText = "[pokaż spoiler]";
        final String hiddenText;

        private SpoilerSpan(String hiddenText) {
            this.hiddenText = hiddenText;
        }

        @Override public void onClick(View view) {
            if (listener != null) {
                SpannableBody unspoiled = unspoiler(this);
                listener.onSpoilerClick(unspoiled);
            }
        }

        @Override public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setUnderlineText(false);
            ds.setFakeBoldText(true);
        }
    }
}
