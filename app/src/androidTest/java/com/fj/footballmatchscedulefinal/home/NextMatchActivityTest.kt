package com.fj.footballmatchscedulefinal.home

import android.support.test.espresso.Espresso.*
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.fj.footballmatchscedulefinal.NextMatchActivity
import com.fj.footballmatchscedulefinal.R.id.rv_match
import com.fj.footballmatchscedulefinal.R.id.spinner_league
import com.fj.footballmatchscedulefinal.R.id.fab
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NextMatchActivityTest{
    @Rule
    @JvmField var activityRule = ActivityTestRule(NextMatchActivity::class.java)

    private var wait : Long = 2000

    @Test
    fun testRecyclerViewBehaviour() {
        Thread.sleep(wait)

        onView(withId(rv_match)).check(matches(isDisplayed()))
        onView(withId(rv_match)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
        onView(withId(rv_match)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(10, click()))
    }

    @Test
    fun testAppBehaviour() {
        Thread.sleep(wait)

        // Apakah ada spinner? Kalo ada pilih yang past match
        onView(withId(spinner_league))
                .check(matches(isDisplayed()))
        onView(withId(spinner_league)).perform(click())
        onView(withText("English League Championship")).perform(click())

        Thread.sleep(wait)

        // Apakah ada text Preston? Kalo ada klik
        onView(withText("Reading"))
                .check(matches(isDisplayed()))
        onView(withText("Reading")).perform(click())

        Thread.sleep(wait)

        // Apakah ada text Preston
        onView(withText("Reading")).check(matches(isDisplayed()))

        // Apakah ada add to favorite? Kalo ada klik (add to favorite)
        onView(withId(fab))
                .check(matches(isDisplayed()))
        onView(withId(fab)).perform(click())

        // Apakah ada button add to favorite? Kalo ada klik (removed favorite)
        onView(withId(fab))
                .check(matches(isDisplayed()))
        onView(withId(fab)).perform(click())

        Thread.sleep(1000)
        pressBack()
    }
}