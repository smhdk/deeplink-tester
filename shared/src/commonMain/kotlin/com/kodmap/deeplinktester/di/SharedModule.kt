package com.kodmap.deeplinktester.di

import com.kodmap.deeplinktester.data.repository.DeeplinkRepository
import com.kodmap.deeplinktester.ui.applist.AppListViewModel
import com.kodmap.deeplinktester.ui.deeplinkdetail.DeeplinkDetailViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val sharedModule = module {
    single { DeeplinkRepository(get(), get()) }
    
    viewModel { AppListViewModel(get()) }
    viewModel { DeeplinkDetailViewModel(get()) }
}
