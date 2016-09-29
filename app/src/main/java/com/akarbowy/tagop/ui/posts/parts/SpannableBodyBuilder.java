package com.akarbowy.tagop.ui.posts.parts;

import android.annotation.SuppressLint;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpannableBodyBuilder {

    private static final int MENTION = 0;
    private static final int TAG = 1;
    private static final int HYPERLINK = 2;
    private static final int SPOILER = 3;

    private final SpanMeta mentionMeta;
    private final SpanMeta tagMeta;
    private final SpanMeta hyperlinkMeta;
    private final SpanMeta spoilerMeta;

    private final String htmlBodyText;
    private ClickableListener listener;
    private SpannableStringBuilder spannable;

    public SpannableBodyBuilder(String htmlBodyText) {
        this.htmlBodyText = htmlBodyText;

        mentionMeta = new SpanMeta(MENTION, Pattern.compile("(@[a-zA-Z0-9_]+)"), 1);
        tagMeta = new SpanMeta(TAG, Pattern.compile("(#[a-zA-Z0-9_-]+)"), 1);
        hyperlinkMeta = new SpanMeta(HYPERLINK, Pattern.compile("([Hh][tT][tT][pP][sS]?:\\/\\/[^ ,'\">\\]\\)]*[^\\. ,'\">\\]\\)])"));
        spoilerMeta = new SpanMeta(SPOILER, Pattern.compile("<code class=\"dnone\">(.+?)</code>", Pattern.DOTALL));
    }

    public Spannable build() {
        spannable = new SpannableStringBuilder(Html.fromHtml(htmlBodyText));

        removeUrlSpans(spannable, mentionMeta);
        replaceUrlSpans(spannable, tagMeta);
        setSpoilerSpan(spannable, htmlBodyText, spoilerMeta);

        return spannable;
    }

    private void removeUrlSpans(Spannable s, SpanMeta... toRemove) {
        for (SpanMeta m : toRemove) {
            for (URLSpan span : findMatchingSpans(s, m.pattern)) {
                s.removeSpan(span);
            }
        }
    }

    private void replaceUrlSpans(Spannable s, SpanMeta... toReplace) {
        for (SpanMeta m : toReplace) {
            for (URLSpan span : findMatchingSpans(s, m.pattern)) {
                setLocalUrlSpan(s, m, span);
                s.removeSpan(span);
            }
        }
    }

    private List<URLSpan> findMatchingSpans(Spannable s, Pattern pattern) {
        List<URLSpan> spans = new ArrayList<>();
        Matcher m = pattern.matcher(s);
        while (m.find()) {
            int start = m.start();
            int end = m.end();

            spans.add(s.getSpans(start, end, URLSpan.class)[0]);
        }

        return spans;
    }

    private void setLocalUrlSpan(Spannable s, SpanMeta meta, URLSpan span) {
        int start = s.getSpanStart(span);
        int end = s.getSpanEnd(span);
        int flags = s.getSpanFlags(span);

        LocalUrlSpan localUrlSpan = new LocalUrlSpan(span.getURL());
        localUrlSpan.meta = meta;
        localUrlSpan.text = s.subSequence(start - meta.startCharOffset, end).toString();

        s.setSpan(localUrlSpan, start - meta.startCharOffset, end, flags);
    }

    private void setSpoilerSpan(SpannableStringBuilder s, String text, SpanMeta meta) {
        Matcher m = meta.pattern.matcher(text);
        while (m.find()) {
            String textToReplace = Html.fromHtml(m.group()).toString();
            int ttrStart = s.toString().indexOf(textToReplace);

            SpoilerSpan spoilerSpan = new SpoilerSpan();
            spoilerSpan.hiddenText = textToReplace;
            spoilerSpan.meta = meta;

            s.delete(ttrStart, ttrStart + textToReplace.length());
            s.insert(ttrStart, spoilerSpan.foregroundText);
            s.setSpan(spoilerSpan, ttrStart, ttrStart + spoilerSpan.foregroundText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

    }

    public SpannableBodyBuilder setOnLinkClickListener(ClickableListener listener) {
        this.listener = listener;
        return this;
    }

    private Spannable unspoiler(SpoilerSpan span) {
        int start = spannable.getSpanStart(span);
        spannable.removeSpan(this);
        spannable.delete(start, start + span.foregroundText.length());
        spannable.insert(start, span.hiddenText);
        return spannable;
    }

    public interface ClickableListener {
        void onSpoilerClick(Spannable unspoiled);
    }

    @SuppressLint("ParcelCreator")
    private class LocalUrlSpan extends URLSpan {
        String text;
        SpanMeta meta;

        public LocalUrlSpan(String url) {
            super("localUrlSpan://" + url);
        }

        @Override public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setUnderlineText(meta.underline);
            ds.setFakeBoldText(true);
        }
    }

    private class SpoilerSpan extends ClickableSpan {
        final String foregroundText = "[pokaż spoiler]";
        String hiddenText;
        SpanMeta meta;

        @Override public void onClick(View view) {
            if (listener != null) {
                Spannable unspoiled = unspoiler(this);
                listener.onSpoilerClick(unspoiled);
            }
        }

        @Override public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setUnderlineText(meta.underline);
            ds.setFakeBoldText(true);
        }
    }

    private class SpanMeta {
        int type;
        Pattern pattern;
        int startCharOffset = 0;
        boolean underline = false;

        SpanMeta(int type, Pattern pattern) {
            this(type, pattern, 0, false);
        }

        SpanMeta(int type, Pattern pattern, int startCharOffset) {
            this(type, pattern, startCharOffset, false);
        }

        SpanMeta(int type, Pattern pattern, int startCharOffset, boolean underline) {
            this.type = type;
            this.pattern = pattern;
            this.startCharOffset = startCharOffset;
            this.underline = underline;
        }
    }
}
