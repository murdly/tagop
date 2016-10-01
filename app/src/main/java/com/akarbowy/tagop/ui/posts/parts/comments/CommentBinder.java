package com.akarbowy.tagop.ui.posts.parts.comments;

import android.text.format.DateUtils;

import com.akarbowy.tagop.network.model.Comment;
import com.akarbowy.tagop.network.model.Embed;
import com.akarbowy.tagop.parto.Binder;
import com.akarbowy.tagop.ui.posts.SpannableBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import timber.log.Timber;
//TODO temporary solution. change when group parts implemented
public class CommentBinder implements Binder<CommentView> {

    private final Comment comment;
    private SpannableBody body;
    private String relativeDate;

    public CommentBinder(Comment viewObject) {
        this.comment = viewObject;
    }

    @Override public void prepare(final CommentView view) {
        body = new SpannableBody().setHtmlBodyText(comment.body)
                .setOnLinkClickListener(new SpannableBody.ClickableListener() {
                    @Override public void onSpoilerClick(SpannableBody unspoiled) {
                        view.setContent(unspoiled.getSpannable());
                    }
                }).create();

        try {
            long dateInMillis = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(comment.date).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateInMillis).toString();
        } catch (ParseException e) {
            relativeDate = comment.date;
            Timber.e(e, "Error when parsing date.");
        }
    }

    @Override public void bind(CommentView view) {
        view.setAvatar(comment.authorAvatar);
        view.setAuthor(comment.author);
        view.setContent(body.getSpannable());
        view.setVotes(comment.voteCount.toString());
        view.setDate(relativeDate);

        Embed embed = comment.getEmbed();
        if(embed == null){
            view.hideEmbedView();
        }else{
            view.setEmbedData(embed.previewUrl, embed.url, embed.type.equals("video"));
        }
    }
}
