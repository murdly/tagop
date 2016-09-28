package com.akarbowy.tagop.ui.posts;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.akarbowy.RecyclerItemsCount;
import com.akarbowy.tagop.R;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

@RunWith(AndroidJUnit4.class)
public class PostsActivityTest {

    @Rule
    public ActivityTestRule<PostsActivity> mActivityRule = new ActivityTestRule<>(PostsActivity.class, true, false);
    private IdlingResource idlingResource;

    @Test
    public void givenTag_whenResults_thenStateContent() {
        mActivityRule.launchActivity(PostsActivity.getStartIntent(InstrumentationRegistry.getContext(), "android", false));
        idlingResource = mActivityRule.getActivity().getIdlingResource();
        Espresso.registerIdlingResources(idlingResource);

        onView(withId(R.id.recycler)).check(new RecyclerItemsCount(greaterThan(0)));
        onView(withId(R.id.recycler)).check(matches(isDisplayed()));
        onView(withId(R.id.state_empty_content)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.state_general_error)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));

        mActivityRule.getActivity().finish();
    }

    @Test
    public void givenTag_whenNoResults_thenStateContentEmpty() {
        mActivityRule.launchActivity(PostsActivity.getStartIntent(InstrumentationRegistry.getContext(), "s2f1nd23ascnj2", false));
        idlingResource = mActivityRule.getActivity().getIdlingResource();
        Espresso.registerIdlingResources(idlingResource);

        onView(withId(R.id.recycler)).check(new RecyclerItemsCount(equalTo(0)));
        onView(withId(R.id.state_empty_content)).check(matches(isDisplayed()));
        onView(withId(R.id.recycler)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.state_general_error)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));

        mActivityRule.getActivity().finish();
    }

    /**requires manual switch to airplane mode before running*/
   /* @Test
    public void givenTag_whenError_thenStateError() {
        mActivityRule.launchActivity(PostsActivity.getStartIntent(InstrumentationRegistry.getContext(), "whatever", false));
        idlingResource = mActivityRule.getActivity().getIdlingResource();
        Espresso.registerIdlingResources(idlingResource);

        onView(withId(R.id.recycler)).check(new RecyclerItemsCount(equalTo(0)));
        onView(withId(R.id.state_general_error)).check(matches(isDisplayed()));
        onView(withId(R.id.recycler)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.state_empty_content)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));

        mActivityRule.getActivity().finish();
    }*/

    @After
    public void unregisterIdlingResource() {
        if (idlingResource != null) {
            Espresso.unregisterIdlingResources(idlingResource);
        }
    }
}