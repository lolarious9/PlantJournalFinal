package com.edu.lewisu.cs.quinnrafferty.plantjournal.ui.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.edu.lewisu.cs.quinnrafferty.plantjournal.Plant
import com.edu.lewisu.cs.quinnrafferty.plantjournal.PlantRepository

class GalleryViewModel : ViewModel() {

    var plants : MutableList<Plant> = mutableListOf()


}