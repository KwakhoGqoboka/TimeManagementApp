package com.example.design

class DataClass {
    var dataName: String = ""
    var dataMin: Int = 0
    var dataMax: Int = 0
    var dataDesc: String = ""
    var dataBudget: Int = 0
    var dataImage : String = ""

    constructor()

    constructor(dataName : String, dataMin : Int, dataMax : Int, dataDesc : String, dataBudget : Int, dataImage : String)
    {
        this.dataName = dataName
        this.dataMin = dataMin
        this.dataMax = dataMax
        this.dataDesc = dataDesc
        this.dataBudget = dataBudget
        this.dataImage = dataImage
    }

}