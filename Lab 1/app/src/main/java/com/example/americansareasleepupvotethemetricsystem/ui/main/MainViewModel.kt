package com.example.americansareasleepupvotethemetricsystem.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel



class MainViewModel : ViewModel() {
    val output = MutableLiveData<String>()
    val input = MutableLiveData<String>()
    var mainCategory = String()


    init {
        output.value = ""
        input.value = ""
    }

    fun Converter(from: String, to: String)
    {
        if (input.value?.length!! == 0)
        {
            return
        }
        Log.d("MyApp", from + to)
        var meadleware = 0.0
        when (mainCategory) {
            "Length" -> {
                when (from) {
                    "Millimeters" -> { meadleware = input.value?.toDouble()!! * 10e-4 }
                    "Centimeters" -> { meadleware = input.value?.toDouble()!! * 10e-3 }
                    "Meters" -> { meadleware = input.value?.toDouble()!! }
                    "Kilometers" -> { meadleware = input.value?.toDouble()!! * 10e2 }
                    "Inch" -> { meadleware = input.value?.toDouble()!! / 12  }
                    "Foot" -> { meadleware = input.value?.toDouble()!! }
                    "Yard" -> { meadleware = input.value?.toDouble()!! * 3 }
                    "Mile" -> { meadleware = input.value?.toDouble()!! / 1760 }
                }
                when (to) {
                    "Inch" -> { output.value = (meadleware * 39.37).toString() }
                    "Foot" -> { output.value = (meadleware * 3.281).toString() }
                    "Yard" -> { output.value = (meadleware * 1.09361).toString() }
                    "Mile" -> { output.value = (meadleware / 1609).toString() }
                    "Millimeters" -> { output.value  = (meadleware * 304).toString() }
                    "Centimeters" -> { output.value  = (meadleware * 30.48).toString() }
                    "Meters" -> { output.value  = (meadleware / 3.281).toString() }
                    "Kilometers" -> { output.value = (meadleware / 3281).toString() }

                }
            }
            "Speed" -> {
                when (from) {
                    "m/s" -> { meadleware = input.value?.toDouble()!! * 3.6 }
                    "km/h" -> { meadleware = input.value?.toDouble()!! }
                    "Knot" -> { meadleware = input.value?.toDouble()!! * 1.852 }
                    "ft/s" -> { meadleware = input.value?.toDouble()!! * 1.097 }
                    "mi/h" -> { meadleware = input.value?.toDouble()!! * 1.609 }
                }
                when (to) {
                    "Knot" -> { output.value = (meadleware / 1.852).toString() }
                    "ft/s" -> { output.value = (meadleware / 1.097).toString() }
                    "mi/h" -> { output.value = (meadleware / 1.609).toString() }
                    "m/s" -> { output.value = (meadleware / 3.6).toString() }
                    "km/h" -> { output.value = meadleware.toString() }
                }
            }
            "Weight" -> {
                when (from) {
                    "Milligram" -> { meadleware = input.value?.toDouble()!! / 10e-6 }
                    "Gram" -> { meadleware = input.value?.toDouble()!! / 1000 }
                    "Kilogram" -> { meadleware = input.value?.toDouble()!! }
                    "Stone" -> {meadleware  = input.value?.toDouble()!! * 6.35 }
                    "Pound" -> { meadleware = input.value?.toDouble()!! / 2.205 }
                    "Ounce" -> { meadleware = input.value?.toDouble()!! / 35.274 }

                }
                when (to) {
                    "Stone" -> { output.value = (meadleware / 6.35).toString() }
                    "Pound" -> { output.value = (meadleware * 2.205).toString() }
                    "Ounce" -> { output.value = (meadleware * 35.274).toString() }
                    "Milligram" -> { output.value = (meadleware * 10e-6).toString() }
                    "Gram" -> { output.value = (meadleware * 1000).toString() }
                    "Kilogram" -> { output.value = meadleware.toString() }
                }
            }
        }
    }

    fun onCorrect(str: String)
    {
        if (input.value?.length!! <= 12)
        {
            input.value = input.value + str
        }
    }

    fun delete()
    {
        input.value = input.value?.dropLast(1)
    }

    fun addDot()
    {
        if (input.value?.all{ it.isDigit()}!! and input.value?.isNotEmpty()!!){
            input.value = input.value + "."
        }

    }

    fun deleteall(): Boolean
    {
        input.value = ""
        output.value = ""
        return true
    }

}