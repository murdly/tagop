package com.akarbowy.tagop.ui.posts.parts.header;

import android.text.format.DateUtils;

import com.akarbowy.tagop.network.model.TagEntry;
import com.akarbowy.tagop.parto.Binder;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import timber.log.Timber;

public class HeaderBinder implements Binder<HeaderView> {
    private TagEntry tagEntry;

    public HeaderBinder(TagEntry viewObject) {
        tagEntry = viewObject;
    }

    @Override public void prepare(HeaderView view) {

    }

    @Override public void bind(HeaderView view) {
        view.setAvatar(tagEntry.authorAvatar);
        view.setTitle(tagEntry.author);


        try {
            long dateInMillis = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(tagEntry.date).getTime();
            String relativeDate = DateUtils.getRelativeTimeSpanString(dateInMillis).toString();
            view.setDate(relativeDate);
        } catch (ParseException e) {
            Timber.e(e, "Error when parsing date.");
        }
    }
}
