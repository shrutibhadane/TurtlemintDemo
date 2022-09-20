package com.app.turtlemint.view.splash

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.app.turtlemint.R
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class SplashActivityTest {

    @Rule
    @JvmField
    var activityRule: ActivityScenarioRule<SplashActivity> =
        ActivityScenarioRule(SplashActivity::class.java)

    @Before
    @Throws(Exception::class)
    fun setUp() {
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
    }

    //test system back arrow click
    @Test
    @Throws(InterruptedException::class)
    fun testClickOnSystemBackArrow() {

        Espresso.pressBack()
        Thread.sleep(100)

    }

    //test screen back arrow click
    @Test
    @Throws(InterruptedException::class)
    fun testCheckAnimation() {

        Espresso.onView(withId(R.id.lav_splash)).perform(ViewActions.click())
        Thread.sleep(2500)
        Espresso.onView(withId(R.id.tv_powered_by))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }


}