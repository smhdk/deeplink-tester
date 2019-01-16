package com.kodmap.deeplinktester.db.entities

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Deeplink")
data class DeeplinkEntity(
        @PrimaryKey(autoGenerate = true)
        var id : Int? = null,

        var appId: Int? = null,

        var link : String? = null
): Parcelable {
        constructor(parcel: Parcel) : this(
                parcel.readValue(Int::class.java.classLoader) as? Int,
                parcel.readValue(Int::class.java.classLoader) as? Int,
                parcel.readString()) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeValue(id)
                parcel.writeValue(appId)
                parcel.writeString(link)
        }

        override fun describeContents(): Int {
                return 0
        }

        companion object CREATOR : Parcelable.Creator<DeeplinkEntity> {
                override fun createFromParcel(parcel: Parcel): DeeplinkEntity {
                        return DeeplinkEntity(parcel)
                }

                override fun newArray(size: Int): Array<DeeplinkEntity?> {
                        return arrayOfNulls(size)
                }
        }
}