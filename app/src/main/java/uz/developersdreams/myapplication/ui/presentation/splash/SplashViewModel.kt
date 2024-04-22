package uz.developersdreams.myapplication.ui.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import uz.developersdreams.myapplication.R
import uz.developersdreams.myapplication.core.util.Constants.DELAY_FOR_SPLASH_SCREEN
import uz.developersdreams.myapplication.core.util.UiEvent
import uz.developersdreams.myapplication.core.util.UiText
import uz.developersdreams.myapplication.feature.domain.use_case.category.CategoryUseCases
import uz.developersdreams.myapplication.feature.domain.use_case.product.ProductUseCases
import uz.developersdreams.myapplication.feature.domain.use_case.tag.TagUseCases
import uz.developersdreams.myapplication.ui.navigation.Screens
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val categoryUseCases: CategoryUseCases,
    private val tagUseCases: TagUseCases,
    private val productUseCases: ProductUseCases
) : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val delayForShowSnackBar = 500L

    private var allDataLoaded = false

    fun onEvent(event: SplashEvent) {
        when(event) {
            is SplashEvent.OnFetchData -> {
                if (event.isInternet) {
                    getCategoriesFromApi()
                    skipLoadingIfSlowInternet()
                }
                else {
                    viewModelScope.launch {
                        delay(delayForShowSnackBar)
                        sendUiEvent(UiEvent.ShowSnackBar(UiText.StringResource(R.string.noIntenerConnection)))
                        delay(DELAY_FOR_SPLASH_SCREEN - delayForShowSnackBar)
                        changeScreen()
                    }
                }
            }
        }
    }

    private fun getCategoriesFromApi() = viewModelScope.launch {
        categoryUseCases.getCategoriesFromApi().let { response ->
            if (response?.isSuccessful != true) {
                changeScreen()
                return@launch
            }

            getTagsFromApi()
        }
    }

    private fun getTagsFromApi() = viewModelScope.launch {
        tagUseCases.getTagsFromApi().let { response ->
            if (response?.isSuccessful != true) {
                changeScreen()
                return@launch
            }

            getProductsFromApi()
        }
    }

    private fun getProductsFromApi() = viewModelScope.launch {
        productUseCases.getProductsFromApi().let { response ->
            if (response?.isSuccessful != true) {
                changeScreen()
                return@launch
            }

            allDataLoaded = true
            changeScreen()
        }
    }

    /** Waits 10s, ifDataNotLoadedAutoNavigates ------------------------------------------------! */
    private fun skipLoadingIfSlowInternet() = viewModelScope.launch {
        for (i in 1..10) { //Waits 10s
            delay(1000)
        }
        changeScreen()
    }

    private fun changeScreen() {
        sendUiEvent(UiEvent.OnNavigate(Screens.CatalogScreen.route + "/$allDataLoaded"))
    }

    private fun sendUiEvent(event: UiEvent) = viewModelScope.launch {
        _uiEvent.send(event)
    }
}