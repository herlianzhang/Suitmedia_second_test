package com.herlianzhang.suitmedia_first_test.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.herlianzhang.suitmedia_first_test.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private var dialog: AlertDialog? = null

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
            val message = if (isPalindrome(name)) "isPalindrome" else "not palindrome"

            dialog?.dismiss()
            dialog = MaterialAlertDialogBuilder(requireContext())
                .setTitle(name)
                .setMessage(message)
                .setPositiveButton("Okay") { _, _ ->
                    val action =
                        HomeFragmentDirections.actionHomeFragmentToEventOrGuestFragment(name)
                    findNavController().navigate(action)
                }.show()
        }
    }

    private fun isPalindrome(value: String): Boolean {
        val text = value.replace(" ", "").lowercase()
        for (i in 0 until (text.count() / 2))
            if (text[i] != text[text.count() - 1 - i])
                return false
        
        return true
    }

}