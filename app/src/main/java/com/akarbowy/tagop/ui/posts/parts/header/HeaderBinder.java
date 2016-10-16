package com.akarbowy.tagop.ui.posts.parts.header;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.format.DateUtils;
import android.widget.Toast;

import com.akarbowy.partdefiner.Binder;
import com.akarbowy.tagop.R;
import com.akarbowy.tagop.data.network.model.TagEntry;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import timber.log.Timber;

public class HeaderBinder implements Binder<HeaderView> {

    private TagEntry tagEntry;
    private MoreActionsBottomSheet.Callback moreActionsListener;
    private String relativeDate;

    public HeaderBinder(TagEntry viewObject) {
        tagEntry = viewObject;
    }

    @Override public void prepare(final HeaderView view) {
        try {
            long dateInMillis = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(tagEntry.date).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateInMillis).toString();
        } catch (ParseException e) {
            relativeDate = tagEntry.date;
            Timber.e(e, "Error when parsing date.");
        }

        final ClipboardManager clipboard = (ClipboardManager) view.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        moreActionsListener = new MoreActionsBottomSheet.Callback() {
            @Override public void openInBrowser() {
                view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(tagEntry.url)));
            }

            @Override public void copyLink() {
                ClipData clip = ClipData.newPlainText(null, tagEntry.url);
                clipboard.setPrimaryClip(clip);

                CharSequence clippedText = clip.getItemAt(0).getText();
                Toast.makeText(view.getContext(),
                        view.getResources().getString(R.string.toast_copy_link_success, clippedText),
                        Toast.LENGTH_SHORT)
                        .show();
            }
        };
    }

    @Override public void bind(HeaderView view) {
        view.setAvatar(tagEntry.authorAvatar);
        view.setTitle(tagEntry.author);
        view.setDate(relativeDate);
        view.setOnMoreActionsListener(moreActionsListener);
    }

    @Override public void unbind(HeaderView view) {

    }
}
