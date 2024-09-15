package com.example.weatherus.view

import android.view.View
import android.widget.TextView
import androidx.test.espresso.Espresso
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.weatherus.R
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @get:Rule
    var mActivityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun title_of_state_and_city_should_be_shown_by_default() {
        Espresso.onView(withId(R.id.tvStateTitle)).check(matches(withText("State")))
        Espresso.onView(withId(R.id.tvCityTitle)).check(matches(withText("City")))
        //Espresso.onView(withId(R.id.etCity)).perform(typeText("Novi"))
        Espresso.onView(withId(R.id.etCity)).perform(typeText("Novi"), closeSoftKeyboard())
        Espresso.onView(withId(R.id.etCity)).check(matches(withText("Novi")))
    }

    @Test
    fun get_Weather_button_should_display_data() {
        Espresso.onView(withId(R.id.tvState)).perform(setTextInTextView("CA"))
        Espresso.onView(withId(R.id.tvState)).check(matches(withText("CA")))
        Espresso.onView(withId(R.id.etCity)).perform(typeText("La"))
        Espresso.onView(withId(R.id.etCity)).check(matches(withText("La")))
        Espresso.onView(withId(R.id.btnWeather)).perform(click())
        Thread.sleep(500)
        Espresso.onView(withId(R.id.tvCity)).check(matches(withText("La, US")))
        Espresso.onView(withId(R.id.tvTemp)).check(matches(withText(containsString("°F"))))
        Espresso.onView(withId(R.id.tvMinMax)).check(matches(withText(containsString("/"))))
        Espresso.onView(withId(R.id.tvMinMax)).check(matches(withText(containsString("°F"))))
        Espresso.onView(withId(R.id.tvfeelsLike)).check(matches(withText(containsString("°F"))))
        Espresso.onView(withId(R.id.tvWeatherDescription)).check(matches(notNullValue()))
        Espresso.onView(withId(R.id.tvWind)).check(matches(withText(containsString("m/s"))))
        Espresso.onView(withId(R.id.tvPressure)).check(matches(withText(containsString("hPa"))))
        Espresso.onView(withId(R.id.tvHumidity)).check(matches(withText(containsString("Humidity:"))))
        Espresso.onView(withId(R.id.tvHumidity)).check(matches(withText(containsString("%"))))
        Espresso.onView(withId(R.id.tvVisibility)).check(matches(withText(containsString("Visibility:"))))
        Espresso.onView(withId(R.id.tvVisibility)).check(matches(withText(containsString("km"))))
    }

    private fun setTextInTextView(value: String?): ViewAction? {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return allOf(isDisplayed(), isAssignableFrom(TextView::class.java))
            }

            override fun perform(uiController: UiController?, view: View) {
                (view as TextView).text = value
            }

            override fun getDescription(): String {
                return "replace text"
            }
        }
    }
}