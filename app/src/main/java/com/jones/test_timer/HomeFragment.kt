package com.jones.test_timer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.jones.test_timer.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding

    private var isTimerRunning = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        lifecycleScope.launch {
            viewModel.counterValue.collect { value ->
                binding.tvCount.text = value.toString()
            }
        }


        binding.btnStartTimer.setOnClickListener {
            if (!isTimerRunning) {
                val seconds = binding.etTimerValue.text.toString().toIntOrNull()
                if (seconds != null) {
                    isTimerRunning = true
                    binding.etTimerValue.isEnabled = false
                    binding.btnStartTimer.isEnabled = false

                    viewModel.startTimer(seconds)
                    startTimerCountdown(seconds)
                }
            }
        }

    }

    private fun startTimerCountdown(seconds: Int) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeat(seconds + 1) {
                viewModel.startTimer(seconds - it)
                kotlinx.coroutines.delay(1000)
            }
            isTimerRunning = false
            binding.etTimerValue.isEnabled = true
            binding.btnStartTimer.isEnabled = true
        }
    }

}
