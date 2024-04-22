package uz.developersdreams.myapplication.core.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import uz.developersdreams.myapplication.R
import uz.developersdreams.myapplication.ui.theme.Black
import uz.developersdreams.myapplication.ui.theme.buttonsSize
import uz.developersdreams.myapplication.ui.theme.defaultTextSize


@Composable
fun CustomText(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color = Black,
    fontSize: TextUnit = defaultTextSize,
    fontWeight: FontWeight = FontWeight.Normal,
    textAlign: TextAlign = TextAlign.Start,
    maxLines: Int = 1,
    drawLine: Boolean = false
) {
    Text(
        modifier = modifier,
        text = text,
        fontSize = fontSize,
        fontWeight = fontWeight,
        color = textColor,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        textAlign = textAlign,
        style = TextStyle(textDecoration = if (drawLine) TextDecoration.LineThrough else TextDecoration.None)
    )
}

@Composable
fun ShowIcon(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int = R.drawable.ic_app_logo,
    iconColor: Color = Black
) {
    Icon(
        modifier = modifier
            .size(buttonsSize),
        painter = painterResource(icon),
        contentDescription = "Icon",
        tint = iconColor
    )
}