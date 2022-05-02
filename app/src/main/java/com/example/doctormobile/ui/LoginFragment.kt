package com.example.doctormobile.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.doctormobile.MainActivity
import com.example.doctormobile.R
import com.example.doctormobile.databinding.FragmentLoginBinding
import com.example.doctormobile.viewModel.AuthViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<AuthViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLoginBinding.bind(view)
        binding.apply {

            emailEditText.editText!!.setText(viewModel.loginEmail)
            passwordEditText.editText!!.setText(viewModel.loginPassword)
            emailEditText.editText!!.addTextChangedListener {
                viewModel.loginEmail = it.toString()
            }
            passwordEditText.editText!!.addTextChangedListener {
                viewModel.loginPassword = it.toString()
            }
            loginButton.setOnClickListener {
                loginProgressBar.isVisible = true
                viewModel.onLoginClick()
            }
            needNewAccountTextView.setOnClickListener {
                findNavController().navigate(
                    LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
                )
            }
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                viewModel.authFlow.collect {
                    when (it) {
                        is AuthViewModel.AuthEvent.AuthSuccess -> {
                            loginProgressBar.isVisible = false
                            if (viewModel.loginEmail.contains(
                                    "@st.niituniversity.in",
                                    ignoreCase = true
                                )
                            ) {
                                MainActivity.isDoc = false
                                findNavController().navigate(
                                    R.id.createAppointmentFragment
                                )
                            } else {
                                MainActivity.isDoc = true
                                findNavController().navigate(
                                    LoginFragmentDirections.actionLoginFragmentToPendingAppointmentsFragment()
                                )
                            }
                        }
                        is AuthViewModel.AuthEvent.AuthFailure -> {
                            loginProgressBar.isVisible = false
                            Snackbar.make(
                                requireView(),
                                it.msg,
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}