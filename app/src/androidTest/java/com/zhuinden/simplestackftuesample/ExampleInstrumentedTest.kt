package com.zhuinden.simplestackftuesample

import android.view.ViewGroup
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.zhuinden.espressohelper.checkContainsText
import com.zhuinden.espressohelper.conditionwatcher.ConditionWatcher
import com.zhuinden.espressohelper.performClick
import com.zhuinden.espressohelper.performTypeText
import com.zhuinden.espressohelper.rotateOrientation
import com.zhuinden.simplestackextensions.fragments.DefaultFragmentKey
import com.zhuinden.simplestackextensions.navigatorktx.backstack
import com.zhuinden.simplestackftuesample.R.id.*
import com.zhuinden.simplestackftuesample.app.MainActivity
import com.zhuinden.simplestackftuesample.features.profile.ProfileKey
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @JvmField
    @field:Rule
    var rule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.zhuinden.simplestackftuesample", appContext.packageName)
    }

    @Test
    fun registerSuccessfully() {
        ConditionWatcher.waitForCondition {
            rule.activity.findViewById<ViewGroup>(R.id.rootContainer).childCount > 0
        }

        val backstack = rule.activity.backstack

        buttonRegister.performClick()
        textFullName.performTypeText("hello")
        textBio.performTypeText("world")

        rule.activity.rotateOrientation()
        rule.activity.rotateOrientation()

        textFullName.checkContainsText("hello")
        textBio.checkContainsText("world")

        buttonEnterProfileNext.performClick()

        textUsername.performTypeText("username")
        textPassword.performTypeText("password")

        InstrumentationRegistry.getInstrumentation().runOnMainSync {
            rule.activity.onBackPressed()
        }


        textFullName.checkContainsText("hello")
        textBio.checkContainsText("world")

        buttonEnterProfileNext.performClick()

        textUsername.checkContainsText("username")
        textPassword.checkContainsText("password")

        buttonRegisterAndLogin.performClick()

        InstrumentationRegistry.getInstrumentation().runOnMainSync {
            ConditionWatcher.waitForCondition {
                backstack.getHistory<DefaultFragmentKey>().top<DefaultFragmentKey>() is ProfileKey
            }
        }
    }
}
