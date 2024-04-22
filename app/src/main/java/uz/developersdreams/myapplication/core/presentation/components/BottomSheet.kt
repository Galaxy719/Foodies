package uz.developersdreams.myapplication.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import uz.developersdreams.myapplication.R
import uz.developersdreams.myapplication.feature.domain.model.tag.TagItem
import uz.developersdreams.myapplication.ui.theme.GrayLight
import uz.developersdreams.myapplication.ui.theme.PrimaryOrange
import uz.developersdreams.myapplication.ui.theme.White
import uz.developersdreams.myapplication.ui.theme.WhiteFade
import uz.developersdreams.myapplication.ui.theme.cartButtonHeight
import uz.developersdreams.myapplication.ui.theme.defaultPadding
import uz.developersdreams.myapplication.ui.theme.largerPadding
import uz.developersdreams.myapplication.ui.theme.largerTextSize

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetFilter(
    tags: List<TagItem>,
    isShowBottomSheet: Boolean = false,
    onChangeBottomSheetVisibility: (Boolean) -> Unit = {},
    onSelection: (Int) -> Unit = {},
    onReadyBtnClick: () -> Unit = {}
) {
    val scope = rememberCoroutineScope()
    val sheetState= rememberModalBottomSheetState(skipPartiallyExpanded = true)

    if (isShowBottomSheet) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = { onChangeBottomSheetVisibility(false) },
            containerColor = White
        ) {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                CustomText(
                    modifier = Modifier
                        .padding(start = defaultPadding, end = defaultPadding),
                    text = stringResource(id = R.string.selectFood),
                    fontSize = largerTextSize,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.padding(bottom = defaultPadding))
                LazyColumn(
                    modifier = Modifier.heightIn(max = 250.dp)
                ) {
                    itemsIndexed(tags) {index, tag ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(cartButtonHeight)
                                .clip(RoundedCornerShape(defaultPadding))
                                .clickable { onSelection(index) },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            CustomText(
                                modifier = Modifier
                                    .padding(start = defaultPadding)
                                    .weight(1F),
                                text = tag.name
                            )
                            RadioButton(
                                selected = tag.isSelected,
                                onClick = { onSelection(index) },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = PrimaryOrange,
                                    unselectedColor = GrayLight
                                )
                            )
                        }
                        if (index < tags.lastIndex) {
                            Spacer(
                                modifier = Modifier
                                    .padding(start = defaultPadding, end = defaultPadding)
                                    .height(2.dp)
                                    .fillMaxWidth()
                                    .background(WhiteFade)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.padding(top = largerPadding))
                CustomButtonDefault(
                    modifier = Modifier
                        .padding(defaultPadding),
                    text = stringResource(id = R.string.ready),
                    onClick = {
                        scope.launch {
                            sheetState.hide()
                            onChangeBottomSheetVisibility(false)
                        }
                        onReadyBtnClick()
                    }
                )
                Spacer(modifier = Modifier.padding(bottom = 50.dp))
            }
        }
    }
}