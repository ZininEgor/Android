package com.zininegor.lab3.menu

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zininegor.lab3.others.Case

class MenuViewModel : ViewModel() {

    var doneNavigation = MutableLiveData<Boolean>()
    var list = MutableLiveData<MutableList<Case>>()

    init {

        doneNavigation.value = false
    }
}