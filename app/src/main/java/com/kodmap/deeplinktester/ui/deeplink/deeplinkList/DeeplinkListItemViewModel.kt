package com.kodmap.deeplinktester.ui.deeplink.deeplinkList

import android.app.Application
import android.content.Intent
import android.net.Uri
import androidx.databinding.ObservableField
import com.kodmap.deeplinktester.App
import com.kodmap.deeplinktester.core.BaseViewModel
import com.kodmap.deeplinktester.db.entities.DeeplinkEntity

class DeeplinkListItemViewModel(app : Application) :BaseViewModel(app){
    var item = ObservableField<DeeplinkEntity>()

    fun setModel(item : DeeplinkEntity){
        this.item.set(item)
    }

    fun onClick(){
        listener.onClick(item.get(),null)
    }

    fun onLongClick() : Boolean{
        listener.onLongClick(item.get(),null)
        return true
    }
}