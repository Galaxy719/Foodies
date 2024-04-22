package uz.developersdreams.myapplication.ui.presentation.search.components

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
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collectLatest
import uz.developersdreams.myapplication.R
import uz.developersdreams.myapplication.core.extensions.navigateBack
import uz.developersdreams.myapplication.core.presentation.components.BasicTextFieldLetter
import uz.developersdreams.myapplication.core.presentation.components.CustomButtonCircle
import uz.developersdreams.myapplication.core.presentation.components.CustomText
import uz.developersdreams.myapplication.core.presentation.components.Products
import uz.developersdreams.myapplication.core.util.UiEvent
import uz.developersdreams.myapplication.ui.presentation.search.SearchEvent
import uz.developersdreams.myapplication.ui.presentation.search.SearchViewModel
import uz.developersdreams.myapplication.ui.theme.PrimaryOrange
import uz.developersdreams.myapplication.ui.theme.White
import uz.developersdreams.myapplication.ui.theme.defaultPadding
import uz.developersdreams.myapplication.ui.theme.topHeaderHeight

@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchViewModel = hiltViewModel()
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
            Header(viewModel)
            Box(modifier = Modifier
                .padding(top = defaultPadding)
                .fillMaxWidth()
                .weight(1F),
                contentAlignment = Alignment.Center
            ) {
                if (state.searchedProducts.isNotEmpty()) {
                    Products(
                        modifier = Modifier.fillMaxSize(),
                        products = state.searchedProducts,
                        onClick = { id ->
                            viewModel.onEvent(SearchEvent.OnProductItemClick(id))
                        },
                        onAddToCart = { id ->
                            viewModel.onEvent(SearchEvent.AddProductToBasket(id))
                        },
                        onRemoveFromCart = { id ->
                            viewModel.onEvent(SearchEvent.RemoveProductToBasket(id))
                        }
                    )
                }
                else if (state.filterProduct.byName.isNullOrBlank()) {
                    CustomText(
                        text = stringResource(id = R.string.enterNameOfFood),
                        maxLines = 5,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collectLatest { uiEvent ->
            when (uiEvent) {
                is UiEvent.OnNavigate -> {
                    navController.navigate(uiEvent.route)
                }
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
    viewModel: SearchViewModel
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
                viewModel.onEvent(SearchEvent.OnPopBack)
            }
        )
        BasicTextFieldLetter(
            modifier = Modifier.weight(1F),
            text = viewModel.uiState.collectAsState().value.filterProduct.byName ?: "",
            onTextChanged = {
                viewModel.onEvent(SearchEvent.OnSearchText(it))
            }
        )
        AnimatedVisibility(visible = !viewModel.uiState.collectAsState().value.filterProduct.byName.isNullOrBlank()) {
            CustomButtonCircle(
                icon = R.drawable.ic_cancel,
                onClick = {
                    viewModel.onEvent(SearchEvent.OnClear)
                }
            )
        }
    }
}