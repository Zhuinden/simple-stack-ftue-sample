package com.zhuinden.simplestackftuesample.features.registration

import android.os.Bundle
import android.view.View
import com.zhuinden.simplestackextensions.fragments.KeyedFragment
import com.zhuinden.simplestackextensions.fragmentsktx.lookup
import com.zhuinden.simplestackftuesample.R
import com.zhuinden.simplestackftuesample.databinding.CreateLoginCredentialsFragmentBinding
import com.zhuinden.simplestackftuesample.utils.get
import com.zhuinden.simplestackftuesample.utils.onClick
import com.zhuinden.simplestackftuesample.utils.onTextChanged
import com.zhuinden.simplestackftuesample.utils.set
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy


class CreateLoginCredentialsFragment : KeyedFragment(R.layout.create_login_credentials_fragment) {
    private val viewModel by lazy { lookup<RegistrationViewModel>() }

    private val compositeDisposable = CompositeDisposable()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = CreateLoginCredentialsFragmentBinding.bind(view)

        with(binding) {
            textUsername.setText(viewModel.username.get())
            textPassword.setText(viewModel.password.get())

            viewModel.isRegisterAndLoginEnabled.subscribeBy { enabled ->
                buttonRegisterAndLogin.isEnabled = enabled
            }.addTo(compositeDisposable)

            textUsername.onTextChanged { username -> viewModel.username.set(username) }
            textPassword.onTextChanged { password -> viewModel.password.set(password) }
            buttonRegisterAndLogin.onClick { viewModel.onRegisterAndLoginClicked() }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
    }
}