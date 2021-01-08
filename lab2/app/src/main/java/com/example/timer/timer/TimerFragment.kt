package com.example.timer.timer

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import com.example.timer.others.Constants.ACTION_START_OR_RESUME_SERVICE
import android.os.Bundle
import android.text.format.DateUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.timer.R
import com.example.timer.databinding.TimerFragmentBinding
import com.example.timer.main.MainFragmentDirections
import com.example.timer.others.Constants
import com.example.timer.others.Constants.ACTION_NEXT_BUTTON
import com.example.timer.others.Constants.ACTION_PAUSE_BUTTON
import com.example.timer.others.Constants.ACTION_PREVIEW_BUTTON
import com.example.timer.others.Constants.ACTION_STOP_SERVICE


class TimerFragment : Fragment() {


    var mMediaPlayer: MediaPlayer? = null

    @SuppressLint("ResourceAsColor", "UseCompatLoadingForDrawables")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val binding: TimerFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.timer_fragment, container, false
        )

        val arguments = TimerFragmentArgs.fromBundle(requireArguments())
        MyService.key.value = arguments.sleepNightKey
        sendCommandToService(ACTION_START_OR_RESUME_SERVICE, arguments.sleepNightKey)

        (activity as AppCompatActivity).supportActionBar?.hide()

        binding.lifecycleOwner = this

        val adapter = TimerAdapter()


        binding.sleepList.adapter = adapter
        MyService.currentTime.observe(viewLifecycleOwner, Observer { newTime ->
            binding.timerText.text = DateUtils.formatElapsedTime(newTime)

        })




        MyService.cycles.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })



        MyService.nameStep.observe(viewLifecycleOwner, Observer { titleString ->
            binding.sleepList.smoothScrollToPosition(5 + MyService.currentStep.value!!)
            binding.textView2.text = titleString
            if (MyService.currentStep.value == 0) {
                MyService.currentStep.value?.let { adapter.notifyItemChanged(it) }
                MyService.currentStep.value?.let { adapter.notifyItemChanged(it + 1) }
            } else {
                MyService.currentStep.value?.let { adapter.notifyItemChanged(it - 1) }
                MyService.currentStep.value?.let { adapter.notifyItemChanged(it) }
                MyService.currentStep.value?.let { adapter.notifyItemChanged(it + 1) }
            }
        })

        MyService.currentStep.observe(viewLifecycleOwner, Observer { step ->
            if (step == 0) {
                step.let { adapter.notifyItemChanged(it) }
                step.let { adapter.notifyItemChanged(it + 1) }
            } else {
                step.let { adapter.notifyItemChanged(it - 1) }
                step.let { adapter.notifyItemChanged(it) }
                step.let { adapter.notifyItemChanged(it + 1) }
            }
        })
//
        MyService.backgroundColor.observe(viewLifecycleOwner, Observer { color ->
            binding.layout.setBackgroundColor(Color.parseColor(color.toString()))

        })
        binding.pause.background = resources.getDrawable(R.drawable.pause)
        MyService.pause.observe(viewLifecycleOwner, { pause ->
            if (pause == true) {

                binding.pause.background = resources.getDrawable(R.drawable.play)
                binding.textView2.text = resources.getString(R.string.Pause)

            } else {
                binding.textView2.text = MyService.nameStep.value
                binding.pause.background = resources.getDrawable(R.drawable.pause)

            }
        })

        binding.pause.setOnClickListener {
            Log.i("clickbutton", "Click")
        }
        binding.pause.setOnClickListener()
        {
            sendCommandToService(ACTION_PAUSE_BUTTON,arguments.sleepNightKey )
        }
        binding.right.setOnClickListener()
        {
            sendCommandToService(ACTION_NEXT_BUTTON,arguments.sleepNightKey )
        }
        binding.left.setOnClickListener()
        {
            sendCommandToService(ACTION_PREVIEW_BUTTON,arguments.sleepNightKey )
        }
        binding.close.setOnClickListener()
        {
            sendCommandToService(ACTION_STOP_SERVICE,arguments.sleepNightKey )
            this.findNavController().navigate(
                TimerFragmentDirections.actionSleepDetailFragmentToSleepTrackerFragment()
            )

        }
        return binding.root
    }


    private fun sendCommandToService(action: String, id: Long) =
        Intent(requireContext(), MyService::class.java).also {
            it.putExtra("id", id)
            it.action = action
            requireContext().startService(it)
        }


    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity).supportActionBar?.show()
    }

}

