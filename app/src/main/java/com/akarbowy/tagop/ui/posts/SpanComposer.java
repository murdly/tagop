package com.akarbowy.tagop.ui.posts;

import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.URLSpan;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class SpanComposer {
    private final SpannableBody.SpanFactory spanFactory;

    SpanComposer(SpannableBody.SpanFactory spanFactory) {
        this.spanFactory = spanFactory;
    }

    SpannableStringBuilder compose(String htmlText) {
        SpannableStringBuilder workerSpannable = new SpannableStringBuilder(Html.fromHtml(htmlText));

        removeUrlSpans(workerSpannable, Type.MENTION.pattern);
        replaceUrlSpans(workerSpannable, Type.TAG.pattern);
        setSpoilerSpan(workerSpannable, htmlText, Type.SPOILER.pattern);

        return workerSpannable;
    }

    SpannableStringBuilder composeUnspoiled(SpannableStringBuilder spannable, SpannableBody.SpoilerSpan span) {
        int start = spannable.getSpanStart(span);
        spannable.removeSpan(span);
        spannable.delete(start, start + span.foregroundText.length());

        SpannableStringBuilder unspoiled = compose(span.hiddenText);
        spannable.insert(start, unspoiled);
        return spannable;
    }


    private void removeUrlSpans(Spannable s, Pattern... toRemove) {
        for (Pattern p : toRemove) {
            for (URLSpan span : findMatchingSpans(s, p)) {
                s.removeSpan(span);
            }
        }
    }

    private void replaceUrlSpans(Spannable s, Pattern... toReplace) {
        for (Pattern p : toReplace) {
            for (URLSpan fromSpan : findMatchingSpans(s, p)) {
                setLocalUrlSpan(s, fromSpan);
                s.removeSpan(fromSpan);
            }
        }
    }

    private void setLocalUrlSpan(Spannable s, URLSpan span) {
        int start = s.getSpanStart(span);
        int end = s.getSpanEnd(span);
        int flags = s.getSpanFlags(span);

        SpannableBody.LocalUrlSpan localUrlSpan = spanFactory.createLocalUrlSpan(span.getURL());
        localUrlSpan.text = s.subSequence(start - localUrlSpan.startCharOffset, end).toString();

        s.setSpan(localUrlSpan, start - localUrlSpan.startCharOffset, end, flags);
    }

    private void setSpoilerSpan(SpannableStringBuilder s, String text, Pattern pattern) {
        Matcher m = pattern.matcher(text);
        while (m.find()) {
            String textInsideCodeTag = m.group(1);
            SpannableBody.SpoilerSpan spoilerSpan = spanFactory.createSpoilerSpan(textInsideCodeTag);

            String textFromSpannable = Html.fromHtml(m.group(0)).toString();
            int ttrStart = s.toString().indexOf(textFromSpannable);
            s.delete(ttrStart, ttrStart + textFromSpannable.length());
            s.insert(ttrStart, spoilerSpan.foregroundText);
            s.setSpan(spoilerSpan, ttrStart, ttrStart + spoilerSpan.foregroundText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

    }

    private List<URLSpan> findMatchingSpans(Spannable s, Pattern pattern) {
        List<URLSpan> spans = new ArrayList<>();
        Matcher m = pattern.matcher(s);
        while (m.find()) {
            int start = m.start();
            int end = m.end();

            URLSpan[] spanFounded = s.getSpans(start, end, URLSpan.class);
            if (spanFounded.length > 0) {
                spans.add(spanFounded[0]);
            }
        }

        return spans;
    }

    public enum Type {
        MENTION(Pattern.compile("((\\s|^)@[a-zA-Z0-9_]+)")),
        TAG(Pattern.compile("((\\s|^)#[a-zA-Z0-9_-]+)")),
        HYPERLINK(Pattern.compile("([Hh][tT][tT][pP][sS]?:\\/\\/[^ ,'\">\\]\\)]*[^\\. ,'\">\\]\\)])")),
        SPOILER(Pattern.compile("<code class=\"dnone\">(.+?)</code>", Pattern.DOTALL));

        private final Pattern pattern;

        Type(Pattern pattern) {
            this.pattern = pattern;
        }
    }
}