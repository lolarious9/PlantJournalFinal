package com.edu.lewisu.cs.quinnrafferty.plantjournal

import java.io.File

class Plant(private var symbol:String, private var synSymbol:String, private var sciNameWithAuthor:String, private var nationalCommonName:String, private var family:String){
    fun getSymbol():String{
        return this.symbol
    }
    fun getSynSymbol():String{
        return this.synSymbol
    }
    fun getSciNameWithAuthor():String{
        return this.sciNameWithAuthor
    }
    fun getNationalCommName():String{
        return this.nationalCommonName
    }
    fun getFamily():String{
        return this.family
    }
    override fun toString(): String {
        return """
            symbol = $symbol
            synSymbol = $synSymbol
            sciNameWithAuthor = $sciNameWithAuthor
            nationalCommonName = $nationalCommonName
            family = $family
        """
    }
}
