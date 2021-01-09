package com.example.timer.workoutdetail

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.DrawableCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.timer.R
import com.example.timer.database.SleepDatabase
import com.example.timer.databinding.FragmentDetailBinding


class DetailFragment : Fragment() {

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val binding: FragmentDetailBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_detail, container, false
        )

        val application = requireNotNull(this.activity).application
        val arguments = DetailFragmentArgs.fromBundle(requireArguments())

        val dataSource = SleepDatabase.getInstance(application).sleepDatabaseDao
        val viewModelFactory = DetailViewModelFactory(arguments.sleepNightKey, dataSource)

        val sleepDetailViewModel =
            ViewModelProvider(this, viewModelFactory).get(DetailViewModel::class.java)

        binding.detailViewModel = sleepDetailViewModel

        binding.lifecycleOwner = this

        sleepDetailViewModel.navigateToTimer.observe(viewLifecycleOwner,
            {
                if (it == true) {
                    this.findNavController().navigate(
                        DetailFragmentDirections.actionSleepDetailFragmentToTimerFragment(
                            sleepDetailViewModel.sleepNightKey
                        )
                    )
                    sleepDetailViewModel.doneNavigatingToTimer()
                    sleepDetailViewModel.doneNavigating(binding.title.text.toString())
                }
                sleepDetailViewModel.save(binding.title.text.toString())
            })

        fun setalUsualButtons()
        {
            var unwrappedDrawable = activity?.let { it1 -> AppCompatResources.getDrawable(it1.applicationContext, R.drawable.buttonshape) }
            var wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable!!)
            DrawableCompat.setTint(wrappedDrawable, Color.parseColor("#cb75fd"))
            binding.buttonColor1.background = wrappedDrawable

            unwrappedDrawable = activity?.let { it1 -> AppCompatResources.getDrawable(it1.applicationContext, R.drawable.buttonshape) }
            wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable!!)
            DrawableCompat.setTint(wrappedDrawable, Color.parseColor("#ffcb00"))

            binding.buttonColor2.background = wrappedDrawable

            unwrappedDrawable = activity?.let { it1 -> AppCompatResources.getDrawable(it1.applicationContext, R.drawable.buttonshape) }
            wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable!!)
            DrawableCompat.setTint(wrappedDrawable, Color.parseColor("#b01717"))

            binding.buttonColor3.background = wrappedDrawable

            unwrappedDrawable = activity?.let { it1 -> AppCompatResources.getDrawable(it1.applicationContext, R.drawable.buttonshape) }
            wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable!!)
            DrawableCompat.setTint(wrappedDrawable, Color.parseColor("#0b09ae"))
            binding.buttonColor4.background = wrappedDrawable
            unwrappedDrawable = activity?.let { it1 -> AppCompatResources.getDrawable(it1.applicationContext, R.drawable.buttonshape) }

            wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable!!)
            DrawableCompat.setTint(wrappedDrawable, Color.parseColor("#198c19"))
            binding.buttonColor5.background = wrappedDrawable
        }
        sleepDetailViewModel.navigateToSleepTracker.observe(viewLifecycleOwner, {
            if (it == true) {
                this.findNavController().navigate(
                    DetailFragmentDirections.actionSleepDetailFragmentToSleepTrackerFragment()
                )
                sleepDetailViewModel.doneNavigating(binding.title.text.toString())
            }
            sleepDetailViewModel.save(binding.title.text.toString())
        })

        sleepDetailViewModel.readyString.observe(viewLifecycleOwner, { readyString ->
            binding.ready.setText(readyString)
        })
        sleepDetailViewModel.workString.observe(viewLifecycleOwner, { titleString ->
            binding.workString.setText(titleString)
        })
        sleepDetailViewModel.chillString.observe(viewLifecycleOwner, { titleString ->
            binding.chill.setText(titleString)
        })
        sleepDetailViewModel.cycleString.observe(viewLifecycleOwner, { titleString ->
            binding.cycle.setText(titleString)
        })
        sleepDetailViewModel.setString.observe(viewLifecycleOwner, { titleString ->
            binding.set.setText(titleString)
        })
        sleepDetailViewModel.chillWithSetString.observe(viewLifecycleOwner, { titleString ->
            binding.chillWithSet.setText(titleString)
        })

        fun setColor(): Drawable? {
            setalUsualButtons()
            val unwrappedDrawable = activity?.let { it1 -> AppCompatResources.getDrawable(it1.applicationContext, R.drawable.done) }
            val wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable!!)
            DrawableCompat.setTint(wrappedDrawable, Color.parseColor(sleepDetailViewModel.color.value))
            return wrappedDrawable
        }
        sleepDetailViewModel.color.observe(viewLifecycleOwner, {
            if(sleepDetailViewModel.color.value == "#cb75fd")
            {
                binding.buttonColor1.background = setColor()
            }
            if(sleepDetailViewModel.color.value == "#ffcb00")
            {
                binding.buttonColor2.background = setColor()
            }
            if(sleepDetailViewModel.color.value == "#b01717")
            {
                binding.buttonColor3.background = setColor()
            }
            if(sleepDetailViewModel.color.value == "#0b09ae")
            {
                binding.buttonColor4.background = setColor()
            }
            if(sleepDetailViewModel.color.value == "#198c19")
            {
                binding.buttonColor5.background = setColor()
            }
        })
        binding.buttonColor1.setOnClickListener()
        {
            sleepDetailViewModel.color_button1()
        }
        binding.buttonColor1.setOnClickListener()
        {
            sleepDetailViewModel.color_button1()
        }
        binding.buttonColor2.setOnClickListener()
        {
            sleepDetailViewModel.color_button2()
        }
        binding.buttonColor3.setOnClickListener()
        {
            sleepDetailViewModel.color_button3()
        }
        binding.buttonColor4.setOnClickListener()
        {
            sleepDetailViewModel.color_button4()
        }
        binding.buttonColor5.setOnClickListener()
        {
            sleepDetailViewModel.color_button5()
        }

        binding.button12.setOnClickListener()
        {
            sleepDetailViewModel.save(binding.title.text.toString())
        }
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.overflow_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(
            item,
            requireView().findNavController()
        )
                || super.onOptionsItemSelected(item)
    }


}
