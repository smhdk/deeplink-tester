package com.kodmap.deeplinktester.ui.deeplink

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kodmap.deeplinktester.App
import com.kodmap.deeplinktester.core.BaseViewModel
import com.kodmap.deeplinktester.db.AppDatabase
import com.kodmap.deeplinktester.db.entities.AppEntity
import com.kodmap.deeplinktester.db.entities.DeeplinkEntity
import javax.inject.Inject

class DeeplinkActivityViewModel(app : Application) : BaseViewModel(app){
    @Inject
    lateinit var db: AppDatabase

    private var linkList: LiveData<List<DeeplinkEntity>>? = null
    var appEntity = ObservableField<AppEntity>()

    init {
        (app as? App)?.component?.inject(this)
    }

    fun getLinkList(): LiveData<List<DeeplinkEntity>> {
        if (linkList == null) {
            linkList = MutableLiveData<List<DeeplinkEntity>>()
            loadLinkList()
        }
        return linkList!!
    }

    private fun loadLinkList() {
        linkList = db.deeplinkDao().getLinksFromApp(appEntity.get()?.id!!)
    }
}