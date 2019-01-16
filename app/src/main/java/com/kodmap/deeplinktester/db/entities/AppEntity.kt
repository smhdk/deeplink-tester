package com.kodmap.deeplinktester.db.entities

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "App")
data class AppEntity(
        @PrimaryKey(autoGenerate = true)
        var id: Int? = null,

        var name: String? = null
) : Parcelable {
        constructor(parcel: Parcel) : this(
                parcel.readValue(Int::class.java.classLoader) as? Int,
                parcel.readString()) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeValue(id)
                parcel.writeString(name)
        }

        override fun describeContents(): Int {
                return 0
        }

        companion object CREATOR : Parcelable.Creator<AppEntity> {
                override fun createFromParcel(parcel: Parcel): AppEntity {
                        return AppEntity(parcel)
                }

                override fun newArray(size: Int): Array<AppEntity?> {
                        return arrayOfNulls(size)
                }
        }
}