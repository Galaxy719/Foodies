package uz.developersdreams.myapplication.ui.presentation.catalog.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.developersdreams.myapplication.R
import uz.developersdreams.myapplication.core.presentation.components.BottomSheetFilter
import uz.developersdreams.myapplication.core.presentation.components.CategoriesBtn
import uz.developersdreams.myapplication.core.presentation.components.CustomButtonCircle
import uz.developersdreams.myapplication.core.presentation.components.CustomButtonDefault
import uz.developersdreams.myapplication.core.presentation.components.CustomText
import uz.developersdreams.myapplication.core.presentation.components.Products
import uz.developersdreams.myapplication.core.presentation.components.ShowCartButton
import uz.developersdreams.myapplication.core.util.UiEvent
import uz.developersdreams.myapplication.core.util.checkInternetConnection
import uz.developersdreams.myapplication.feature.data.data_source.remote.status.Status
import uz.developersdreams.myapplication.feature.domain.model.category.CategoryItem
import uz.developersdreams.myapplication.ui.presentation.catalog.CatalogEvent
import uz.developersdreams.myapplication.ui.presentation.catalog.CatalogViewModel
import uz.developersdreams.myapplication.ui.theme.PrimaryOrange
import uz.developersdreams.myapplication.ui.theme.White
import uz.developersdreams.myapplication.ui.theme.categoriesHeight
import uz.developersdreams.myapplication.ui.theme.defaultPadding
import uz.developersdreams.myapplication.ui.theme.topHeaderHeight

@Composable
fun CatalogScreen(
    navController: NavController,
    isDataLoaded: Boolean,
    viewModel: CatalogViewModel = hiltViewModel()
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
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Header(viewModel = viewModel)
            Categories(
                categories = state.categories,
                onClick = {index ->
                    viewModel.onEvent(CatalogEvent.ChangeChosenCategory(index))
                }
            )
            Box(modifier = Modifier
                .padding(top = defaultPadding)
                .fillMaxWidth()
                .weight(1F),
                contentAlignment = Alignment.Center
            ) {
                if (state.status == Status.LOADING) {
                    CircularProgressIndicator(
                        color = PrimaryOrange
                    )
                }

                else if (!isDataLoaded && state.status != Status.SUCCESS) {
                    CustomButtonDefault(
                        modifier = Modifier.padding(defaultPadding),
                        text = stringResource(id = R.string.reload),
                        onClick = {
                            viewModel.onEvent(CatalogEvent.FetchData(checkInternetConnection(context)))
                        }
                    )
                }
                else if (state.filteredProducts.isNotEmpty()) {
                    Products(
                        modifier = Modifier
                            .fillMaxSize(),
                        products = state.filteredProducts,
                        onClick = { id ->
                            viewModel.onEvent(CatalogEvent.OnProductItemClick(id))
                        },
                        onAddToCart = { id ->
                            viewModel.onEvent(CatalogEvent.AddProductToBasket(id))
                        },
                        onRemoveFromCart = { id ->
                            viewModel.onEvent(CatalogEvent.RemoveProductToBasket(id))
                        }
                    )
                }
                else if (state.filterProduct.byTags.isNotEmpty()) {
                    CustomText(
                        text = stringResource(id = R.string.filteredFoodNotFound),
                        maxLines = 5,
                        textAlign = TextAlign.Center
                    )
                }
                else if (state.filteredProducts.isEmpty()) {
                    CustomText(
                        text = stringResource(id = R.string.empty),
                        maxLines = 1
                    )
                }
            }
            
            AnimatedVisibility(visible = state.productsInCart.isNotEmpty()) {
                ShowCartButton(
                    productPrice = state.totalValueOfCart,
                    onClick = {
                        viewModel.onEvent(CatalogEvent.OnCartClick)
                    }
                )
            }
        }
    }

    BottomSheetFilter(
        tags = state.tags,
        isShowBottomSheet = state.isBottomSheetVisible,
        onChangeBottomSheetVisibility = {
            viewModel.onEvent(CatalogEvent.IsShowBottomSheet(it))
        },
        onSelection = { index ->
            viewModel.onEvent(CatalogEvent.OnTagSelection(index))
        },
        onReadyBtnClick = {
            viewModel.onEvent(CatalogEvent.OnBottomSheetReadyBtn)
        }
    )

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collectLatest { uiEvent ->
            when (uiEvent) {
                is UiEvent.OnNavigate -> {
                    navController.navigate(uiEvent.route)
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
private fun Header(
    modifier: Modifier = Modifier,
    viewModel: CatalogViewModel
) {
    Row(
        modifier = modifier
            .padding(start = defaultPadding, top = defaultPadding, end = defaultPadding)
            .fillMaxWidth()
            .height(topHeaderHeight),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CustomButtonCircle(
            icon = R.drawable.ic_filter,
            onClick = {
                viewModel.onEvent(CatalogEvent.IsShowBottomSheet(true))
            }
        )
        Image(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1F),
            painter = painterResource(id = R.drawable.ic_app_logo),
            contentDescription = "Foodies"
        )
        CustomButtonCircle(
            icon = R.drawable.ic_search,
            onClick = {
                viewModel.onEvent(CatalogEvent.OnSearchUi)
            }
        )
    }
}

@Composable
private fun Categories(
    modifier: Modifier = Modifier,
    categories: List<CategoryItem>,
    onClick: (Int) -> Unit = {}
) {
    LazyRow(modifier = modifier
        .fillMaxWidth()
        .height(categoriesHeight)
    ) {
        itemsIndexed(categories) { index, category ->
            CategoriesBtn(
                category = category,
                index = index,
                onClick = {
                    onClick(it)
                }
            )
        }
    }
}