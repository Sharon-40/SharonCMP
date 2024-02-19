package presentation.custom_views

import ColorResources
import StringResources
import StyleUtils
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.DropdownMenu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.preferences.LocalSharedStorage
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveIconButton
import io.github.alexzhirkevich.cupertino.adaptive.ExperimentalAdaptiveApi
import moe.tlaster.precompose.koin.koinViewModel
import org.koin.compose.koinInject
import presentation.components.CustomCircleProgressbar
import presentation.viewmodels.CustomComponentsViewModel

@Composable
fun VerticalCustomText(headerText:String="",headerColor:Color=Color.Black,valueText:String="",valueColor:Color=Color.Black,modifier: Modifier=Modifier){
    Column(modifier = modifier.padding(7.dp)) {
        Text(headerText, style = StyleUtils.getRegularFontStyle(color = headerColor))
        Spacer(modifier = Modifier.height(2.dp))
        Text(valueText, style = StyleUtils.getBoldFontStyle(color = valueColor, size = 15.sp))
    }
}

@Composable
fun HorizontalCustomText(headerText:String="",headerColor:Color=Color.Black,valueText:String="",valueColor:Color=Color.Black,modifier: Modifier=Modifier){
    Row(modifier = modifier.padding(7.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(headerText, style = StyleUtils.getRegularFontStyle(headerColor))
        Spacer(modifier = Modifier.width(5.dp))
        Text(valueText, style = StyleUtils.getSemiBoldFontStyle(valueColor))
    }
}

@OptIn(ExperimentalAdaptiveApi::class)
@Composable
fun QRPickerTextField(headerText:String="", headerColor:Color=Color.Black, valueText:String="",onValueChange:(String)->Unit={}, isMandatory:Boolean=false, enableCharCount:Boolean=false, maxLength:Int=100, validation:Boolean=false, validationType:String?=null,modifier: Modifier=Modifier.fillMaxWidth())
{


    var enteredValue by remember { mutableStateOf(valueText) }
    var isLoading by remember { mutableStateOf(false) }
    var validationStatus by remember { mutableStateOf(StringResources.ValidationStatus.CLEAR) }
    var validationMessage by remember { mutableStateOf("") }


    val viewModel: CustomComponentsViewModel = koinViewModel(CustomComponentsViewModel::class)
    val localSharedStorage: LocalSharedStorage = koinInject()


    Column(modifier = modifier.padding(2.dp)) {

        Row(modifier = Modifier.fillMaxWidth(),verticalAlignment = Alignment.CenterVertically) {
            Text(headerText, style = StyleUtils.getRegularFontStyle(color = headerColor))
            if (isMandatory)
            {
                Text("*", style = StyleUtils.getRegularFontStyle(color = Color.Red))
            }
        }

        Spacer(modifier = Modifier.height(5.dp))

        Row(modifier = Modifier.fillMaxWidth().border(border =  BorderStroke(1.dp, ColorResources.ColorPrimary), shape = RoundedCornerShape(8.dp))) {

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = enteredValue,
                onValueChange = {
                    if (it.length <= maxLength){
                        enteredValue = it
                        onValueChange(it)
                        //validationStatus=StringResources.ValidationStatus.CLEAR
                    }
                },
                singleLine = true,
                placeholder = {
                    Text("Enter $headerText",style = StyleUtils.getRegularFontStyle(color = headerColor, size = 12.sp))
                },
                trailingIcon = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if (enteredValue.isNotEmpty()) {
                            AdaptiveIconButton(onClick = {
                                enteredValue = ""
                                //validationStatus=StringResources.ValidationStatus.CLEAR
                            }) {
                                Icon(
                                    imageVector = Icons.Outlined.Delete,
                                    contentDescription = null,
                                    tint = ColorResources.ColorPrimary
                                )
                            }
                        }
                        if (isLoading)
                        {
                            CustomCircleProgressbar( modifier = Modifier.height(25.dp).width(25.dp).padding(end = 5.dp))
                        }
                    }

                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    cursorColor = Color.Black,
                    disabledLabelColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                textStyle = StyleUtils.getBoldFontStyle(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done, keyboardType = KeyboardType.Text),
                keyboardActions = KeyboardActions(onDone = {
                    if (validation && !validationType.isNullOrEmpty() && enteredValue.isNotEmpty())
                    {
                        when (validationType) {
                            StringResources.ValidationTypes.ValidationType_PutAway_WarehouseOrder -> {
                                viewModel.getPutAwayWarehouseTasks(localSharedStorage.getWareHouse(),enteredValue,null,null,null,null,null)
                            }
                            StringResources.ValidationTypes.ValidationType_PutAway_WarehouseTask -> {
                                viewModel.getPutAwayWarehouseTasks(localSharedStorage.getWareHouse(),null,enteredValue,null,null,null,null)
                            }
                            StringResources.ValidationTypes.ValidationType_PutAway_PurchaseOrder -> {
                                viewModel.getPutAwayWarehouseTasks(localSharedStorage.getWareHouse(),null,null,enteredValue,null,null,null)
                            }
                            StringResources.ValidationTypes.ValidationType_PutAway_Inbound -> {
                                viewModel.getPutAwayWarehouseTasks(localSharedStorage.getWareHouse(),null,null,null,enteredValue,null,null)
                            }
                            StringResources.ValidationTypes.ValidationType_PutAway_Product -> {
                                viewModel.getPutAwayWarehouseTasks(localSharedStorage.getWareHouse(),null,null,null,null,enteredValue,null)
                            }
                        }
                    }
                })
            )

        }

        Row (modifier = Modifier.fillMaxWidth().padding(top = 5.dp)){

            if (validationStatus==StringResources.ValidationStatus.VALIDATED)
            {
                Text(
                    text = validationMessage,
                    textAlign = TextAlign.Start,
                    style = StyleUtils.getBoldFontStyle(Color.Green,12.sp)
                )
            }

            if (validationStatus==StringResources.ValidationStatus.INVALID)
            {
                Text(
                    text = validationMessage,
                    textAlign = TextAlign.Start,
                    style = StyleUtils.getBoldFontStyle(Color.Red,12.sp)
                )
            }

            if (enableCharCount)
            {
                Text(
                    text = "${enteredValue.length} / $maxLength",
                    modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                    textAlign = TextAlign.End,
                    style = StyleUtils.getBoldFontStyle()
                )
            }
        }

    }

    LaunchedEffect(Unit)
    {
        viewModel._uiState.collect{

            when {
                it.isLoading -> {
                    isLoading=true
                }

                it.error.isNotEmpty() -> {
                    validationMessage= StringResources.ValidationStatus.INVALID.name
                    validationStatus=StringResources.ValidationStatus.INVALID
                    isLoading=false
                }

                it.data!=null ->{
                    validationMessage= StringResources.ValidationStatus.VALIDATED.name
                    validationStatus=StringResources.ValidationStatus.VALIDATED
                    isLoading=false
                }

            }
        }
    }



}

