package com.akarbowy.tagop.ui.posts;

import android.content.Intent;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.widget.TextView;

import com.akarbowy.RecyclerItemsCount;
import com.akarbowy.tagop.R;
import com.akarbowy.tagop.data.FakeRemoteDataSource;
import com.akarbowy.tagop.data.model.PostModel;
import com.akarbowy.tagop.data.model.TagModel;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;
import java.util.UUID;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.instanceOf;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class PostScreenTest {

    private static String TAG_TITLE = UUID.randomUUID().toString();

    private static String TAG_TITLE_EMPTY = UUID.randomUUID().toString();

    private static TagModel TAG = new TagModel(TAG_TITLE, false);

    private static PostModel POST = new PostModel(new Random().nextInt());

    static {
        POST.setAuthor("");
        POST.setAuthorAvatar("");
        POST.setDate("");
        POST.setBody("");
        POST.setCommentCount(0);
        POST.setVoteCount(0);
        POST.setTag(TAG);
    }

    @Rule
    public ActivityTestRule<PostsActivity> postsActivityActivityTestRule =
            new ActivityTestRule<>(PostsActivity.class, true, false);


    private void startActivityWithPost(PostModel post, String tag) {
        new FakeRemoteDataSource().addPost(post);

        Intent intent = new Intent();
        intent.putExtra(PostsActivity.EXTRA_TAG, tag);
        intent.setAction(PostsActivity.ACTION_SEARCH);
        postsActivityActivityTestRule.launchActivity(intent);
    }

    @Test
    public void givenTag_whenNoPosts_thenStateContentEmpty(){
        startActivityWithPost(POST, TAG_TITLE_EMPTY);

        onView(allOf(instanceOf(TextView.class), withParent(withId(R.id.toolbar))))
                .check(matches(withText(TAG_TITLE_EMPTY)));

        onView(withId(R.id.recycler)).check(new RecyclerItemsCount(equalTo(0)));
        onView(withId(R.id.state_empty_content)).check(matches(isDisplayed()));
        onView(withId(R.id.recycler)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.state_general_error)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
    }

    @Test
    public void givenTag_whenResults_thenStateContent() {
        startActivityWithPost(POST, TAG_TITLE);

        onView(allOf(instanceOf(TextView.class), withParent(withId(R.id.toolbar))))
                .check(matches(withText(TAG_TITLE)));

        onView(withId(R.id.recycler)).check(new RecyclerItemsCount(greaterThan(0)));
        onView(withId(R.id.recycler)).check(matches(isDisplayed()));
        onView(withId(R.id.state_empty_content)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.state_general_error)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
    }

    /* @Test
    public void givenTag_whenError_thenStateError() {

        onView(withId(R.id.recycler)).check(new RecyclerItemsCount(equalTo(0)));
        onView(withId(R.id.state_general_error)).check(matches(isDisplayed()));
        onView(withId(R.id.recycler)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.state_empty_content)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
    }*/

}
