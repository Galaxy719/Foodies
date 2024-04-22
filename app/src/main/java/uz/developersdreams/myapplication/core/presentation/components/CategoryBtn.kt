package uz.developersdreams.myapplication.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import uz.developersdreams.myapplication.feature.domain.model.category.CategoryItem
import uz.developersdreams.myapplication.ui.theme.Black
import uz.developersdreams.myapplication.ui.theme.PrimaryOrange
import uz.developersdreams.myapplication.ui.theme.White
import uz.developersdreams.myapplication.ui.theme.categoriesHeight
import uz.developersdreams.myapplication.ui.theme.defaultPadding
import uz.developersdreams.myapplication.ui.theme.defaultRadius
import uz.developersdreams.myapplication.ui.theme.defaultTextSize

@Composable
fun CategoriesBtn(
    modifier: Modifier = Modifier,
    category: CategoryItem,
    index: Int,
    onClick: (Int) -> Unit = {}
) {
    Text(
        modifier = modifier
            .padding(start = defaultPadding)
            .height(categoriesHeight)
            .wrapContentWidth()
            .clip(shape = RoundedCornerShape(defaultRadius))
            .background(
                color = if (category.isSelected) PrimaryOrange else Color.Transparent,
                shape = if (category.isSelected) RoundedCornerShape(defaultRadius) else RoundedCornerShape(0)
            )
            .clickable { onClick(index) }
            .wrapContentHeight(align = Alignment.CenterVertically)
            .padding(start = defaultPadding, end = defaultPadding),
        text = category.name,
        fontSize = defaultTextSize,
        color = if (category.isSelected) White else Black,
        fontWeight = FontWeight.Bold,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
    )
}