@OptIn(ExperimentalAdaptiveApi::class)
@Composable
fun ChipQRPickerTextField(headerText:String="", headerColor:Color=Color.Black, valueText:String="", isMandatory:Boolean=false, validation:Boolean=false, validationType:String?=null, onChipSelected: (List<String>) -> Unit = { _ -> })
{

    var enteredValue by remember { mutableStateOf(valueText) }
    var isLoading by remember { mutableStateOf(false) }
    var validationStatus by remember { mutableStateOf(StringResources.ValidationStatus.CLEAR) }
    var validationMessage by remember { mutableStateOf("") }

    val chips = remember { mutableStateListOf<String>() }


    val viewModel: CustomComponentsViewModel = koinViewModel(CustomComponentsViewModel::class)
    val localSharedStorage: LocalSharedStorage = koinInject()

    Column(modifier = Modifier.fillMaxWidth().padding(2.dp)) {

        Row(modifier = Modifier.fillMaxWidth(),verticalAlignment = Alignment.CenterVertically) {
            Text(headerText, style = StyleUtils.getRegularFontStyle(color = headerColor))
            if (isMandatory)
            {
                Text("*", style = StyleUtils.getRegularFontStyle(color = Color.Red))
            }
        }

        Spacer(modifier = Modifier.height(5.dp))

        Row(modifier = Modifier.fillMaxWidth().border(border =  BorderStroke(1.dp, ColorResources.ColorPrimary), shape = RoundedCornerShape(8.dp))) {

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = enteredValue,
                onValueChange = {
                    enteredValue = it
                    validationStatus=StringResources.ValidationStatus.CLEAR
                },
                singleLine = true,
                placeholder = {
                    Text("Enter $headerText",style = StyleUtils.getRegularFontStyle(color = headerColor, size = 12.sp))
                },
                trailingIcon = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if (enteredValue.isNotEmpty()) {
                            AdaptiveIconButton(onClick = {
                                enteredValue = ""
                                validationStatus=StringResources.ValidationStatus.CLEAR
                            }) {
                                Icon(
                                    imageVector = Icons.Outlined.Delete,
                                    contentDescription = null,
                                )
                            }
                        }
                        if (isLoading)
                        {
                            CustomCircleProgressbar(modifier = Modifier.height(25.dp).width(25.dp).padding(end = 5.dp))
                        }
                    }

                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    cursorColor = Color.Black,
                    disabledLabelColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                textStyle = StyleUtils.getBoldFontStyle(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done, keyboardType = KeyboardType.Number),
                keyboardActions = KeyboardActions(onDone = {
                    if (validation && !validationType.isNullOrEmpty() && enteredValue.isNotEmpty())
                    {
                        /*chips.add(enteredValue)
                        enteredValue = ""*/
                        when (validationType) {
                            StringResources.ValidationTypes.ValidationType_PutAway_WarehouseOrder -> {
                                viewModel.getPutAwayWarehouseTasks(localSharedStorage.getWareHouse(),enteredValue,null,null,null,null,null)
                            }
                            StringResources.ValidationTypes.ValidationType_PutAway_WarehouseTask -> {
                                viewModel.getPutAwayWarehouseTasks(localSharedStorage.getWareHouse(),null,enteredValue,null,null,null,null)
                            }
                            StringResources.ValidationTypes.ValidationType_PutAway_PurchaseOrder -> {
                                viewModel.getPutAwayWarehouseTasks(localSharedStorage.getWareHouse(),null,null,enteredValue,null,null,null)
                            }
                            StringResources.ValidationTypes.ValidationType_PutAway_Inbound -> {
                                viewModel.getPutAwayWarehouseTasks(localSharedStorage.getWareHouse(),null,null,null,enteredValue,null,null)
                            }
                            StringResources.ValidationTypes.ValidationType_PutAway_Product -> {
                                viewModel.getPutAwayWarehouseTasks(localSharedStorage.getWareHouse(),null,null,null,null,enteredValue,null)
                            }
                        }
                    }
                })
            )

        }

        Column (modifier = Modifier.fillMaxWidth().padding(top = 5.dp)){

            if (validationStatus==StringResources.ValidationStatus.INVALID)
            {
                Text(
                    text = validationMessage,
                    textAlign = TextAlign.Start,
                    style = StyleUtils.getBoldFontStyle(Color.Red,12.sp)
                )
            }

            MaterialChipGroup(
                items = chips,
                onChipSelected = {
                    onChipSelected(it)
                }
            )

        }

    }

    LaunchedEffect(Unit)
    {

        viewModel._uiState.collect{
            when {
                it.isLoading && enteredValue.isNotEmpty()-> {
                    isLoading=true
                }

                it.error.isNotEmpty() && enteredValue.isNotEmpty()-> {
                    validationMessage= StringResources.ValidationStatus.INVALID.name
                    validationStatus=StringResources.ValidationStatus.INVALID
                    isLoading=false
                }

                it.data!=null && enteredValue.isNotEmpty() ->{
                    isLoading=false
                    chips.add(enteredValue)
                    enteredValue = ""
                    onChipSelected(chips.toList())
                }

            }
        }


    }

}

