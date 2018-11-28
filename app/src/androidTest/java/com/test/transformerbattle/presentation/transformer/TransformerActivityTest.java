package com.test.transformerbattle.presentation.transformer;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.SeekBar;

import com.test.transformerbattle.R;
import com.test.transformerbattle.presentation.arena.ArenaActivity;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class TransformerActivityTest {

    @Rule
    public ActivityTestRule<ArenaActivity> mActivityRule
            = new ActivityTestRule<>(ArenaActivity.class);

    @Rule
    public ActivityTestRule<TransformerActivity> mActivityRule2
            = new ActivityTestRule<>(TransformerActivity.class);

    @Test
    public void openTransformerActivity() {
        onView(withId(R.id.fabNewTransformer))
                .perform(click());

        onView(withId(R.id.edName))
                .perform(typeText("Optimus Prime"));

        onView(withId(R.id.rbTeamAutobots))
                .perform(click());

        onView(withId(R.id.sbStrength)).perform(setProgress(90));
        onView(withId(R.id.sbIntelligence)).perform(setProgress(80));
        onView(withId(R.id.sbSpeed)).perform(setProgress(23));
        onView(withId(R.id.sbEndurance)).perform(setProgress(65));
        onView(withId(R.id.sbRank)).perform(setProgress(69));
        onView(withId(R.id.sbCourage)).perform(setProgress(98));
        onView(withId(R.id.sbFirePower)).perform(setProgress(92));
        onView(withId(R.id.sbSkill)).perform(setProgress(86));

        onView(withId(R.id.menu_action_save_update))
                .perform(click());

        onView(withText(R.string.transformer_save_progress)).check(matches(isDisplayed()));
    }

    public static ViewAction setProgress(final int progress) {
        return new ViewAction() {
            @Override
            public void perform(UiController uiController, View view) {
                ((SeekBar) view).setProgress(progress);
            }

            @Override
            public String getDescription() {
                return "Set a progress";
            }

            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isAssignableFrom(SeekBar.class);
            }
        };
    }
}