package com.app.turtlemint.view.issues

import android.view.View
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.app.turtlemint.R
import com.app.turtlemint.model.CommentsResponseModel
import com.app.turtlemint.model.IssueResponseModel
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainActivityTest {

    @Rule
    @JvmField
    var activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    @Before
    @Throws(Exception::class)
    fun setUp() {
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
    }

    //test click system back pressed
    @Test
    @Throws(InterruptedException::class)
    fun testClickOnSystemBackArrow() {
        Espresso.pressBack()
        Thread.sleep(300)
        onView(withId(R.id.tv_powered_by)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    //test issue item click
    @Test
    @Throws(InterruptedException::class)
    fun testClickOnIssuesItem() {

        onView(withId(R.id.rv_issues))
            .perform(
                RecyclerViewActions
                    .actionOnItemAtPosition<IssuesAdapter.MyViewHolder>(
                        0,
                        clickItemWithId(R.id.cl_issue_item)
                    )
            )

        Thread.sleep(100)

    }

    //SB: click child view by using resource id
    private fun clickItemWithId(id: Int): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View>? {
                return null
            }

            override fun getDescription(): String {
                return "Click on a child view with specified id."
            }

            override fun perform(uiController: UiController, view: View) {
                val v = view.findViewById<View>(id) as View
                v.performClick()
            }
        }
    }

    //show data or no data found
    @Test
    @Throws(InterruptedException::class)
    fun testCommentsData() {
        Espresso.onView(withId(R.id.tv_issue_list))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        val dbResponse = IssueResponseModel()
        if(dbResponse.size > 0){
            Espresso.onView(withId(R.id.rv_issues))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        } else{
            Espresso.onView(withId(R.id.iv_no_issues_found))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            Espresso.onView(withId(R.id.tv_no_issues_found))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        }

    }
}