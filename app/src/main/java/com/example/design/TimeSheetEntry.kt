package com.example.design

import android.os.Parcel
import android.os.Parcelable


class TimeSheetEntry(
    val date: String?,
    val startTime: String?,
    val endTime: String?,
    val description: String?,
    val category: String?,
    val photoPath: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(date)
        parcel.writeString(startTime)
        parcel.writeString(endTime)
        parcel.writeString(description)
        parcel.writeString(category)
        parcel.writeString(photoPath)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TimeSheetEntry> {
        override fun createFromParcel(parcel: Parcel): TimeSheetEntry {
            return TimeSheetEntry(parcel)
        }

        override fun newArray(size: Int): Array<TimeSheetEntry?> {
            return arrayOfNulls(size)
        }
    }
}