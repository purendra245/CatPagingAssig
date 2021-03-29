package com.catpaging.ui.activity

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.catpaging.R
import com.catpaging.constants.IntentConstants
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4ClassRunner::class)
class CatImageActviityTest {

    private val catFactory = CatFactoryTest ()


    @Test
    fun mainCatActivityTest() {

        val element = catFactory.createCatResponse()
        val scenario = ActivityScenario.launch<CatImageActivity>(
            Intent(
                InstrumentationRegistry.getInstrumentation().targetContext,
                CatImageActivity::class.java
            ).apply {
                putExtra(IntentConstants.CAT_EXTRA_URL, element.url)
            }
        )
        onView(withId(R.id.imgViewCat))
            .check(matches(isDisplayed()));


    }

}