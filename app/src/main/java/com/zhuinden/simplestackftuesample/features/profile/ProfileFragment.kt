package com.zhuinden.simplestackftuesample.features.profile

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.zhuinden.simplestackextensions.fragments.KeyedFragment
import com.zhuinden.simplestackftuesample.R

class ProfileFragment : KeyedFragment(R.layout.profile_fragment) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val key = getKey<ProfileKey>()

        Toast.makeText(requireContext(), "Welcome ${key.username}!", Toast.LENGTH_LONG).show()
    }
}