package com.fj.footballmatchscedulefinal.home

import android.support.test.espresso.Espresso.*
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.fj.footballmatchscedulefinal.R.id.rv_team
import com.fj.footballmatchscedulefinal.R.id.spinner_team
import com.fj.footballmatchscedulefinal.R.id.fab
import com.fj.footballmatchscedulefinal.TeamActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TeamActivityTest{
    @Rule
    @JvmField var activityRule = ActivityTestRule(TeamActivity::class.java)

    private var wait : Long = 2000

    @Test
    fun testRecyclerViewBehaviour() {
        Thread.sleep(wait)

        onView(withId(rv_team)).check(matches(isDisplayed()))
        onView(withId(rv_team)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
        onView(withId(rv_team)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(10, click()))
    }

    @Test
    fun testAppBehaviour() {
        Thread.sleep(wait)

        // Apakah ada spinner? Kalo ada pilih yang past match
        onView(withId(spinner_team))
                .check(matches(isDisplayed()))
        onView(withId(spinner_team)).perform(click())
        onView(withText("English League Championship")).perform(click())

        Thread.sleep(wait)

        // Apakah ada text Aston Villa? Kalo ada klik
        onView(withText("Aston Villa"))
                .check(matches(isDisplayed()))
        onView(withText("Aston Villa")).perform(click())

        Thread.sleep(wait)

        // Apakah ada text Preston
        onView(withText("Aston Villa")).check(matches(isDisplayed()))

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