package com.example.androiduitesting;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ShowActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> rule =
            new ActivityScenarioRule<>(MainActivity.class);

    private void addCity(String name){
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(typeText(name));
        onView(withId(R.id.button_confirm)).perform(click());
    }

    private void openFirstCity(){
        onData(is(instanceOf(String.class)))
                .inAdapterView(withId(R.id.city_list))
                .atPosition(0)
                .perform(click());
    }

    // 1) switched activity
    @Test
    public void switchesToShowActivity(){
        addCity("Edmonton");
        openFirstCity();
        onView(withId(R.id.show_root)).check(matches(isDisplayed()));
    }

    // 2) shows correct name
    @Test
    public void showsCorrectCityName(){
        addCity("Vancouver");
        openFirstCity();
        onView(withId(R.id.text_city_name)).check(matches(withText("Vancouver")));
    }

    // 3) back goes to MainActivity
    @Test
    public void backReturnsToMain(){
        addCity("Calgary");
        openFirstCity();
        onView(withId(R.id.button_back)).perform(click());
        onView(withId(R.id.button_add)).check(matches(isDisplayed()));

        // (system back)
        openFirstCity();
        pressBack();
        onView(withId(R.id.button_add)).check(matches(isDisplayed()));
    }
}
