package com.kodmap.deeplinktester

import androidx.compose.ui.window.ComposeUIViewController
import com.kodmap.deeplinktester.di.platformModule
import com.kodmap.deeplinktester.di.sharedModule
import com.kodmap.deeplinktester.ui.App
import org.koin.core.context.startKoin
import platform.Foundation.NSURL
import platform.UIKit.UIApplication

fun MainViewController() = ComposeUIViewController { 
    App(
        onDeeplinkClick = { link ->
            openDeeplink(link)
        }
    )
}

fun initKoin() {
    startKoin {
        modules(platformModule, sharedModule)
    }
}

private fun openDeeplink(link: String) {
    val url = NSURL.URLWithString(link) ?: return
    if (UIApplication.sharedApplication.canOpenURL(url)) {
        UIApplication.sharedApplication.openURL(url)
    }
}
