package com.kodmap.deeplinktester.core

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.kodmap.deeplinktester.base.AdapterClickListener

open class BaseViewModel(app: Application) : AndroidViewModel(app){
    lateinit var listener : AdapterClickListener

    fun initListener(listener : AdapterClickListener){
        this.listener = listener
    }
}
