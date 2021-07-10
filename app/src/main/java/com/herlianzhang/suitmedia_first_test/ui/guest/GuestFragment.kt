package com.herlianzhang.suitmedia_first_test.ui.guest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.herlianzhang.suitmedia_first_test.databinding.FragmentGuestBinding
import com.herlianzhang.suitmedia_first_test.ui.event_or_guest.EventOrGuestFragment.Companion.GUEST_NAME
import com.herlianzhang.suitmedia_first_test.ui.event_or_guest.EventOrGuestFragment.Companion.GUEST_RESULT
import com.herlianzhang.suitmedia_first_test.vo.Guest
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GuestFragment : Fragment(), GuestAdapter.OnClickListener {

    private lateinit var binding: FragmentGuestBinding
    private val viewModel: GuestViewModel by viewModels()

    private val adapter = GuestAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGuestBinding.inflate(inflater, container, false)

        initAdapter()
        initListener()
        initObserver()

        return binding.root
    }

    private fun initAdapter() {
        binding.rvMain.adapter = adapter
    }

    private fun initListener() {
        binding.buttonError.setOnClickListener {
            viewModel.getGuests()
        }

        binding.srlMain.setOnRefreshListener {
            viewModel.getGuests()
        }
    }

    private fun initObserver() {
        viewModel.data.observe(viewLifecycleOwner) { data ->
            adapter.submitList(data)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.pbMain.isVisible = isLoading
            if (isLoading == false)
                binding.srlMain.isRefreshing = false
        }

        viewModel.isError.observe(viewLifecycleOwner) { isError ->
            binding.buttonError.isVisible = isError
        }

        viewModel.navigateBack.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { mPair ->
                Toast.makeText(requireContext(), mPair.second, Toast.LENGTH_SHORT).show()
                setFragmentResult(GUEST_RESULT, Bundle().also { bundle ->
                    bundle.putString(GUEST_NAME, mPair.first)
                })
                findNavController().popBackStack()
            }
        }
    }

    override fun onClick(guest: Guest) {
        viewModel.onClick(guest)
    }
}