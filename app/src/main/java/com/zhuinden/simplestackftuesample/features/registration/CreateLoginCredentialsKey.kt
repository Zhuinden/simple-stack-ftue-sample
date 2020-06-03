package com.zhuinden.simplestackftuesample.features.registration

import androidx.fragment.app.Fragment
import com.zhuinden.simplestack.ScopeKey
import com.zhuinden.simplestackextensions.fragments.DefaultFragmentKey
import com.zhuinden.simplestackftuesample.app.ServiceProvider
import kotlinx.android.parcel.Parcelize

@Parcelize
class CreateLoginCredentialsKey : DefaultFragmentKey(), ScopeKey.Child {
    override fun instantiateFragment(): Fragment = CreateLoginCredentialsFragment()

    override fun getParentScopes(): List<String> = listOf(ServiceProvider.REGISTRATION_TAG)
}