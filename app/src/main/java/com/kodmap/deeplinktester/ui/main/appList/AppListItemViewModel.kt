package com.kodmap.deeplinktester.ui.main.appList

import android.app.Application
import androidx.databinding.ObservableField
import com.kodmap.deeplinktester.core.BaseViewModel
import com.kodmap.deeplinktester.db.entities.AppItem

class AppListItemViewModel(app : Application) :BaseViewModel(app){
    var appItem = ObservableField<AppItem>()

    fun setModel(model : AppItem){
        appItem.set(model)
    }

    fun onClick(){
        listener.onClick(appItem.get(),null)
    }
}