package uz.developersdreams.myapplication.ui.presentation.card_product.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.developersdreams.myapplication.R
import uz.developersdreams.myapplication.core.extensions.navigateBack
import uz.developersdreams.myapplication.core.presentation.components.CustomButtonCircle
import uz.developersdreams.myapplication.core.presentation.components.CustomText
import uz.developersdreams.myapplication.core.presentation.components.ShowCartButton
import uz.developersdreams.myapplication.core.util.UiEvent
import uz.developersdreams.myapplication.ui.presentation.card_product.CardProductEvent
import uz.developersdreams.myapplication.ui.presentation.card_product.CardProductState
import uz.developersdreams.myapplication.ui.presentation.card_product.CardProductViewModel
import uz.developersdreams.myapplication.ui.theme.White
import uz.developersdreams.myapplication.ui.theme.WhiteFade
import uz.developersdreams.myapplication.ui.theme.defaultElevation
import uz.developersdreams.myapplication.ui.theme.defaultPadding
import uz.developersdreams.myapplication.ui.theme.smallPadding
import uz.developersdreams.myapplication.ui.theme.veryLargerTextSize

@Composable
fun CardProductScreen(
    navController: NavController,
    productId: Int,
    viewModel: CardProductViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val state = viewModel.uiState.collectAsState().value

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = White,
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1F)
                        .verticalScroll(state = rememberScrollState())
                ) {
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1F),
                        painter = painterResource(id = R.drawable.img_food),
                        contentDescription = "Image"
                    )
                    CustomText(
                        modifier = Modifier.padding(defaultPadding),
                        text = state.productItem?.name ?: "",
                        fontSize = veryLargerTextSize,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2
                    )
                    CustomText(
                        modifier = Modifier.padding(start = defaultPadding, top = smallPadding, end = defaultElevation),
                        text = state.productItem?.description ?: "",
                        maxLines = 4
                    )
                    ProductSpecs(state = state)
                }
                ShowCartButton(
                    text = stringResource(id = R.string.addToCart),
                    productPrice = state.productItem?.priceCurrent ?: 0,
                    isShowCartIcon = false,
                    onClick = {
                        viewModel.onEvent(CardProductEvent.OnAddToCart)
                    }
                )
            }
            CustomButtonCircle(
                modifier = Modifier.padding(defaultPadding),
                onClick = {
                    viewModel.onEvent(CardProductEvent.OnPopBack)
                },
                elevation = defaultElevation
            )
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.onEvent(CardProductEvent.OnGetProduct(id = productId))

        viewModel.uiEvent.collectLatest { uiEvent ->
            when (uiEvent) {
                is UiEvent.OnPopBack -> {
                    navController.navigateBack()
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

@Composable
private fun ProductSpecs(
    state: CardProductState
) {
    Column {
        Spacer(
            modifier = Modifier
                .padding(top = defaultPadding)
                .fillMaxWidth()
                .height(2.dp)
                .background(WhiteFade)
        )
        DrawSpecs(
            name = stringResource(id = R.string.weight),
            value = ("${state.productItem?.measure} ${state.productItem?.measureUnit}")
        )
        DrawSpecs(
            name = stringResource(id = R.string.productEnergy),
            value = ("${state.productItem?.energyPer100Grams} ${stringResource(id = R.string.kcal)}")
        )
        DrawSpecs(
            name = stringResource(id = R.string.fats),
            value = ("${state.productItem?.fatsPer100Grams} ${state.productItem?.measureUnit}")
        )
    }
}

@Composable
private fun DrawSpecs(
    name: String,
    value: String
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            CustomText(
                modifier = Modifier
                    .padding(start = defaultPadding, top = defaultPadding, end = defaultPadding)
                    .weight(1F),
                text = name
            )
            CustomText(
                modifier = Modifier
                    .padding(top = defaultPadding, end = defaultPadding),
                text = value
            )
        }
        Spacer(
            modifier = Modifier
                .padding(top = defaultPadding)
                .fillMaxWidth()
                .height(2.dp)
                .background(WhiteFade)
        )
    }
}
