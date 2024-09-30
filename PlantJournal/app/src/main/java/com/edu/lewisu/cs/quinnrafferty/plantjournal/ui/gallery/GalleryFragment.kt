package com.edu.lewisu.cs.quinnrafferty.plantjournal.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.edu.lewisu.cs.quinnrafferty.plantjournal.PlantAdapter
import com.edu.lewisu.cs.quinnrafferty.plantjournal.PlantRepository
import com.edu.lewisu.cs.quinnrafferty.plantjournal.R
import com.edu.lewisu.cs.quinnrafferty.plantjournal.databinding.FragmentGalleryBinding

class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val galleryViewModel =
            ViewModelProvider(this).get(GalleryViewModel::class.java)
        galleryViewModel.plants = PlantRepository.getInstance(requireContext()).getAllPlants()
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val onClickListener = View.OnClickListener { itemView:View->
            val symbol = itemView.tag.toString()
            val args =  Bundle()
            args.putString("plantSymbol",symbol)
            Navigation.findNavController(requireView()).navigate(R.id.plant_detail_fragment,args)

        }

        val recyclerView: RecyclerView = root.findViewById(R.id.plantsrecylerview)
        val adapter : PlantAdapter = PlantAdapter(galleryViewModel.plants,onClickListener)
        recyclerView.adapter=adapter

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}