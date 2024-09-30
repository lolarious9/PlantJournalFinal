package com.edu.lewisu.cs.quinnrafferty.plantjournal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PlantAdapter(private val ratingList:List<Plant>,private val onClickListener: View.OnClickListener):
    RecyclerView.Adapter<PlantAdapter.PlantViewHolder> (){


    inner class PlantViewHolder(inflater: LayoutInflater, parent: ViewGroup?):
        RecyclerView.ViewHolder(inflater.inflate(R.layout.list_item_plant, parent,false)){
        private  val ratingTitleView : TextView = itemView.findViewById(R.id.plantSymbol)



        fun bind(plant: Plant) {
            val str="${plant.getSymbol()}. ${plant.getNationalCommName()}:${plant.getSciNameWithAuthor()}"
            ratingTitleView.text = str

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        val  layoutInflater = LayoutInflater.from(parent.context)
        return PlantViewHolder(layoutInflater,parent)
    }

    override fun getItemCount(): Int {
        return ratingList.size
    }

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        val rating = ratingList[position]
        holder.bind(rating)
        holder.itemView.tag = rating.getSymbol()
        holder.itemView.setOnClickListener(onClickListener)
    }
}