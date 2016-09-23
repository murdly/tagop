package com.akarbowy.tagop;

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.akarbowy.tagop.ui.search.MainSearchActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ToolbarModeSearchTest {

    @Rule
    public ActivityTestRule<MainSearchActivity> mActivityRule = new ActivityTestRule(MainSearchActivity.class);

    @Before
    public void switchToSearchMode(){
        onView(withId(R.id.toolbar_layout_action)).perform(click());
    }

    @Test
    public void clickClearQuery_check_fieldIsEmpty() {
        ViewInteraction fieldView = onView(withId(R.id.field_query));
        fieldView.perform(click(), typeText("android"));
        onView(withId(R.id.button_field_cancel)).perform(click());
        fieldView.check(matches(withText("")));
    }

    @Test
    public void typeQuery_check_stateEmptyFilter() {
        onView(withId(R.id.field_query)).perform(typeText("android"));
        onView(withId(R.id.state_history_empty)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.state_history_empty_filter)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.filter_query_param)).check(matches(withText(containsString("android"))));
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