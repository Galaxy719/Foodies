package uz.developersdreams.myapplication.ui.presentation.cart.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collectLatest
import uz.developersdreams.myapplication.R
import uz.developersdreams.myapplication.core.extensions.navigateBack
import uz.developersdreams.myapplication.core.presentation.components.CustomButtonCircle
import uz.developersdreams.myapplication.core.presentation.components.CustomText
import uz.developersdreams.myapplication.core.presentation.components.ProductItemInCart
import uz.developersdreams.myapplication.core.presentation.components.ShowCartButton
import uz.developersdreams.myapplication.core.util.UiEvent
import uz.developersdreams.myapplication.ui.presentation.cart.CartEvent
import uz.developersdreams.myapplication.ui.presentation.cart.CartViewModel
import uz.developersdreams.myapplication.ui.theme.PrimaryOrange
import uz.developersdreams.myapplication.ui.theme.White
import uz.developersdreams.myapplication.ui.theme.defaultPadding
import uz.developersdreams.myapplication.ui.theme.topHeaderHeight

@Composable
fun CartScreen(
    navController: NavController,
    viewModel: CartViewModel = hiltViewModel()
) {
    val state = viewModel.uiState.collectAsState().value

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Header(viewModel = viewModel)
            Box(modifier = Modifier
                .padding(top = defaultPadding)
                .fillMaxWidth()
                .weight(1F),
                contentAlignment = Alignment.Center
            ) {
                ProductItemInCart(
                    modifier = Modifier
                        .fillMaxSize(),
                    products = state.productsInCart,
                    onAddToCart = {
                        viewModel.onEvent(CartEvent.AddProductToBasket(it))
                    },
                    onRemoveFromCart = {
                        viewModel.onEvent(CartEvent.RemoveProductToBasket(it))
                    }
                )
                if  (state.productsInCart.isEmpty()) {
                    CustomText(
                        text = stringResource(id = R.string.cartIsEmpry),
                        maxLines = 1
                    )
                }
            }
            AnimatedVisibility(visible = state.productsInCart.isNotEmpty()) {
                ShowCartButton(
                    text = stringResource(id = R.string.orderFor),
                    productPrice = state.totalValueOfCart,
                    isShowCartIcon = false
                )
            }
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collectLatest { uiEvent ->
            when (uiEvent) {
                is UiEvent.OnPopBack -> {
                    navController.navigateBack()
                }
                else -> {}
            }
        }
    }
}

@Composable
private fun Header(
    viewModel: CartViewModel
) {
    Row(
        modifier = Modifier
            .padding(start = defaultPadding, end = defaultPadding)
            .fillMaxWidth()
            .height(topHeaderHeight),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CustomButtonCircle(
            iconColor = PrimaryOrange,
            onClick = {
                viewModel.onEvent(CartEvent.OnPopBack)
            }
        )
        CustomText(
            modifier = Modifier.padding(start = defaultPadding),
            text = stringResource(id = R.string.cart),
            fontWeight = FontWeight.Bold
        )
    }
}