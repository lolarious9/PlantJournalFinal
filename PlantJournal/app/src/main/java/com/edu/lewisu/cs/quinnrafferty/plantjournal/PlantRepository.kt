package com.edu.lewisu.cs.quinnrafferty.plantjournal

import android.content.Context
import android.util.Log
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.util.Hashtable
import java.util.SortedMap

class PlantRepository private constructor(context:Context){
    private var plantList : MutableList<Plant> = mutableListOf()
    private  var plantSymbols :   Hashtable<String,Plant> = Hashtable(100000)
    private  var plantCommonName: Hashtable<String,Plant> = Hashtable(40000)
    private  var plantSciName : Hashtable<String,Plant>  = Hashtable(40000)
    companion object {
        private var instance: PlantRepository? = null

        fun getInstance(context: Context): PlantRepository {
            if (instance == null) {
                instance = PlantRepository(context)
            }
            return instance!!
        }
    }
   private fun cleanseString(strIn:String):String{
       return strIn.replace("\"","")
    }
    fun findPlantsBySymbol(symbolIn:String): Map<String, Plant> {
        return plantSymbols.filter{ it.key.contains(cleanseString(symbolIn),true) }
    }
    fun findPlantsByCommonName(nameIn:String): Map<String, Plant> {
        return plantCommonName.filter{it.key.contains(cleanseString(nameIn),true)}
    }
    fun findPlantsBySciName(sciIn:String): Map<String, Plant> {
        return plantSciName.filter{it.key.contains(cleanseString(sciIn),true)}
    }
    init {
        Log.d("test","$context.fileList()")

        val file = BufferedReader(InputStreamReader(context.assets.open("Plants.csv")))

        file.forEachLine{
            var plantData = it.split(",")
            plantData = plantData.map{cleanseString(it)}
            if(plantData.isEmpty()){
                throw Error("Plant Data Couldn't be Found!")
            }
            if(plantData.size==5 && plantList.add(Plant(plantData[0],plantData[1],plantData[2],plantData[3],plantData[4]))) {
                plantSymbols[plantData[0]] = plantList.last()
                plantCommonName[plantData[2]] = plantList.last()
                plantSciName[plantData[3]]=plantList.last()
            }
        }

        file.close()
    }
    fun getAllPlants(): MutableList<Plant>{
        return this.plantList
    }
    fun getPlant(symbolSearch:String): Plant? {
       val result = plantList.find { it.getSymbol() == symbolSearch}
        Log.d("MainActivity","Getting $result")
        return result
    }

}