package com.example.americansareasleepupvotethemetricsystem.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import com.example.americansareasleepupvotethemetricsystem.R
import androidx.lifecycle.Observer
import com.example.americansareasleepupvotethemetricsystem.MainActivity
import com.example.americansareasleepupvotethemetricsystem.databinding.MainFragmentBinding
import com.example.americansareasleepupvotethemetricsystem.databinding.FragmentBlankBinding

class BlankFragment : Fragment()  {


    private lateinit var binding: FragmentBlankBinding


    private lateinit var viewModel: MainViewModel



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_blank,
                container,
                false
        )

        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        binding.btn0.setOnClickListener { viewModel.onCorrect(binding.btn0.text.toString()) }
        binding.btn1.setOnClickListener { viewModel.onCorrect(binding.btn1.text.toString()) }
        binding.btn2.setOnClickListener { viewModel.onCorrect(binding.btn2.text.toString()) }
        binding.btn3.setOnClickListener { viewModel.onCorrect(binding.btn3.text.toString()) }
        binding.btn4.setOnClickListener { viewModel.onCorrect(binding.btn4.text.toString()) }
        binding.btn5.setOnClickListener { viewModel.onCorrect(binding.btn5.text.toString()) }
        binding.btn6.setOnClickListener { viewModel.onCorrect(binding.btn6.text.toString()) }
        binding.btn7.setOnClickListener { viewModel.onCorrect(binding.btn7.text.toString()) }
        binding.btn8.setOnClickListener { viewModel.onCorrect(binding.btn8.text.toString()) }
        binding.btn9.setOnClickListener { viewModel.onCorrect(binding.btn9.text.toString()) }
        binding.btnDot.setOnClickListener { viewModel.addDot() }
        binding.btnDelete.setOnClickListener { viewModel.delete() }
        binding.btnDelete.setOnLongClickListener { viewModel.deleteall()}
        return binding.root

    }
}