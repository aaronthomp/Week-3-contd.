package com.example.testdesigntab;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(5892);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.editTextTextPassword),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatEditText.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.editTextTextPassword),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("12345"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.buttonLogin), withText("Login"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.textView), withText("You shall not pass!"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView.check(matches(withText("You shall not pass!")));
    }

    /**
     * testing password when its missin an upper case letter
     */
    @Test
    public void testFindMissingUpperCase(){
        ViewInteraction appCompatEditText = onView(withId(R.id.editTextTextPassword));
        appCompatEditText.perform(replaceText("password123#$*"));

        ViewInteraction materialButton = onView(withId(R.id.buttonLogin));
        materialButton.perform(click());

        ViewInteraction textView = onView(withId((R.id.textView)));
        textView.check(matches(withText("You shall not pass!")));
    }

    /**
     * testing password when its missing a lower case letter
     */
    @Test
    public void testFindMissingLowerCase(){
        ViewInteraction appCompatEditText = onView(withId(R.id.editTextTextPassword));
        appCompatEditText.perform(replaceText("PASSWORD123#$*"));

        ViewInteraction materialButton = onView(withId(R.id.buttonLogin));
        materialButton.perform(click());

        ViewInteraction textView = onView(withId((R.id.textView)));
        textView.check(matches(withText("You shall not pass!")));
    }

    /**
     * testing password when it is missing a special character
     */
    @Test
    public void testFindMissingSpecialChar(){
        ViewInteraction appCompatEditText = onView(withId(R.id.editTextTextPassword));
        appCompatEditText.perform(replaceText("Password123"));

        ViewInteraction materialButton = onView(withId(R.id.buttonLogin));
        materialButton.perform(click());

        ViewInteraction textView = onView(withId((R.id.textView)));
        textView.check(matches(withText("You shall not pass!")));
    }

    /**
     * testing password when it is missing a digit
     */
    @Test
    public void testFindMissingDigit(){
        ViewInteraction appCompatEditText = onView(withId(R.id.editTextTextPassword));
        appCompatEditText.perform(replaceText("Password#$*"));

        ViewInteraction materialButton = onView(withId(R.id.buttonLogin));
        materialButton.perform(click());

        ViewInteraction textView = onView(withId((R.id.textView)));
        textView.check(matches(withText("You shall not pass!")));
    }

    /**
     * testing valid password case
     */
    @Test
    public void testGoodPasswordCase(){
        ViewInteraction appCompatEditText = onView(withId(R.id.editTextTextPassword));
        appCompatEditText.perform(replaceText("Password123#$*"));

        ViewInteraction materialButton = onView(withId(R.id.buttonLogin));
        materialButton.perform(click());

        ViewInteraction textView = onView(withId((R.id.textView)));
        textView.check(matches(withText("Your password meets the requirements")));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
