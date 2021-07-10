package com.herlianzhang.suitmedia_first_test.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.herlianzhang.suitmedia_first_test.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        initListener()

        return binding.root
    }

    private fun initListener() {
        binding.buttonMain.setOnClickListener {
            val name = binding.etMain.text.toString().trim()
            val action = HomeFragmentDirections.actionHomeFragmentToEventOrGuestFragment(name)
            findNavController().navigate(action)
        }
    }

}