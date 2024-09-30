package com.edu.lewisu.cs.quinnrafferty.plantjournal.ui.MyPlants

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.edu.lewisu.cs.quinnrafferty.plantjournal.MyPlants
import com.edu.lewisu.cs.quinnrafferty.plantjournal.PlantAdapter
import com.edu.lewisu.cs.quinnrafferty.plantjournal.R
import com.edu.lewisu.cs.quinnrafferty.plantjournal.databinding.FragmentMyPlantsBinding


class MyPlantsFragment : Fragment() {

    private var _binding: FragmentMyPlantsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val myPlantViewModel =
            ViewModelProvider(this).get(SlideshowViewModel::class.java)

        _binding = FragmentMyPlantsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val onClickListener = View.OnClickListener { itemView:View->
            val symbol = itemView.tag.toString()
            val args =  Bundle()
            args.putString("plantSymbol",symbol)
            Navigation.findNavController(requireView()).navigate(R.id.plant_detail_fragment,args)
        }
        var myPlants = MyPlants(requireContext())
        val recyclerView: RecyclerView = root.findViewById(R.id.myplantsrecylerview)
        if(myPlants.getPlantAmount() >0){
            root.findViewById<TextView>(R.id.empty_plant_list).visibility = View.GONE
        }
        val adapter : PlantAdapter = PlantAdapter(myPlants.getListOfPlantsAsPlant(),onClickListener)

        recyclerView.adapter=adapter
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}