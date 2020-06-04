package com.zhuinden.simplestackftuesample.features.profile

import androidx.fragment.app.Fragment
import com.zhuinden.simplestack.ServiceBinder
import com.zhuinden.simplestackextensions.fragments.DefaultFragmentKey
import com.zhuinden.simplestackextensions.services.DefaultServiceProvider
import com.zhuinden.simplestackextensions.servicesktx.add
import com.zhuinden.simplestackextensions.servicesktx.lookup
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProfileKey(val username: String) : DefaultFragmentKey(),
    DefaultServiceProvider.HasServices {
    override fun bindServices(serviceBinder: ServiceBinder) {
        with(serviceBinder) {
            add(ProfileViewModel(lookup(), backstack))
        }
    }

    override fun getFragmentTag(): String = toString()

    override fun getScopeTag(): String = toString()

    override fun instantiateFragment(): Fragment = ProfileFragment()
}
