package com.example.timer.main

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.timer.R
import com.example.timer.database.SleepDatabase
import com.example.timer.databinding.FragmentTrainingMainBinding
import com.example.timer.timer.MyService

class MainFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding: FragmentTrainingMainBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_training_main, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = SleepDatabase.getInstance(application).sleepDatabaseDao

        val viewModelFactory = MainViewModelFactory(dataSource, application)


        val sleepTrackerViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(MainViewModel::class.java)


        binding.mainViewModel = sleepTrackerViewModel

        binding.lifecycleOwner = this
        val adapter = TitleWorkoutAdapter(TitleWorkoutListener {
                nightId ->  sleepTrackerViewModel.onSleepNightClicked(nightId)
        })
        sleepTrackerViewModel.navigateToSleepDataQuality.observe(
            viewLifecycleOwner,
            { night ->
                night?.let {
                    this.findNavController().navigate(
                        MainFragmentDirections
                            .actionSleepTrackerFragmentToSleepDetailFragment(night)
                    )
                    sleepTrackerViewModel.onSleepDataQualityNavigated()
                }
            }
        )
        binding.sleepList.adapter = adapter
        sleepTrackerViewModel.titles.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
            binding.sleepList.smoothScrollToPosition(0)
        })
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.overflow_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,
            requireView().findNavController())
                || super.onOptionsItemSelected(item)
    }

}
