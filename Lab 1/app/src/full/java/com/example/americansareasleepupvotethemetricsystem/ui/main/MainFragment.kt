package com.example.americansareasleepupvotethemetricsystem.ui.main

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.getSystemService
import androidx.databinding.DataBindingUtil
import com.example.americansareasleepupvotethemetricsystem.R
import androidx.lifecycle.Observer
import com.example.americansareasleepupvotethemetricsystem.MainActivity
import com.example.americansareasleepupvotethemetricsystem.databinding.MainFragmentBinding

class MainFragment : Fragment(), AdapterView.OnItemSelectedListener  {


    private lateinit var binding: MainFragmentBinding


    private lateinit var viewModel: MainViewModel

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {

        when(parent.id)
        {
            binding.spinner2.id -> {
                viewModel.Converter(from = binding.spinner2.getItemAtPosition(pos).toString(), to = binding.spinner3.selectedItem.toString())
            }
            binding.spinner3.id ->
            {
                viewModel.Converter(from = binding.spinner2.selectedItem.toString(), to = binding.spinner3.getItemAtPosition(pos).toString())
            }
            binding.spinner.id -> {
                viewModel.mainCategory = binding.spinner.getItemAtPosition(pos).toString()
                var from_text: Array<String> = resources.getStringArray(R.array.Weight_SI)
                var to_text: Array<String> = resources.getStringArray(R.array.Weight_SI)
                when (viewModel.mainCategory)
                {
                    "Weight"-> {
                        from_text = resources.getStringArray(R.array.Weight_SI)
                        to_text = resources.getStringArray(R.array.Weight_USA)
                    }
                    "Length"-> {
                        from_text = resources.getStringArray(R.array.Length_SI)
                        to_text = resources.getStringArray(R.array.Length_USA)
                    }
                    "Speed"-> {
                        from_text = resources.getStringArray(R.array.Speed_SI)
                        to_text = resources.getStringArray(R.array.Speed_USA)
                    }
                }
                var from = ArrayAdapter<String>(requireActivity(),android.R.layout.simple_spinner_item, from_text)
                var to = ArrayAdapter<String>(requireActivity(),android.R.layout.simple_spinner_item, to_text)
                from.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                to.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinner2.adapter = from
                binding.spinner3.adapter = to
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.main_fragment,
                container,
                false
        )

        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        viewModel.input.observe(viewLifecycleOwner, Observer { Input ->
            binding.TextInput.text = Input.toString()
        })
        viewModel.output.observe(viewLifecycleOwner, Observer { OutPut ->
            binding.textOutput.text = OutPut.toString()
        })
        binding.spinner.onItemSelectedListener = this
        binding.spinner2.onItemSelectedListener = this
        binding.spinner3.onItemSelectedListener = this
        binding.button.setOnClickListener { copyText(binding.TextInput)}
        binding.button2.setOnClickListener {copyText(binding.textOutput)}
        binding.button3.setOnClickListener {swap()}
        return binding.root

    }
    fun swap(){

        var spawn = binding.spinner2.adapter
        binding.spinner2.adapter = binding.spinner3.adapter
        binding.spinner3.adapter = spawn

    }
    fun copyText(view: TextView) {
        val textToCopy = view.text
        val clipboardManager = context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("text", textToCopy)
        clipboardManager.setPrimaryClip(clipData)
        Toast.makeText(context, "Text copied to clipboard", Toast.LENGTH_LONG).show()
    }
}