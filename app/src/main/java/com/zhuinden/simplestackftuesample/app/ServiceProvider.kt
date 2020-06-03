package com.zhuinden.simplestackftuesample.app

import com.zhuinden.simplestack.ServiceBinder
import com.zhuinden.simplestackextensions.services.DefaultServiceProvider
import com.zhuinden.simplestackextensions.servicesktx.add
import com.zhuinden.simplestackextensions.servicesktx.lookup
import com.zhuinden.simplestackftuesample.features.registration.RegistrationViewModel

class ServiceProvider : DefaultServiceProvider() {
    companion object {
        const val REGISTRATION_TAG = "registration"
    }

    override fun bindServices(serviceBinder: ServiceBinder) {
        super.bindServices(serviceBinder)

        val scope = serviceBinder.scopeTag

        with(serviceBinder) {
            when (scope) {
                REGISTRATION_TAG -> {
                    add(RegistrationViewModel(lookup(), backstack))
                }
                else -> {
                }
            }
        }
    }
}
