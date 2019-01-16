package com.kodmap.deeplinktester.db.entities

import android.os.Parcel
import android.os.Parcelable
import androidx.databinding.ObservableField
import androidx.room.Embedded
import androidx.room.Relation
import java.util.*

data class AppItem(
        @Embedded
        var app : AppEntity? = null,

        @Relation(parentColumn = "id", entityColumn = "appId", entity = DeeplinkEntity::class)
        var linkList : List<DeeplinkEntity>? = emptyList()
)  :Parcelable {

        fun getDeeplinkSize() : String{
                return "${linkList?.size} link"
        }
        constructor(parcel: Parcel) : this(
                parcel.readParcelable(AppEntity::class.java.classLoader),
                parcel.createTypedArrayList(DeeplinkEntity))

        override fun writeToParcel(parcel: Parcel, flags: Int) {

        }

        override fun describeContents(): Int {
                return 0
        }

        companion object CREATOR : Parcelable.Creator<AppItem> {
                override fun createFromParcel(parcel: Parcel): AppItem {
                        return AppItem(parcel)
                }

                override fun newArray(size: Int): Array<AppItem?> {
                        return arrayOfNulls(size)
                }
        }
}