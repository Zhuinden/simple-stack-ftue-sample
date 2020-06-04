package com.zhuinden.simplestackftuesample.features.registration

import com.jakewharton.rxrelay2.BehaviorRelay
import com.zhuinden.rxcombinetuplekt.combineTuple
import com.zhuinden.simplestack.*
import com.zhuinden.simplestackftuesample.app.AuthenticationManager
import com.zhuinden.simplestackftuesample.features.profile.ProfileKey
import com.zhuinden.simplestackftuesample.utils.get
import com.zhuinden.simplestackftuesample.utils.set
import com.zhuinden.statebundle.StateBundle
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy

class RegistrationViewModel(
    private val authenticationManager: AuthenticationManager,
    private val backstack: Backstack
) : Bundleable, ScopedServices.Registered, ScopedServices.HandlesBack {
    enum class RegistrationState { // this is actually kinda superfluous/unnecessary but ok
        COLLECT_PROFILE_DATA,
        COLLECT_USER_PASSWORD,
        REGISTRATION_COMPLETED
    }

    private var currentState: RegistrationState = RegistrationState.COLLECT_PROFILE_DATA

    private val compositeDisposable = CompositeDisposable()

    val fullName = BehaviorRelay.createDefault("")
    val bio = BehaviorRelay.createDefault("")

    val username = BehaviorRelay.createDefault("")
    val password = BehaviorRelay.createDefault("")

    private val isRegisterAndLoginEnabledRelay = BehaviorRelay.createDefault(false)
    val isRegisterAndLoginEnabled: Observable<Boolean> = isRegisterAndLoginEnabledRelay

    private val isEnterProfileNextEnabledRelay = BehaviorRelay.createDefault(false)
    val isEnterProfileNextEnabled: Observable<Boolean> = isEnterProfileNextEnabledRelay

    override fun onServiceRegistered() {
        combineTuple(fullName, bio)
            .subscribeBy { (fullName, bio) ->
                isEnterProfileNextEnabledRelay.set(fullName.isNotBlank() && bio.isNotBlank())
            }.addTo(compositeDisposable)

        combineTuple(username, password)
            .subscribeBy { (username, password) ->
                isRegisterAndLoginEnabledRelay.set(username.isNotBlank() && password.isNotBlank())
            }.addTo(compositeDisposable)
    }

    override fun onServiceUnregistered() {
        compositeDisposable.clear()
    }

    fun onRegisterAndLoginClicked() {
        if (isRegisterAndLoginEnabledRelay.get()) {
            currentState = RegistrationState.REGISTRATION_COMPLETED
            val username = username.get()
            authenticationManager.saveRegistration(username)
            backstack.setHistory(History.of(ProfileKey(username)), StateChange.FORWARD)
        }
    }

    fun onEnterProfileNextClicked() {
        if (isEnterProfileNextEnabledRelay.get()) {
            currentState = RegistrationState.COLLECT_USER_PASSWORD
            backstack.goTo(CreateLoginCredentialsKey())
        }
    }

    override fun onBackEvent(): Boolean {
        if (currentState == RegistrationState.COLLECT_USER_PASSWORD) {
            currentState = RegistrationState.COLLECT_PROFILE_DATA
            return false // already dispatching, so just go back a screen
        }
        return false
    }

    override fun toBundle(): StateBundle = StateBundle().apply {
        putSerializable("currentState", currentState)
        putString("username", username.get())
        putString("password", password.get())
        putString("fullName", fullName.get())
        putString("bio", bio.get())
    }

    override fun fromBundle(bundle: StateBundle?) {
        bundle?.run {
            currentState = getSerializable("currentState") as RegistrationState
            username.set(getString("username", ""))
            password.set(getString("password", ""))
            fullName.set(getString("fullName", ""))
            bio.set(getString("bio", ""))
        }
    }
}