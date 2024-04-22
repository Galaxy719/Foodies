package uz.developersdreams.myapplication.core.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import uz.developersdreams.myapplication.R
import uz.developersdreams.myapplication.core.extensions.decimalFormatToText
import uz.developersdreams.myapplication.ui.theme.Black
import uz.developersdreams.myapplication.ui.theme.PrimaryOrange
import uz.developersdreams.myapplication.ui.theme.White
import uz.developersdreams.myapplication.ui.theme.buttonsSize
import uz.developersdreams.myapplication.ui.theme.cartButtonHeight
import uz.developersdreams.myapplication.ui.theme.cartIconSize
import uz.developersdreams.myapplication.ui.theme.defaultPadding
import uz.developersdreams.myapplication.ui.theme.defaultRadius

@Composable
fun CustomButtonCircle(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int = R.drawable.ic_arrow_left,
    iconColor: Color = Black,
    containerColor: Color = White,
    elevation: Dp = 0.dp,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .size(buttonsSize)
            .shadow(elevation = elevation, shape = CircleShape)
            .clip(shape = CircleShape)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = containerColor)
    ) {
        Icon(
            modifier = Modifier
                .padding(defaultPadding)
                .fillMaxSize(),
            painter = painterResource(id = icon),
            contentDescription = "Button",
            tint = iconColor
        )
    }
}

@Composable
fun CustomButtonRounded(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int = R.drawable.ic_arrow_left,
    iconColor: Color = Black,
    containerColor: Color = White,
    elevation: Dp = 0.dp,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .size(buttonsSize)
            .shadow(elevation = elevation, shape = RoundedCornerShape(defaultRadius), clip = true)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = containerColor)
    ) {
        Icon(
            modifier = Modifier
                .padding(defaultPadding)
                .fillMaxSize(),
            painter = painterResource(id = icon),
            contentDescription = "Button",
            tint = iconColor
        )
    }
}

@Composable
fun CustomButtonDefault(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color = White,
    @DrawableRes icon: Int? = null,
    iconColor: Color = White,
    containerColor: Color = PrimaryOrange,
    onClick: () -> Unit = {}
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(cartButtonHeight),
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(containerColor),
        shape = RoundedCornerShape(defaultRadius)
    ) {
        if (icon != null) {
            Icon(
                modifier = Modifier
                    .padding(start = defaultPadding, end = defaultPadding)
                    .size(cartIconSize),
                painter = painterResource(id = icon),
                contentDescription = "Button",
                tint = iconColor
            )
        }
        CustomText(
            text = text,
            textColor = textColor,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ShowCartButton(
    modifier: Modifier = Modifier,
    text: String = "",
    productPrice: Int,
    isShowCartIcon: Boolean = true,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = defaultPadding, end = defaultPadding)
            .wrapContentHeight()
    ) {
        CustomButtonDefault(
            modifier = Modifier
                .padding(defaultPadding)
                .fillMaxWidth(),
            text = if (text.isBlank()) productPrice.decimalFormatToText()
            else text.plus(" ${productPrice.decimalFormatToText()}"),
            icon = if (isShowCartIcon) R.drawable.ic_cart else null,
            onClick = { onClick() }
        )
    }
}