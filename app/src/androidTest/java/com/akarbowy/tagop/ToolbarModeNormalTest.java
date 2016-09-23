package com.akarbowy.tagop;

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.akarbowy.tagop.ui.search.MainSearchActivity;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.is;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class ToolbarModeNormalTest {

    private static final Matcher<View> OVERFLOW_BUTTON_MATCHER = anyOf(
            allOf(isDisplayed(), withContentDescription("Więcej opcji")),
            allOf(isDisplayed(), withClassName(endsWith("OverflowMenuButton"))));
    @Rule
    public ActivityTestRule<MainSearchActivity> mActivityRule = new ActivityTestRule(MainSearchActivity.class);

    @Test
    public void clearHistory_check_emptyList_stateEmptyHistory() {
        onView(withId(R.id.toolbar_layout_action)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(allOf(OVERFLOW_BUTTON_MATCHER, isDescendantOfA(withId(R.id.toolbar)))).perform(click());
        onView(withText("Czyść historię")).perform(click());
        onView(withId(R.id.recycler_history)).check(new RecyclerItemsCount(0));
        onView(withId(R.id.state_history_empty)).check(matches(isDisplayed()));
        onView(withId(R.id.state_history_empty_filter)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
    }

    @Test
    public void clickToolbarNormal_check_toolbarSwitchedToSearchMode() {
        onView(withId(R.id.toolbar_layout_action)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.toolbar_layout_action)).perform(click());
        onView(withId(R.id.toolbar_layout_action)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.toolbar_layout_searchable)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    private class RecyclerItemsCount implements ViewAssertion {
        private final int expectedCount;

        private RecyclerItemsCount(int expectedCount) {
            this.expectedCount = expectedCount;
        }


        @Override public void check(View view, NoMatchingViewException noViewFoundException) {
            if (noViewFoundException != null) {
                throw noViewFoundException;
            }

            RecyclerView recyclerView = (RecyclerView) view;
            RecyclerView.Adapter adapter = recyclerView.getAdapter();
            assertThat(adapter.getItemCount(), is(expectedCount));
        }
    }

}