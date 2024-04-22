package uz.developersdreams.myapplication.core.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import uz.developersdreams.myapplication.R
import uz.developersdreams.myapplication.core.extensions.getMaxValueLength
import uz.developersdreams.myapplication.core.util.Constants.SEARCH_TEXT_MAX_LENGTH
import uz.developersdreams.myapplication.ui.theme.Black
import uz.developersdreams.myapplication.ui.theme.defaultPadding
import uz.developersdreams.myapplication.ui.theme.defaultTextSize

@Composable
fun BasicTextFieldLetter(
    modifier: Modifier = Modifier,
    text: String = "",
    maxLength: Int = SEARCH_TEXT_MAX_LENGTH,
    onTextChanged: (String) -> Unit
) {
    BasicTextField(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxSize(),
        textStyle = TextStyle(
            color = Black,
            fontSize = defaultTextSize,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start,
        ),
        singleLine = true,
        value = text,
        onValueChange = { value ->
            if (value.getMaxValueLength(maxLength))
                return@BasicTextField

            else onTextChanged.invoke(value)
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text
        ),
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = defaultPadding, end = defaultPadding),
                contentAlignment = Alignment.CenterStart
            ) {
                if (text.isBlank()) {
                    CustomText(text = stringResource(id = R.string.searchFood))
                }
                innerTextField()
            }
        }
    )
}