@Composable
fun MaterialChipGroup(
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues(2.dp),
    items: List<String> = emptyList(),
    cornerRadius: Dp = 16.dp,
    strokeSize: Dp = 1.dp,
    strokeColor: Color = ColorResources.ColorSecondary,
    horizontalPadding: Dp = 5.dp,
    textStyle: TextStyle=StyleUtils.getBoldFontStyle(color = ColorResources.ColorSecondary, size = 12.sp),
    onChipSelected: (List<String>) -> Unit = {  _ -> },
) {

    var allItems: List<String> by remember { mutableStateOf(items) }

    LazyRow(
        modifier = modifier.fillMaxWidth(),
        state = state,
        contentPadding = contentPadding,
        verticalAlignment = Alignment.CenterVertically,
        flingBehavior = ScrollableDefaults.flingBehavior(),
        reverseLayout = false,
        content = {

            itemsIndexed(allItems) { index, item ->

                MaterialChip(
                    text = item,
                    cornerRadius = cornerRadius,
                    strokeSize = strokeSize,
                    strokeColor = strokeColor,
                    horizontalPadding = horizontalPadding,
                    textStyle = textStyle,
                    onCloseClick = {
                        allItems = allItems.toMutableList().apply { removeAt(index) }
                        onChipSelected(allItems)
                    }
                )
            }
        }
    )
}

