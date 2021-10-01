package com.example.doctormobile.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
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
                viewModel.onRegisterClick()
                hideKeyboard(requireContext())
            }
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                viewModel.authFlow.collect {
                    when (it) {
                        is AuthViewModel.AuthEvent.AuthSuccess -> {
                            findNavController().navigate(
                                RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
                            )
                        }
                        is AuthViewModel.AuthEvent.AuthFailure -> {
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

    private fun hideKeyboard(mContext: Context) {
        val imm = mContext
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(
            requireActivity().window
                .currentFocus!!.windowToken, 0
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}