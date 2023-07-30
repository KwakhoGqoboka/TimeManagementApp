package com.example.design

class TimesheetDataClass {
    var dateLabel: String = ""
    var dateStartTime: String = ""
    var dateEndTime: String = ""
    var dateDescription: String = ""
    var dateCategory: String = ""
    var dateImage : String = ""

    constructor()

    constructor(dateLabel : String, dateStartTime : String, dateEndTime : String, dateDescription : String, dateCategory : String, dateImage : String)
    {
        this.dateLabel = dateLabel
        this.dateStartTime = dateStartTime
        this.dateEndTime = dateEndTime
        this.dateDescription = dateDescription
        this.dateCategory = dateCategory
        this.dateImage = dateImage
    }

}