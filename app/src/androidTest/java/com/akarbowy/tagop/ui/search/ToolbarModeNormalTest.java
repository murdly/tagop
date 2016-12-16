package com.akarbowy.tagop.ui.search;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.akarbowy.RecyclerItemsCount;
import com.akarbowy.tagop.R;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
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


@RunWith(AndroidJUnit4.class)
@LargeTest
public class ToolbarModeNormalTest {

    private static final Matcher<View> OVERFLOW_BUTTON_MATCHER = anyOf(
            allOf(isDisplayed(), withContentDescription("Więcej opcji")),
            allOf(isDisplayed(), withClassName(endsWith("OverflowMenuButton"))));
    @Rule
    public ActivityTestRule<MainSearchActivity> mainSearchActivityActivityTestRule =
            new ActivityTestRule<>(MainSearchActivity.class);

    @Test
    public void clearHistory_check_emptyList_stateEmptyHistory() {
        onView(ViewMatchers.withId(R.id.toolbar_layout_action)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
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
}