package com.zhuinden.simplestackftuesample.features.login

import android.os.Bundle
import android.view.View
import com.zhuinden.simplestackextensions.fragments.KeyedFragment
import com.zhuinden.simplestackextensions.fragmentsktx.lookup
import com.zhuinden.simplestackftuesample.R
import com.zhuinden.simplestackftuesample.databinding.LoginFragmentBinding
import com.zhuinden.simplestackftuesample.utils.get
import com.zhuinden.simplestackftuesample.utils.onClick
import com.zhuinden.simplestackftuesample.utils.onTextChanged
import com.zhuinden.simplestackftuesample.utils.set
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy


class LoginFragment : KeyedFragment(R.layout.login_fragment) {
    private val viewModel by lazy { lookup<LoginViewModel>() }

    private val compositeDisposable = CompositeDisposable()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = LoginFragmentBinding.bind(view)

        with(binding) {
            textUsername.setText(viewModel.username.get())
            textPassword.setText(viewModel.password.get())

            viewModel.isLoginEnabled.distinctUntilChanged().subscribeBy { enabled ->
                buttonLogin.isEnabled = enabled
            }.addTo(compositeDisposable)

            textUsername.onTextChanged { username -> viewModel.username.set(username) }
            textPassword.onTextChanged { password -> viewModel.password.set(password) }
            buttonLogin.onClick { viewModel.onLoginClicked() }
            buttonRegister.onClick { viewModel.onRegisterClicked() }
        }
    }

    override fun onDestroyView() {
        compositeDisposable.clear()
        super.onDestroyView()
    }
}