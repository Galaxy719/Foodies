package uz.developersdreams.myapplication.ui.navigation

import uz.developersdreams.myapplication.core.util.Constants.CARD_PRODUCT_SCREEN
import uz.developersdreams.myapplication.core.util.Constants.CART_SCREEN
import uz.developersdreams.myapplication.core.util.Constants.CATALOG_SCREEN
import uz.developersdreams.myapplication.core.util.Constants.SEARCH_SCREEN
import uz.developersdreams.myapplication.core.util.Constants.SPLASH_SCREEN

sealed class Screens(val route: String) {

    data object SplashScreen: Screens(SPLASH_SCREEN)

    data object CatalogScreen: Screens(CATALOG_SCREEN)

    data object SearchScreen: Screens(SEARCH_SCREEN)

    data object CardProductScreen: Screens(CARD_PRODUCT_SCREEN)

    data object CartScreen: Screens(CART_SCREEN)
}