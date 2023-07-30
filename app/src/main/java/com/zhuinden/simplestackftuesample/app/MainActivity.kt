package com.zhuinden.simplestackftuesample.app

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.zhuinden.simplestack.BackHandlingModel
import com.zhuinden.simplestack.Backstack
import com.zhuinden.simplestack.History
import com.zhuinden.simplestack.SimpleStateChanger
import com.zhuinden.simplestack.StateChange
import com.zhuinden.simplestack.navigator.Navigator
import com.zhuinden.simplestackextensions.fragments.DefaultFragmentStateChanger
import com.zhuinden.simplestackextensions.lifecyclektx.observeAheadOfTimeWillHandleBackChanged
import com.zhuinden.simplestackextensions.servicesktx.get
import com.zhuinden.simplestackftuesample.R
import com.zhuinden.simplestackftuesample.databinding.MainActivityBinding
import com.zhuinden.simplestackftuesample.features.login.LoginKey
import com.zhuinden.simplestackftuesample.features.profile.ProfileKey

class MainActivity : AppCompatActivity(), SimpleStateChanger.NavigationHandler {

    private lateinit var fragmentStateChanger: DefaultFragmentStateChanger
    private lateinit var authenticationManager: AuthenticationManager

    private lateinit var backstack: Backstack

    private val backPressedCallback = object : OnBackPressedCallback(false) { // <-- !
        override fun handleOnBackPressed() {
            backstack.goBack()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onBackPressedDispatcher.addCallback(backPressedCallback) // <-- !

        fragmentStateChanger =
            DefaultFragmentStateChanger(supportFragmentManager, R.id.rootContainer)

        val app = application as CustomApplication
        val globalServices = app.globalServices

        authenticationManager = globalServices.get()

        backstack = Navigator.configure()
            .setBackHandlingModel(BackHandlingModel.AHEAD_OF_TIME)
            .setStateChanger(SimpleStateChanger(this))
            .setScopedServices(ServiceProvider())
            .setGlobalServices(globalServices)
            .install(
                this, binding.rootContainer, History.of(
                    when {
                        authenticationManager.isAuthenticated() -> ProfileKey(authenticationManager.getAuthenticatedUser())
                        else -> LoginKey()
                    }
                )
            )

        backPressedCallback.isEnabled = backstack.willHandleAheadOfTimeBack() // <-- !
        backstack.observeAheadOfTimeWillHandleBackChanged(
            this,
            backPressedCallback::isEnabled::set
        ) // <-- ! from lifecycle-ktx
    }

    override fun onNavigationEvent(stateChange: StateChange) {
        fragmentStateChanger.handleStateChange(stateChange)
    }

    override fun onDestroy() {
        if (isFinishing) {
            authenticationManager.clearRegistration() // just for sample repeat sake
        }

        super.onDestroy()
    }
}