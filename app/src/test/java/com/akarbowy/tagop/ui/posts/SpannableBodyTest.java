package com.akarbowy.tagop.ui.posts;

import android.text.Spannable;
import android.text.style.URLSpan;

import com.akarbowy.tagop.ui.posts.parts.SpannableBody;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class SpannableBodyTest {

    private SpannableBody builderUnderTest;
    private String tagHtml;
    private String tagPlain;
    private String mentionHtml;
    private String mentionPlain;
    private String urlTitled;
    private String urlRaw;
    private String spoiler;
    private String space = " ";
    private String plain = " asd asdd ";
    private String spoilerWithUrlInside;
    private String urlWithHashCharacterInvolved;

    @Before
    public void setUp() throws Exception {
        tagHtml = "#<a href=\"#taghtml\">taghtml</a>";
        tagPlain = "#tagplain";
        urlWithHashCharacterInvolved = "<a href=\"http://wp.pl/#hash\" rel=\"nofollow\">http://wp.pl/#hash</a><br />\n";

        mentionHtml = "@<a href=\"@mentionhtml\">mentionhtml</a>";
        mentionPlain = "@mentionplain";

        urlTitled = "<a href=\"http://www.wykop.pl\" rel=\"nofollow\">Opis link</a><br />\n";
        urlRaw = "<a href=\"http://wp.pl\" rel=\"nofollow\">http://wp.pl</a><br />\n";

        spoiler = "<code class=\"dnone\">  Ukryty tekst...<br />\n</code><br />\n";
        spoilerWithUrlInside = "<code class=\"dnone\">" + urlRaw  +"<br />\n</code><br />\n";


        builderUnderTest = new SpannableBody();
    }

    @Test
    public void givenSingleTagHtml_thenSingleLocalUrlSpan() {
        String txt = tagHtml;

        Spannable s = builderUnderTest.setHtmlBodyText(txt).create().getSpannable();
        SpannableBody.LocalUrlSpan[] spans = s.getSpans(0, s.length(), SpannableBody.LocalUrlSpan.class);

        Assert.assertEquals(spans.length, 1);
    }

    @Test
    public void givenMultipleTagHtml_thenMultipleLocalUrlSpan() {
        String txt = tagHtml.concat(space).concat(tagHtml);

        Spannable s = builderUnderTest.setHtmlBodyText(txt).create().getSpannable();
        SpannableBody.LocalUrlSpan[] spans = s.getSpans(0, s.length(), SpannableBody.LocalUrlSpan.class);

        Assert.assertEquals(spans.length, 2);
    }

    @Test
    public void givenSingleTagPlain_thenZeroLocalUrlSpan() {
        String txt = tagPlain;

        Spannable s = builderUnderTest.setHtmlBodyText(txt).create().getSpannable();
        SpannableBody.LocalUrlSpan[] spans = s.getSpans(0, s.length(), SpannableBody.LocalUrlSpan.class);

        Assert.assertEquals(spans.length, 0);
    }

    @Test
    public void givenTagHtmlAndTagPlain_thenSingleLocalUrlSpan() {
        String txt = tagHtml.concat(space).concat(tagPlain);

        Spannable s = builderUnderTest.setHtmlBodyText(txt).create().getSpannable();
        SpannableBody.LocalUrlSpan[] spans = s.getSpans(0, s.length(), SpannableBody.LocalUrlSpan.class);

        Assert.assertEquals(spans.length, 1);
    }

    @Test
    public void givenSingleMentionHtml_thenZeroLocalUrlSpan() {
        String txt = mentionHtml;

        Spannable s = builderUnderTest.setHtmlBodyText(txt).create().getSpannable();
        SpannableBody.LocalUrlSpan[] spans = s.getSpans(0, s.length(), SpannableBody.LocalUrlSpan.class);

        Assert.assertEquals(spans.length, 0);
    }

    @Test
    public void givenMultipleMentionHtml_thenZeroLocalUrlSpan() {
        String txt = mentionHtml.concat(space).concat(mentionHtml);

        Spannable s = builderUnderTest.setHtmlBodyText(txt).create().getSpannable();
        SpannableBody.LocalUrlSpan[] spans = s.getSpans(0, s.length(), SpannableBody.LocalUrlSpan.class);

        Assert.assertEquals(spans.length, 0);
    }

    @Test
    public void givenSingleMentionPlain_thenZeroLocalUrlSpan() {
        String txt = mentionPlain;

        Spannable s = builderUnderTest.setHtmlBodyText(txt).create().getSpannable();
        SpannableBody.LocalUrlSpan[] spans = s.getSpans(0, s.length(), SpannableBody.LocalUrlSpan.class);

        Assert.assertEquals(spans.length, 0);
    }

    @Test
    public void givenMentionHtmlAndMentionPlain_thenSingleLocalUrlSpan() {
        String txt = mentionHtml.concat(space).concat(mentionPlain);

        Spannable s = builderUnderTest.setHtmlBodyText(txt).create().getSpannable();
        SpannableBody.LocalUrlSpan[] spans = s.getSpans(0, s.length(), SpannableBody.LocalUrlSpan.class);

        Assert.assertEquals(spans.length, 0);
    }

    @Test
    public void givenUrlWithHashCharacterInvolved_thenUrlSpan() {
        String txt = urlWithHashCharacterInvolved;

        Spannable s = builderUnderTest.setHtmlBodyText(txt).create().getSpannable();
        URLSpan[] spans = s.getSpans(0, s.length(), URLSpan.class);

        Assert.assertEquals(spans.length, 1);
    }
}