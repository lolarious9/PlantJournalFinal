package com.edu.lewisu.cs.quinnrafferty.plantjournal

import android.content.Context
import android.util.Log
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.ArrayList
import java.util.Date

class MyPlants(context: Context) {
    private var plantList :MutableList<WrapMyPlant> = mutableListOf()
    private var hasChanged = false
    private var startSize=0
    inner class WrapMyPlant(private var myPlantID:Int, symbol:String, private var synSymbol:String, private var sciNameWithAuthor:String, private var nationalCommonName:String, private var family:String){
       fun getID():Int{
           return this.myPlantID
       }
        fun getPlant():Plant{
            return this.plant
        }
        private var plant:Plant = Plant(symbol,synSymbol,sciNameWithAuthor,nationalCommonName,family)
        private var notes:MutableList<Pair<String,String>> = mutableListOf()
        fun addNote(note:String):Boolean {

                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                val current = LocalDateTime.now().format(formatter)


            return  notes.add(Pair(current,note))
        }
        fun addNote(date:LocalDateTime,note: String) :Boolean{
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

            return notes.add(Pair(date.format(formatter),note))
        }
    }
    fun hasPlant(plant:Plant):Boolean{
        val plantResult = plantList.find { it.getPlant() == plant }
        return plantResult != null
    }
    fun getPlantAmount():Int{
        return this.plantList.size
    }
    fun getListOfPlantsAsPlant():List<Plant>{
        return plantList.map { it.getPlant() }
    }
    fun getListOfPlants():MutableList<WrapMyPlant>{
        return plantList
    }
    fun save(ctx:Context){

        if(hasChanged){
            val fileToSaveTo = ctx.openFileOutput("MyPlants",Context.MODE_PRIVATE).bufferedWriter()
            for (i in startSize..<plantList.size) {
                var tmp:String =
                    plantList[i].getID().toString() +","+
                            plantList[i].getPlant().getSymbol() + "," + plantList[i].getPlant().getSynSymbol() + "," + plantList[i].getPlant().getSciNameWithAuthor() +","
              plantList[i].getPlant().getNationalCommName() +"," + plantList[i].getPlant().getFamily()
                fileToSaveTo.write(tmp)
                Log.d("MyPlants","saved $tmp")
            }
            fileToSaveTo.close()
        }
    }
    fun addPlant(symbol:String, synSymbol:String,  sciNameWithAuthor:String, nationalCommonName:String, family:String):Boolean
    {
        hasChanged= true
        if(plantList.isEmpty()){
            return plantList.add(WrapMyPlant(0,symbol,synSymbol,sciNameWithAuthor,nationalCommonName,family))
        }
        return plantList.add(WrapMyPlant((plantList.last().getID()+1),symbol,synSymbol,sciNameWithAuthor,nationalCommonName,family))
    }
    fun addPlants(plant:Plant):Boolean{
        return addPlant(plant.getSymbol(),plant.getSynSymbol(),plant.getSciNameWithAuthor(),plant.getNationalCommName(),plant.getFamily())
    }
    fun load(context:Context){
        if(context.fileList().contains("MyPlants")) {
            val reader = context.openFileInput("MyPlants").bufferedReader()
            reader.forEachLine {
                Log.d("MyPlants","attempting to load $it")
                val plantData = it.split(",")
                if (plantData.isEmpty()) {
                    throw Error("Plant Data Couldn't be Found!")
                }
                val id = plantData[0].toInt()
                if (plantData.size >= 6) {
                    plantList.add(
                        WrapMyPlant(
                            id,
                            plantData[1],
                            plantData[2],
                            plantData[3],
                            plantData[4],
                            plantData[5]
                        )
                    )
                }

                startSize = plantList.size
            }

        }
    }
    init {
         load(context)

        }



}