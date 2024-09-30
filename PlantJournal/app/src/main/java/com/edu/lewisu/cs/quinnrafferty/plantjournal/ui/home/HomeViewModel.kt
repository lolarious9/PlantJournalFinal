package com.edu.lewisu.cs.quinnrafferty.plantjournal.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.edu.lewisu.cs.quinnrafferty.plantjournal.Plant
import com.edu.lewisu.cs.quinnrafferty.plantjournal.PlantRepository
import java.util.ArrayList

class HomeViewModel : ViewModel() {
     val searchResult = MutableLiveData<MutableList<Plant>>()
     var plantRepository: PlantRepository? = null
    var commonName:String = ""
    var sciName:String = ""
    var symbol:String = ""

}