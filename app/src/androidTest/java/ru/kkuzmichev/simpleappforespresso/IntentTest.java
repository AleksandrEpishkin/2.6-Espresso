package ru.kkuzmichev.simpleappforespresso;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasData;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import android.content.Intent;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.intent.Intents;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)

public class IntentTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityScenarioRule =
            new ActivityTestRule<>(MainActivity.class);
//    public IntentsTestRule intentsTestRule = new IntentsTestRule(MainActivity.class);

    @Before // Выполняется перед тестами
    public void registerIdlingResources() { //Подключаемся к “счетчику”
        IdlingRegistry.getInstance().register(EspressoIdlingResources.idlingResource);
    }
    @After // Выполняется после тестов
    public void unregisterIdlingResources() { //Отключаемся от “счетчика”
        IdlingRegistry.getInstance().unregister(EspressoIdlingResources.idlingResource);
    }

    @Test
    public void intentTest() {
        ViewInteraction imageView = onView(
                Matchers.allOf(
                        withParent(withParent(withId(R.id.toolbar)))));
        imageView.check(matches(isDisplayed()));
        imageView.perform(click());


        ViewInteraction materialTextView = onView(
                allOf(withId(androidx.constraintlayout.widget.R.id.title), withText("Settings"),
                        isDisplayed()));

        Intents.init();
        materialTextView.perform(click());
        intended(allOf(
            hasData("https://google.com"),
            hasAction(Intent.ACTION_VIEW)
        ));
        Intents.release();
    }

    @Test
    public void checkGalleryTest() {
          ViewInteraction menu = onView(isAssignableFrom(AppCompatImageButton.class));
        menu.check(matches(isDisplayed()));
        menu.perform(click());
        ViewInteraction gallery = onView(withId(R.id.nav_gallery));
        gallery.perform(click());
        ViewInteraction element = (onView(allOf(withId(R.id.item_number), withText("7"))));
        element.check(matches(isDisplayed()));
        element.check(matches(withText("7")));
    }
}
