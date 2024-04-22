package uz.developersdreams.myapplication.ui.presentation.splash

sealed class SplashEvent {

    data class OnFetchData(
        val isInternet: Boolean
    ) : SplashEvent()
}