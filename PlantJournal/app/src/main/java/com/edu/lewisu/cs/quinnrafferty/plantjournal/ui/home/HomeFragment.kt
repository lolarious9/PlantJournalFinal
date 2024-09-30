package com.edu.lewisu.cs.quinnrafferty.plantjournal.ui.home

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.edu.lewisu.cs.quinnrafferty.plantjournal.Plant
import com.edu.lewisu.cs.quinnrafferty.plantjournal.PlantAdapter
import com.edu.lewisu.cs.quinnrafferty.plantjournal.PlantRepository
import com.edu.lewisu.cs.quinnrafferty.plantjournal.R
import com.edu.lewisu.cs.quinnrafferty.plantjournal.databinding.FragmentHomeBinding
import java.io.File
var TAG ="MainActivity"
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var submitButton :Button
    private lateinit var commonNameEdit : EditText
    private lateinit var sciNameEdit :EditText
    private  lateinit var symbolNameEdit:EditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        if(homeViewModel.plantRepository == null){
            homeViewModel.plantRepository = PlantRepository.getInstance(root.context)
        }







        val onClickListener = View.OnClickListener { itemView:View->
            val symbolString = itemView.tag.toString()
            val args =  Bundle()
            args.putString("plantSymbol",symbolString)
            Navigation.findNavController(itemView).navigate(R.id.plant_detail_fragment,args)
        }



        commonNameEdit = root.findViewById(R.id.common_name_edit_text)
        commonNameEdit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
               homeViewModel.commonName = s.toString()
                Log.d(TAG,"Updated game name to $s")
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        sciNameEdit =  root.findViewById(R.id.sci_name_edit_text)
        sciNameEdit.addTextChangedListener(object :TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                homeViewModel.commonName = s.toString()
                Log.d(TAG, "Updated comment to $s")
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
        symbolNameEdit =  root.findViewById(R.id.symbol_edit_text)
        symbolNameEdit.addTextChangedListener(object :TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                homeViewModel.symbol = s.toString()
                Log.d(TAG, "Updated comment to $s")
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        submitButton = root.findViewById(R.id.submit_button)
        submitButton.setOnClickListener{
            var tmpResults : Map<String, Plant>
            var tmpAllResult: MutableList<Plant> = mutableListOf()
            if(homeViewModel.symbol != "" ){
                tmpResults = homeViewModel.plantRepository!!.findPlantsBySymbol(homeViewModel.symbol )
                if(tmpResults.isNotEmpty()){
                    tmpAllResult.addAll(tmpResults.values)
                }
            }
            if(homeViewModel.commonName != "" ){
                tmpResults = homeViewModel.plantRepository!!.findPlantsByCommonName(homeViewModel.commonName)
                if(tmpResults.isNotEmpty()){
                    tmpAllResult.addAll(tmpResults.values)
                }
            }
            if(homeViewModel.commonName != "" ){
                tmpResults = homeViewModel.plantRepository!!.findPlantsBySciName(homeViewModel.commonName)
                if(tmpResults.isNotEmpty()){
                    tmpAllResult.addAll(tmpResults.values)
                }

             }

            Toast.makeText(context,"Rating Submitted", Toast.LENGTH_SHORT).show()



            if (tmpAllResult.isNotEmpty()){
                if(tmpAllResult.size == 1){
                     val args =  Bundle()
                    args.putString("plantSymbol",tmpAllResult[0].getSymbol())
                               Navigation.findNavController(requireView()).navigate(R.id.plant_detail_fragment,args)
                }
                homeViewModel.searchResult.value = tmpAllResult
                val adapter : PlantAdapter = PlantAdapter(homeViewModel.searchResult.value!!.toList(),onClickListener)
                val recyclerView: RecyclerView = root.findViewById(R.id.plants_search_results)

                recyclerView.adapter=adapter
                recyclerView.visibility=View.VISIBLE
            }
            else{
                requireView().findViewById<TextView>(R.id.confirmation_text_view).text =
                    getString(R.string.sorry_no_plants_found)
            }
        }




        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}