package com.herlianzhang.suitmedia_first_test.ui.event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.herlianzhang.suitmedia_first_test.databinding.FragmentEventBinding
import com.herlianzhang.suitmedia_first_test.ui.event_or_guest.EventOrGuestFragment.Companion.EVENT_NAME
import com.herlianzhang.suitmedia_first_test.ui.event_or_guest.EventOrGuestFragment.Companion.EVENT_RESULT
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EventFragment : Fragment(), EventAdapter.OnClickListener {

    private lateinit var binding: FragmentEventBinding
    private val viewModel: EventViewModel by viewModels()

    private val adapter = EventAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEventBinding.inflate(inflater, container, false)

        initAdapter()
        initObserver()

        return binding.root
    }

    private fun initAdapter() {
        binding.rvMain.adapter = adapter
    }

    private fun initObserver() {
        viewModel.data.observe(viewLifecycleOwner) { data ->
            adapter.submitList(data)
        }
    }

    override fun onClick(name: String) {
        setFragmentResult(EVENT_RESULT, Bundle().also {
            it.putString(EVENT_NAME, name)
        })
        findNavController().popBackStack()
    }
}