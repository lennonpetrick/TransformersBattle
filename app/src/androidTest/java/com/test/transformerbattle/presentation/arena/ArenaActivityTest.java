package com.test.transformerbattle.presentation.arena;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.test.transformerbattle.R;
import com.test.transformerbattle.presentation.transformer.TransformerActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class ArenaActivityTest {

    @Rule
    public IntentsTestRule<ArenaActivity> mActivityRule
            = new IntentsTestRule<>(ArenaActivity.class);

    @Test
    public void openTransformerActivity() {
        onView(withId(R.id.fabNewTransformer))
                .perform(click());

        intended(hasComponent(TransformerActivity.class.getName()));
    }

    @Test
    public void showBattleResultDialog() {
        onView(withId(R.id.menu_action_battle))
                .perform(click());

        onView(withText("CLOSE")).check(matches(isDisplayed()));
    }
}