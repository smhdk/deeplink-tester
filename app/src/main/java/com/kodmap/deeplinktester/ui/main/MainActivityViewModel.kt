package com.kodmap.deeplinktester.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kodmap.deeplinktester.App
import com.kodmap.deeplinktester.core.BaseViewModel
import com.kodmap.deeplinktester.db.AppDatabase
import com.kodmap.deeplinktester.db.entities.AppItem
import javax.inject.Inject

class MainActivityViewModel(app: Application) : BaseViewModel(app) {
    @Inject
    lateinit var db: AppDatabase

    private var appList: LiveData<List<AppItem>>? = null

    init {
        (app as? App)?.component?.inject(this)
    }

    fun getAppList(): LiveData<List<AppItem>> {
        if (appList == null) {
            appList = MutableLiveData<List<AppItem>>()
            loadAppItems()
        }
        return appList!!
    }

    private fun loadAppItems() {
        appList = db.appDao().getAppItems()
    }
}