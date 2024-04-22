package uz.developersdreams.myapplication.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import uz.developersdreams.myapplication.core.util.Constants
import uz.developersdreams.myapplication.ui.presentation.card_product.components.CardProductScreen
import uz.developersdreams.myapplication.ui.presentation.cart.components.CartScreen
import uz.developersdreams.myapplication.ui.presentation.catalog.components.CatalogScreen
import uz.developersdreams.myapplication.ui.presentation.search.components.SearchScreen
import uz.developersdreams.myapplication.ui.presentation.splash.components.SplashScreen

@Composable
fun MainNavHost(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Screens.SplashScreen.route
    ) {
        composable(
            route = Screens.SplashScreen.route
        ) {
            SplashScreen(navController = navController)
        }
        
        composable(
            route = Screens.CatalogScreen.route + "/{${Constants.IS_DATA_LOADED}}",
            arguments = listOf(
                navArgument(Constants.IS_DATA_LOADED) {
                    type = NavType.BoolType
                    defaultValue = false
                }
            )
        ) { entry ->
            CatalogScreen(
                navController = navController,
                isDataLoaded = entry.arguments?.getBoolean(Constants.IS_DATA_LOADED) ?: false
            )
        }
        composable(
            route = Screens.SearchScreen.route
        ) {
            SearchScreen(
                navController = navController
            )
        }

        composable(
            route = Screens.CardProductScreen.route + "/{${Constants.PRODUCT_ID}}",
            arguments = listOf(
                navArgument(Constants.PRODUCT_ID) {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) { entry ->
            CardProductScreen(
                navController = navController,
                productId = entry.arguments?.getInt(Constants.PRODUCT_ID) ?: -1
            )
        }

        composable(
            route = Screens.CartScreen.route
        ) {
            CartScreen(navController = navController)
        }
    }
}