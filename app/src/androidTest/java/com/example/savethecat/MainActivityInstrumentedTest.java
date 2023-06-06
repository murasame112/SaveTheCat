package com.example.savethecat;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.espresso.intent.Intents;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;


import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainActivityInstrumentedTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setUp()
    {
        Intents.init();
    }

    @After
    public void tearDown()
    {
        Intents.release();
    }

    @Test
    public void startButtonTest()
    {
        //klikniecie
        onView(withId(R.id.startButton)).perform(ViewActions.click());

        //Sprawdze czy przycisk zmienia layout na chooseMode
        onView(withId(R.id.chooseModeLayout)).check(matches(isDisplayed()));
    }
}