@OptIn(ExperimentalAdaptiveApi::class)
@Composable
fun MaterialChip(
    text: String,
    modifier: Modifier = Modifier,
    cornerRadius: Dp,
    strokeSize: Dp,
    strokeColor: Color,
    horizontalPadding: Dp,
    textStyle: TextStyle,
    onCloseClick: () -> Unit = {}
) {

    val chipShape = RoundedCornerShape(cornerRadius)

    Surface(
        shape = chipShape,
        modifier = modifier
            .padding(horizontalPadding)
            .wrapContentWidth()
            .height(30.dp)
            .border(width = strokeSize, color = strokeColor, shape = chipShape)
    ) {
        Row(modifier = modifier.wrapContentWidth().padding(5.dp), verticalAlignment = Alignment.CenterVertically , horizontalArrangement = Arrangement.SpaceBetween) {

            Spacer(modifier=Modifier.width(5.dp))

            Text(
                text = text,
                modifier = Modifier
                    .wrapContentHeight()
                    .wrapContentWidth()
                    .align(Alignment.CenterVertically),
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = textStyle
            )

            AdaptiveIconButton(onClick = {
                onCloseClick()
            }){
                Icon(
                    imageVector = Icons.Outlined.Close,
                    contentDescription = null,
                )
            }
        }
    }
}

@Composable
fun CustomDropDown(headerText:String="", headerColor:Color=Color.Black, isMandatory:Boolean=false,hint:String?=null, items: List<String>,modifier: Modifier=Modifier.fillMaxWidth(), onItemSelected: (String) -> Unit)
{

    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf(hint?:items.firstOrNull()) }

    Column(modifier = modifier.padding(2.dp)) {

        Row(modifier = Modifier.fillMaxWidth(),verticalAlignment = Alignment.CenterVertically) {
            Text(headerText, style = StyleUtils.getRegularFontStyle(color = headerColor))
            if (isMandatory)
            {
                Text("*", style = StyleUtils.getRegularFontStyle(color = Color.Red))
            }
        }

        Spacer(modifier = Modifier.height(5.dp))

        Row(modifier = Modifier.fillMaxWidth().background(Color.White).border(border =  BorderStroke(1.dp, ColorResources.ColorPrimary), shape = RoundedCornerShape(8.dp))) {

            Column {

                Row(horizontalArrangement = Arrangement.SpaceBetween , verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().clickable { expanded = !expanded }) {

                    val styleSelected= if (selectedItem==hint){
                        StyleUtils.getRegularFontStyle(size = 16.sp)
                    }else{
                        StyleUtils.getBoldFontStyle()
                    }

                    Text(text = selectedItem?:"", modifier = Modifier.padding(17.dp), style = styleSelected)

                    IconButton(onClick = {
                        expanded = !expanded
                    }){
                        Icon(Icons.Outlined.ArrowDropDown,null)
                    }
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    items.forEach { item ->
                        DropdownMenuItem(
                            onClick = {
                                selectedItem = item
                                expanded = false
                                onItemSelected(item)
                            }
                        ) {
                            Text(text = item, style = StyleUtils.getSemiBoldFontStyle())
                        }
                    }
                }
            }

        }

    }
}