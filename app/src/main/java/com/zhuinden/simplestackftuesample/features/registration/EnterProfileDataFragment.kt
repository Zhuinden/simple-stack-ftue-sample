package com.zhuinden.simplestackftuesample.features.registration

import android.os.Bundle
import android.view.View
import com.zhuinden.simplestackextensions.fragments.KeyedFragment
import com.zhuinden.simplestackextensions.fragmentsktx.lookup
import com.zhuinden.simplestackftuesample.R
import com.zhuinden.simplestackftuesample.databinding.EnterProfileDataFragmentBinding
import com.zhuinden.simplestackftuesample.utils.get
import com.zhuinden.simplestackftuesample.utils.onClick
import com.zhuinden.simplestackftuesample.utils.onTextChanged
import com.zhuinden.simplestackftuesample.utils.set
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy


class EnterProfileDataFragment : KeyedFragment(R.layout.enter_profile_data_fragment) {
    private val viewModel by lazy { lookup<RegistrationViewModel>() }

    private val compositeDisposable = CompositeDisposable()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = EnterProfileDataFragmentBinding.bind(view)

        with(binding) {
            textFullName.setText(viewModel.fullName.get())
            textBio.setText(viewModel.bio.get())

            viewModel.isEnterProfileNextEnabled.subscribeBy { enabled ->
                buttonEnterProfileNext.isEnabled = enabled
            }.addTo(compositeDisposable)

            textFullName.onTextChanged { fullName -> viewModel.fullName.set(fullName) }
            textBio.onTextChanged { bio -> viewModel.bio.set(bio) }
            buttonEnterProfileNext.onClick { viewModel.onEnterProfileNextClicked() }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
    }
}