package uz.developersdreams.myapplication.core.util

sealed class UiEvent {

    data class OnNavigate(val route: String): UiEvent()

    data object OnPopBack: UiEvent()

    data class ShowSnackBar(val message: UiText): UiEvent()
}