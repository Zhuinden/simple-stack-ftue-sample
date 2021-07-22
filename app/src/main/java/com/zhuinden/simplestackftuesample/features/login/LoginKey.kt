package com.zhuinden.simplestackftuesample.features.login

import androidx.fragment.app.Fragment
import com.zhuinden.simplestack.ServiceBinder
import com.zhuinden.simplestackextensions.fragments.DefaultFragmentKey
import com.zhuinden.simplestackextensions.services.DefaultServiceProvider
import com.zhuinden.simplestackextensions.servicesktx.add
import com.zhuinden.simplestackextensions.servicesktx.lookup
import com.zhuinden.simplestackftuesample.app.AuthenticationManager
import kotlinx.parcelize.Parcelize

@Parcelize
class LoginKey : DefaultFragmentKey(), DefaultServiceProvider.HasServices {
    @Suppress("RemoveExplicitTypeArguments")
    override fun bindServices(serviceBinder: ServiceBinder) {
        with(serviceBinder) {
            add(LoginViewModel(lookup<AuthenticationManager>(), backstack))
        }
    }

    override fun getScopeTag(): String = javaClass.name

    override fun instantiateFragment(): Fragment = LoginFragment()
}