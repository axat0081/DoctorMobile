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
import com.example.doctormobile.databinding.FragmentRegisterBinding
import com.example.doctormobile.viewModel.AuthViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class RegisterFragment : Fragment(R.layout.fragment_register) {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<AuthViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRegisterBinding.bind(view)
        binding.apply {
            emailEditText.editText!!.setText(viewModel.registerEmail)
            passwordEditText.editText!!.setText(viewModel.registerPassword)
            emailEditText.editText!!.addTextChangedListener {
                viewModel.registerEmail = it.toString()
            }
            passwordEditText.editText!!.addTextChangedListener {
                viewModel.registerPassword = it.toString()
            }
            registerButton.setOnClickListener {
                registrationProgressBar.isVisible = true
                viewModel.onRegisterClick()
            }
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                viewModel.authFlow.collect {
                    when (it) {
                        is AuthViewModel.AuthEvent.AuthSuccess -> {
                            registrationProgressBar.isVisible = false
                            if (viewModel.registerEmail.contains(
                                    "@st.niituniversity.in",
                                    ignoreCase = true
                                )
                            ) {
                                MainActivity.isDoc = true
                                findNavController().navigate(
                                    R.id.createAppointmentFragment
                                )
                            } else {
                                MainActivity.isDoc = true
                                findNavController().navigate(
                                    R.id.pendingAppointmentsFragment
                                )
                            }
                        }
                        is AuthViewModel.AuthEvent.AuthFailure -> {
                            registrationProgressBar.isVisible = false
                            Snackbar.make(
                                requireView(),
                                it.msg,
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
            alreadyHaveAnAccountTextView.setOnClickListener {
                findNavController().navigate(
                    RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}