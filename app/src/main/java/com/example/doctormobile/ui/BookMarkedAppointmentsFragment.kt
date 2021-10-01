package com.example.doctormobile.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.doctormobile.R
import com.example.doctormobile.adapter.CachedCompletedAppointmentsAdapter
import com.example.doctormobile.databinding.FragmentBookmarkedAppointmentsBinding
import com.example.doctormobile.models.CachedCompletedAppointment
import com.example.doctormobile.viewModel.CompletedAppointmentsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class BookMarkedAppointmentsFragment : Fragment(R.layout.fragment_bookmarked_appointments),
    CachedCompletedAppointmentsAdapter.OnItemClickListener {
    private var _binding: FragmentBookmarkedAppointmentsBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<CompletedAppointmentsViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentBookmarkedAppointmentsBinding.bind(view)
        val cachedAdapter = CachedCompletedAppointmentsAdapter(this)
        binding.apply {
            appointmentsRecyclerView.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter = cachedAdapter
            }
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                viewModel.bookMarkedAppointments.collect {
                    cachedAdapter.submitList(it)
                }
            }
        }
    }

    override fun onItemClick(item: CachedCompletedAppointment) {
        findNavController().navigate(
            BookMarkedAppointmentsFragmentDirections
                .actionBookMarkedAppointmentsFragmentToCachedCompletedAppointmentDetailsFragment(
                item
            )
        )
    }
}