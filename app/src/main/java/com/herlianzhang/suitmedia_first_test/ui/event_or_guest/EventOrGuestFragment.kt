package com.herlianzhang.suitmedia_first_test.ui.event_or_guest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.herlianzhang.suitmedia_first_test.R
import com.herlianzhang.suitmedia_first_test.databinding.FragmentEventOrGuestBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EventOrGuestFragment : Fragment() {

    private lateinit var binding: FragmentEventOrGuestBinding
    private val viewModel: EventOrGuestViewModel by viewModels()

    private val args: EventOrGuestFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEventOrGuestBinding.inflate(inflater, container, false)

        viewModel.setName(args.name)

        initListener()
        initObserver()

        return binding.root
    }

    private fun initListener() {
        binding.buttonEvent.setOnClickListener {
            findNavController().navigate(R.id.action_eventOrGuestFragment_to_eventFragment)
        }

        binding.buttonGuest.setOnClickListener {
            findNavController().navigate(R.id.action_eventOrGuestFragment_to_guestFragment)
        }

        setFragmentResultListener(EVENT_RESULT) { _, bundle ->
            val eventName = bundle.getString(EVENT_NAME)
            if (eventName != null)
                viewModel.setEvent(eventName)
        }

        setFragmentResultListener(GUEST_RESULT) { _, bundle ->
            val guestName = bundle.getString(GUEST_NAME)
            if (guestName != null)
                viewModel.setGuest(guestName)
        }
    }

    private fun initObserver() {
        viewModel.name.observe(viewLifecycleOwner) { name ->
            binding.tvName.text = name
        }

        viewModel.event.observe(viewLifecycleOwner) { event ->
            binding.buttonEvent.text = requireContext().getString(R.string.event_name, event)
        }

        viewModel.guest.observe(viewLifecycleOwner) { event ->
            binding.buttonGuest.text = requireContext().getString(R.string.guest_name, event)
        }
    }

    companion object {
        const val EVENT_RESULT = "event_result"
        const val EVENT_NAME = "event_name"

        const val GUEST_RESULT = "guest_result"
        const val GUEST_NAME = "guest_name"
    }
}