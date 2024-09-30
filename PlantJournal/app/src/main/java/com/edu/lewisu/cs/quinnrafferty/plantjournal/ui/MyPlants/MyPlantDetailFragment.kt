package com.edu.lewisu.cs.quinnrafferty.plantjournal.ui.MyPlants

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.edu.lewisu.cs.quinnrafferty.plantjournal.MyPlants
import com.edu.lewisu.cs.quinnrafferty.plantjournal.Plant
import com.edu.lewisu.cs.quinnrafferty.plantjournal.PlantRepository
import com.edu.lewisu.cs.quinnrafferty.plantjournal.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MyPlantDetailFragment : Fragment(){
    private var plant: Plant? = null
    private lateinit var myPlantList:MyPlants
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var id = ""
        arguments?.let{id = it.getString("plantSymbol").toString()}

        // Get the selected to do
        plant = PlantRepository.getInstance(requireContext()).getPlant(id)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val rootView = inflater.inflate(R.layout.plant_detail, container, false)
        myPlantList = MyPlants(rootView.context)
        var button = rootView.findViewById<FloatingActionButton>(R.id.add_to_list)
        button.setOnClickListener{
            var plantBool = myPlantList.addPlants(plant!!)
            Log.d("MainActivity","Attempting to add plant ${plant}: Status $plantBool")
            if (plantBool){
                Toast.makeText(context,"Added ${plant!!.getSymbol()}",Toast.LENGTH_SHORT).show()
            }

        }
        if (plant != null) {
            if(!myPlantList.hasPlant(plant!!)){
                button.visibility = View.VISIBLE
            }
            val commonNameTextView = rootView.findViewById<TextView>(R.id.plant_detail_text)
            commonNameTextView.text = plant!!.getNationalCommName()
            val symbolView = rootView.findViewById<TextView>(R.id.plant_symbol_field)
            symbolView.text = plant!!.toString()
        }

        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        myPlantList.save(requireContext())
    }
}