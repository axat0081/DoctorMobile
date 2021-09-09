package com.example.doctormobile.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.doctormobile.R
import com.example.doctormobile.databinding.FragmentLoginBinding

class LoginFragment : Fragment(R.layout.fragment_login) {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLoginBinding.bind(view)
        binding.apply {
           needNewAccountTextView.setOnClickListener {
               findNavController().navigate(
                   LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
               )
           }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}