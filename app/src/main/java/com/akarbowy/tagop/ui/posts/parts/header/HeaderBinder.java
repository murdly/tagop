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
import com.akarbowy.tagop.data.database.model.PostModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import timber.log.Timber;

public class HeaderBinder implements Binder<HeaderView> {

    private PostModel post;
    private MoreActionsBottomSheet.Callback moreActionsListener;
    private String relativeDate;

    public HeaderBinder(PostModel viewObject) {
        post = viewObject;
    }

    @Override public void prepare(final HeaderView view) {
        try {
            long dateInMillis = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(post.date).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateInMillis).toString();
        } catch (ParseException e) {
            relativeDate = post.date;
            Timber.e(e, "Error when parsing date.");
        }

        final ClipboardManager clipboard = (ClipboardManager) view.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        moreActionsListener = new MoreActionsBottomSheet.Callback() {
            @Override public void openInBrowser() {
                view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(post.url)));
            }

            @Override public void copyLink() {
                ClipData clip = ClipData.newPlainText(null, post.url);
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
        view.setAvatar(post.authorAvatar);
        view.setTitle(post.author);
        view.setDate(relativeDate);
        view.setOnMoreActionsListener(moreActionsListener);
    }

    @Override public void unbind(HeaderView view) {

    }
}
