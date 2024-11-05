package com.example.weathersphere.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.weathersphere.databinding.FragmentErrorBinding

/**
 * ErrorFragment displays an error message and title passed from other fragments.
 */
class ErrorFragment : Fragment() {

    private lateinit var binding: FragmentErrorBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentErrorBinding.inflate(inflater, container, false)

        // Retrieve title and message from arguments, providing default values
        val title = arguments?.getString("title") ?: "Error"
        val message = arguments?.getString("message") ?: "Something went wrong."

        // Set the title and message in the respective TextViews
        binding.titleTxtView.text = title
        binding.messageTxtView.text = message

        return binding.root
    }
}
