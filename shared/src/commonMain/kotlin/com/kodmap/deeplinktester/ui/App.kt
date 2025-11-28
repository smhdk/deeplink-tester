package com.kodmap.deeplinktester.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.kodmap.deeplinktester.ui.applist.AppListScreen
import com.kodmap.deeplinktester.ui.applist.AppListViewModel
import com.kodmap.deeplinktester.ui.deeplinkdetail.DeeplinkDetailScreen
import com.kodmap.deeplinktester.ui.deeplinkdetail.DeeplinkDetailViewModel
import com.kodmap.deeplinktester.ui.theme.DeeplinkTesterTheme
import org.koin.compose.viewmodel.koinViewModel

sealed class Screen {
    data object AppList : Screen()
    data class DeeplinkDetail(val appId: Long, val appName: String) : Screen()
}

@Composable
fun App(
    onDeeplinkClick: (String) -> Unit = {}
) {
    var currentScreen: Screen by remember { mutableStateOf(Screen.AppList) }

    DeeplinkTesterTheme {
        when (val screen = currentScreen) {
            is Screen.AppList -> {
                val viewModel = koinViewModel<AppListViewModel>()
                AppListScreen(
                    viewModel = viewModel,
                    onAppClick = { appId, appName ->
                        currentScreen = Screen.DeeplinkDetail(appId, appName)
                    }
                )
            }
            is Screen.DeeplinkDetail -> {
                val viewModel = koinViewModel<DeeplinkDetailViewModel>()
                DeeplinkDetailScreen(
                    viewModel = viewModel,
                    appId = screen.appId,
                    appName = screen.appName,
                    onBackClick = { currentScreen = Screen.AppList },
                    onDeeplinkClick = onDeeplinkClick
                )
            }
        }
    }
}
