package com.example.threelinetest

import android.os.Parcel
import android.os.Parcelable

class Weather(var cloud : String?, var description : String?, var humidity : Int) :
    Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readInt()
    ) {
    }

    override fun toString(): String {
        return "Photo(cloud='$cloud', description='$description', humidity=$humidity)"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(cloud)
        parcel.writeString(description)
        parcel.writeInt(humidity)

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Weather> {
        override fun createFromParcel(parcel: Parcel): Weather {
            return Weather(parcel)
        }

        override fun newArray(size: Int): Array<Weather?> {
            return arrayOfNulls(size)
        }
    }
}