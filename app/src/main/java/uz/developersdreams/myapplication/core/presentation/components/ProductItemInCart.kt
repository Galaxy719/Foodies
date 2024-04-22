package uz.developersdreams.myapplication.core.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import uz.developersdreams.myapplication.R
import uz.developersdreams.myapplication.core.extensions.decimalFormatToText
import uz.developersdreams.myapplication.feature.domain.model.product.ProductItem
import uz.developersdreams.myapplication.ui.theme.WhiteFade
import uz.developersdreams.myapplication.ui.theme.defaultPadding
import uz.developersdreams.myapplication.ui.theme.smallPadding
import uz.developersdreams.myapplication.ui.theme.smallTextSize

@Composable
fun ProductItemInCart(
    modifier: Modifier = Modifier,
    products: List<ProductItem>,
    onAddToCart: (Int) -> Unit = {},
    onRemoveFromCart: (Int) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(products) {product ->
            DrawProduct(
                productItem = product,
                valueOfInCart = product.valueOfInCart,
                onAddToCart = {
                    onAddToCart(product.id)
                },
                onRemoveFromCart = {
                    onRemoveFromCart(product.id)
                }
            )
            Spacer(
                modifier = Modifier
                    .padding(top = defaultPadding)
                    .fillMaxWidth()
                    .height(2.dp)
                    .background(WhiteFade)
            )
        }
    }
}

@Composable
private fun DrawProduct(
    productItem: ProductItem,
    valueOfInCart: Int = 0,
    onAddToCart: () -> Unit = {},
    onRemoveFromCart: () -> Unit = {}
) {
    ConstraintLayout(
        modifier = Modifier
            .padding(start = defaultPadding, end = defaultPadding)
            .fillMaxWidth()
    ) {
        val (image, productValue, valueText) = createRefs()

        Image(
            modifier = Modifier
                .constrainAs(image){
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(productValue.start)
                    width = Dimension.percent(.3F)
                },
            painter = painterResource(id = R.drawable.img_food),
            contentDescription = "Food"
        )
        Column(
            modifier = Modifier
                .padding(start = defaultPadding, end = defaultPadding)
                .constrainAs(productValue) {
                    start.linkTo(image.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(valueText.start)
                    height = Dimension.percent(1F)
                    width = Dimension.percent(.4F)
                },
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            CustomText(
                text = productItem.name,
                maxLines = 2
            )
            ProductShowValueInCart(
                valueOfInCart = valueOfInCart,
                onAddToCart = { onAddToCart() },
                onRemoveFromCart = { onRemoveFromCart() },
                buttonContainerColor = WhiteFade,
                isBottom = true
            )
        }
        Box(
            modifier = Modifier
                .constrainAs(valueText) {
                    start.linkTo(productValue.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                    height = Dimension.fillToConstraints
                    width = Dimension.percent(.3F)
                },
            contentAlignment = Alignment.BottomEnd
        ) {
            Column {
                CustomText(
                    text = productItem.priceCurrent.decimalFormatToText(),
                    fontWeight = FontWeight.Medium
                )
                if (productItem.priceOld != null) {
                    Spacer(modifier = Modifier.padding(top = defaultPadding))
                    CustomText(
                        modifier = Modifier.padding(start = smallPadding),
                        text = productItem.priceOld.decimalFormatToText(),
                        fontSize = smallTextSize,
                        drawLine = true
                    )
                }
            }
        }
    }
}