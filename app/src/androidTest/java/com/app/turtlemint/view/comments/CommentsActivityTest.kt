package com.app.turtlemint.view.comments

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.app.turtlemint.R
import com.app.turtlemint.model.CommentsResponseModel
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CommentsActivityTest {

    @Rule
    @JvmField
    var activityRule: ActivityScenarioRule<CommentsActivity> =
        ActivityScenarioRule(CommentsActivity::class.java)

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

    //test system back arrow click
    @Test
    @Throws(InterruptedException::class)
    fun testClickOnBackArrow() {

        Espresso.pressBack()
        Thread.sleep(100)
        Espresso.onView(withId(R.id.tv_issue_list))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }

    //test see more comments data button click
    @Test
    @Throws(InterruptedException::class)
    fun testClickOnSeeMore() {

        Espresso.onView(withId(R.id.tvSeeMore)).perform(ViewActions.click())

        Thread.sleep(100)

        Espresso.onView(withId(R.id.tv_issue_description))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }

    //test see less comments data button click
    @Test
    @Throws(InterruptedException::class)
    fun testClickOnSeeLess() {

        Espresso.onView(withId(R.id.tvSeeLess)).perform(ViewActions.click())

        Thread.sleep(100)

        Espresso.onView(withId(R.id.tv_issue_description))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }

    //show data or no data found
    @Test
    @Throws(InterruptedException::class)
    fun testCommentsData() {
        Espresso.onView(withId(R.id.tv_comments_header))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        val dbResponse = CommentsResponseModel()
        if(dbResponse.size > 0){
            Espresso.onView(withId(R.id.rv_comments))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        } else{
            Espresso.onView(withId(R.id.iv_no_comments_found))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            Espresso.onView(withId(R.id.tv_no_comments_found))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        }

    }

}