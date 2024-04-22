package uz.developersdreams.myapplication.core.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import uz.developersdreams.myapplication.R
import uz.developersdreams.myapplication.core.extensions.decimalFormatToText
import uz.developersdreams.myapplication.feature.domain.model.product.ProductItem
import uz.developersdreams.myapplication.ui.theme.PrimaryOrange
import uz.developersdreams.myapplication.ui.theme.White
import uz.developersdreams.myapplication.ui.theme.WhiteFade
import uz.developersdreams.myapplication.ui.theme.categoriesHeight
import uz.developersdreams.myapplication.ui.theme.defaultElevation
import uz.developersdreams.myapplication.ui.theme.defaultPadding
import uz.developersdreams.myapplication.ui.theme.defaultRadius
import uz.developersdreams.myapplication.ui.theme.smallPadding
import uz.developersdreams.myapplication.ui.theme.smallTextSize

@Composable
fun Products(
    modifier: Modifier = Modifier,
    products: List<ProductItem>,
    onClick: (Int) -> Unit = {},
    onAddToCart: (Int) -> Unit = {},
    onRemoveFromCart: (Int) -> Unit = {}
) {
    LazyVerticalGrid(
        modifier = modifier
            .fillMaxWidth(),
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(defaultPadding),
        horizontalArrangement = Arrangement.spacedBy(defaultPadding),
        verticalArrangement = Arrangement.spacedBy(defaultPadding)
    ) {
        items(products) { product ->
            DrawProductItem(
                modifier = Modifier.fillMaxWidth(),
                productItem = product,
                valueOfInCart = product.valueOfInCart,
                onClick = { onClick(product.id) },
                onAddToCart = { onAddToCart(product.id) },
                onRemoveFromCart = { onRemoveFromCart(product.id) }
            )
        }
    }
}

@Composable
private fun DrawProductItem(
    modifier: Modifier = Modifier,
    productItem: ProductItem,
    valueOfInCart: Int = 0,
    onClick: () -> Unit = {},
    onAddToCart: () -> Unit = {},
    onRemoveFromCart: () -> Unit = {}
) {
    Box(modifier = modifier
        .clip(RoundedCornerShape(defaultRadius))
        .background(color = WhiteFade)
        .clickable { onClick() },

        ) {
        if (productItem.tagIds.isNotEmpty()) {
            productItem.tagIds.forEach { id ->
                Image(
                    modifier = Modifier
                        .padding(start = defaultPadding, top = defaultPadding),
                    painter = painterResource(id = getProductTag(id = id)),
                    contentDescription = "Tag"
                )
            }
        }
        Column {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = R.drawable.img_food),
                contentDescription = productItem.name,
            )
            CustomText(
                modifier = Modifier.padding(
                    start = defaultPadding,
                    top = smallPadding,
                    end = defaultPadding
                ),
                text = productItem.name,
            )
            CustomText(
                modifier = Modifier.padding(
                    start = defaultPadding,
                    top = smallPadding,
                    end = defaultPadding
                ),
                text = "${productItem.measure} г",
            )
            Box(
                modifier = Modifier
                    .padding(defaultPadding)
                    .fillMaxWidth()
                    .height(categoriesHeight)
            ) {
                if (valueOfInCart > 0) {
                    ProductShowValueInCart(
                        valueOfInCart = valueOfInCart,
                        onAddToCart = {  onAddToCart() },
                        onRemoveFromCart = { onRemoveFromCart() }
                    )
                }
                else {
                    ProductShowCost(
                        productItem = productItem,
                        onAddToCart = {
                            onAddToCart()
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun ProductShowCost(
    productItem: ProductItem,
    onAddToCart: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .shadow(defaultElevation, shape = RoundedCornerShape(defaultRadius), clip = true)
            .background(White)
            .clickable { onAddToCart() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        CustomText(
            text = productItem.priceCurrent.decimalFormatToText(),
            fontWeight = FontWeight.Medium
        )
        if (productItem.priceOld != null) {
            CustomText(
                modifier = Modifier.padding(start = smallPadding),
                text = productItem.priceOld.decimalFormatToText(),
                fontSize = smallTextSize,
                drawLine = true
            )
        }
    }
}

@Composable
fun ProductShowValueInCart(
    modifier: Modifier = Modifier,
    valueOfInCart: Int = 0,
    onAddToCart: () -> Unit = {},
    onRemoveFromCart: () -> Unit = {},
    buttonContainerColor: Color = White,
    isBottom: Boolean = false
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = if (isBottom) Alignment.Bottom else Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        CustomButtonRounded(
            modifier = Modifier
                .size(categoriesHeight)
            ,
            icon = R.drawable.ic_minus,
            iconColor = PrimaryOrange,
            containerColor = buttonContainerColor,
            elevation = defaultElevation,
            onClick = {
                onRemoveFromCart()
            }
        )
        Box(
            modifier = Modifier.height(categoriesHeight),
            contentAlignment = Alignment.Center
        ) {
            CustomText(
                text = valueOfInCart.toString(),
                fontWeight = FontWeight.Bold
            )
        }
        CustomButtonRounded(
            modifier = Modifier
                .size(categoriesHeight)
            ,
            icon = R.drawable.ic_plus,
            iconColor = PrimaryOrange,
            containerColor = buttonContainerColor,
            elevation = defaultElevation,
            onClick = {
                onAddToCart()
            }
        )
    }
}

@Composable
private fun getProductTag(id: Int): Int {
    return when (id) {
        1 -> R.drawable.ic_sale
        2 -> R.drawable.ic_vegetarian
        4 -> R.drawable.ic_spicy
        else -> R.drawable.ic_sale
    }
}