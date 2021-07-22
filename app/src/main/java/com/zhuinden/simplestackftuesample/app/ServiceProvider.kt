package com.zhuinden.simplestackftuesample.app

import com.zhuinden.simplestack.ServiceBinder
import com.zhuinden.simplestackextensions.services.DefaultServiceProvider
import com.zhuinden.simplestackextensions.servicesktx.add
import com.zhuinden.simplestackextensions.servicesktx.lookup
import com.zhuinden.simplestackftuesample.features.registration.RegistrationViewModel

class ServiceProvider : DefaultServiceProvider() {
    override fun bindServices(serviceBinder: ServiceBinder) {
        super.bindServices(serviceBinder)

        val scope = serviceBinder.scopeTag

        with(serviceBinder) {
            when (scope) {
                RegistrationViewModel::class.java.name -> {
                    add(RegistrationViewModel(lookup(), backstack))
                }
                else -> {
                }
            }
        }
    }
}
