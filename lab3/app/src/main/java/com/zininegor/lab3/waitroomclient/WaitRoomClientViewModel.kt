package com.zininegor.lab3.waitroomclient

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.zininegor.lab3.waitroom.WaitRoomFragment

class WaitRoomClientViewModel : ViewModel() {

    var doneNavigation = MutableLiveData<Boolean>()
    var doneNavigationToGame = MutableLiveData<Boolean>()

    init {
        doneNavigationToGame.value = false
        doneNavigation.value = false
    }


}