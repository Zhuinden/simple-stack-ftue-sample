package com.zhuinden.simplestackftuesample.features.profile

import com.zhuinden.simplestack.Backstack
import com.zhuinden.simplestack.History
import com.zhuinden.simplestack.ScopedServices
import com.zhuinden.simplestack.StateChange
import com.zhuinden.simplestackftuesample.app.AuthenticationManager
import com.zhuinden.simplestackftuesample.features.login.LoginKey

class ProfileViewModel(
    private val authenticationManager: AuthenticationManager,
    private val backstack: Backstack
) : ScopedServices.Activated {
    override fun onServiceActive() {
        if (!authenticationManager.isAuthenticated()) {
            backstack.setHistory(History.of(LoginKey()), StateChange.REPLACE)
        }
    }

    override fun onServiceInactive() {
    }
}