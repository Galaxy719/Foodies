package uz.developersdreams.myapplication.ui.presentation.splash.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.developersdreams.myapplication.core.presentation.components.ShowIcon
import uz.developersdreams.myapplication.core.util.UiEvent
import uz.developersdreams.myapplication.core.util.checkInternetConnection
import uz.developersdreams.myapplication.ui.navigation.Screens
import uz.developersdreams.myapplication.ui.presentation.splash.SplashEvent
import uz.developersdreams.myapplication.ui.presentation.splash.SplashViewModel
import uz.developersdreams.myapplication.ui.theme.PrimaryOrange
import uz.developersdreams.myapplication.ui.theme.White
import uz.developersdreams.myapplication.ui.theme.defaultPadding

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = PrimaryOrange,
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            ShowIcon(
                modifier = Modifier
                    .padding(defaultPadding)
                    .fillMaxWidth()
                    .wrapContentHeight(),
                iconColor = White
            )
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.onEvent(SplashEvent.OnFetchData(checkInternetConnection(context)))

        viewModel.uiEvent.collectLatest { uiEvent ->
            when (uiEvent) {
                is UiEvent.OnNavigate -> {
                    navController.navigate(uiEvent.route) {
                        popUpTo(Screens.SplashScreen.route) { inclusive = true }
                    }
                }
                is UiEvent.ShowSnackBar -> {
                    scope.launch {
                        snackBarHostState.showSnackbar(uiEvent.message.asString(context))
                    }
                }
                else -> {}
            }
        }
    }
}