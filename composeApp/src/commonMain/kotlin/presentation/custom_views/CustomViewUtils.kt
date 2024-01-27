package presentation.custom_views

import ColorResources
import StringResources
import Utils
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.FlingBehavior
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
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.preferences.LocalSharedStorage
import moe.tlaster.precompose.koin.koinViewModel
import org.koin.compose.koinInject
import presentation.viewmodels.CustomComponentsViewModel

@Composable
fun VerticalCustomText(headerText:String="",headerColor:Color=Color.Black,valueText:String="",valueColor:Color=Color.Black){
    Column(modifier = Modifier.padding(10.dp)) {
        Text(headerText, style = Utils.headerFontStyle(color = headerColor))
        Spacer(modifier = Modifier.height(2.dp))
        Text(valueText, style = Utils.valueFontStyle(color = valueColor))
    }
}

@Composable
fun HorizontalCustomText(headerText:String="",headerColor:Color=Color.Black,valueText:String="",valueColor:Color=Color.Black){
    Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(headerText, style = Utils.headerFontStyle(headerColor))
        Spacer(modifier = Modifier.width(2.dp))
        Text(valueText, style = Utils.valueFontStyle(valueColor))
    }
}

@Composable
fun QRPickerTextField(headerText:String="", headerColor:Color=Color.Black, valueText:String="", isMandatory:Boolean=false, enableCharCount:Boolean=false, maxLength:Int=100, validation:Boolean=false, validationType:String?=null)
{


    var enteredValue by remember { mutableStateOf(valueText) }
    var isLoading by remember { mutableStateOf(false) }
    var validationStatus by remember { mutableStateOf(StringResources.ValidationStatus.CLEAR) }
    var validationMessage by remember { mutableStateOf("") }


    val viewModel: CustomComponentsViewModel = koinViewModel(CustomComponentsViewModel::class)
    val localSharedStorage: LocalSharedStorage = koinInject()

    val uiState = viewModel.uiState.collectAsState()

    Column(modifier = Modifier.fillMaxWidth().padding(2.dp)) {

        Row(modifier = Modifier.fillMaxWidth(),verticalAlignment = Alignment.CenterVertically) {
            Text(headerText, style = Utils.headerFontStyle(color = headerColor))
            if (isMandatory)
            {
                Text("*", style = Utils.headerFontStyle(color = Color.Red))
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
                        //validationStatus=StringResources.ValidationStatus.CLEAR
                    }
                },
                singleLine = true,
                placeholder = {
                    Text("Enter $headerText",style = Utils.headerFontStyle(color = headerColor, size = 12.sp))
                },
                trailingIcon = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if (enteredValue.isNotEmpty()) {
                            IconButton(onClick = {
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
                            CircularProgressIndicator(color = ColorResources.ColorAccent, modifier = Modifier.height(25.dp).width(25.dp).padding(end = 5.dp))
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
                textStyle = Utils.valueFontStyle(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done, keyboardType = KeyboardType.Number),
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
                    style = Utils.valueFontStyle(Color.Green,12.sp)
                )
            }

            if (validationStatus==StringResources.ValidationStatus.INVALID)
            {
                Text(
                    text = validationMessage,
                    textAlign = TextAlign.Start,
                    style = Utils.valueFontStyle(Color.Red,12.sp)
                )
            }

            if (enableCharCount)
            {
                Text(
                    text = "${enteredValue.length} / $maxLength",
                    modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                    textAlign = TextAlign.End,
                    style = Utils.valueFontStyle()
                )
            }
        }

    }

    when {
        uiState.value.isLoading && enteredValue.isNotEmpty()-> {
            isLoading=true
        }

        !uiState.value.error.isNullOrEmpty() && enteredValue.isNotEmpty()-> {
            validationMessage= StringResources.ValidationStatus.INVALID.name
            validationStatus=StringResources.ValidationStatus.INVALID
            isLoading=false
        }

        uiState.value.data!=null && enteredValue.isNotEmpty()->{
            validationMessage= StringResources.ValidationStatus.VALIDATED.name
            validationStatus=StringResources.ValidationStatus.VALIDATED
            isLoading=false
        }

    }

}

@Composable
fun ChipQRPickerTextField(headerText:String="", headerColor:Color=Color.Black, valueText:String="", isMandatory:Boolean=false, validation:Boolean=false, validationType:String?=null, onChipSelected: (List<String>) -> Unit = { _ -> })
{


    var enteredValue by remember { mutableStateOf(valueText) }
    var isLoading by remember { mutableStateOf(false) }
    var validationStatus by remember { mutableStateOf(StringResources.ValidationStatus.CLEAR) }
    var validationMessage by remember { mutableStateOf("") }

    var chips:List<String> by remember { mutableStateOf(arrayListOf()) }


    val viewModel: CustomComponentsViewModel = koinViewModel(CustomComponentsViewModel::class)
    val localSharedStorage: LocalSharedStorage = koinInject()

    val uiState = viewModel.uiState.collectAsState()

    Column(modifier = Modifier.fillMaxWidth().padding(2.dp)) {

        Row(modifier = Modifier.fillMaxWidth(),verticalAlignment = Alignment.CenterVertically) {
            Text(headerText, style = Utils.headerFontStyle(color = headerColor))
            if (isMandatory)
            {
                Text("*", style = Utils.headerFontStyle(color = Color.Red))
            }
        }

        Spacer(modifier = Modifier.height(5.dp))

        Row(modifier = Modifier.fillMaxWidth().border(border =  BorderStroke(1.dp, ColorResources.ColorPrimary), shape = RoundedCornerShape(8.dp))) {

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = enteredValue,
                onValueChange = {
                    enteredValue = it
                    //validationStatus=StringResources.ValidationStatus.CLEAR
                },
                singleLine = true,
                placeholder = {
                    Text("Enter $headerText",style = Utils.headerFontStyle(color = headerColor, size = 12.sp))
                },
                trailingIcon = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if (enteredValue.isNotEmpty()) {
                            IconButton(onClick = {
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
                            CircularProgressIndicator(color = ColorResources.ColorAccent, modifier = Modifier.height(25.dp).width(25.dp).padding(end = 5.dp))
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
                textStyle = Utils.valueFontStyle(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done, keyboardType = KeyboardType.Number),
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

        Column (modifier = Modifier.fillMaxWidth().padding(top = 5.dp)){

            if (validationStatus==StringResources.ValidationStatus.INVALID)
            {
                Text(
                    text = validationMessage,
                    textAlign = TextAlign.Start,
                    style = Utils.valueFontStyle(Color.Red,12.sp)
                )
            }

            if (chips.isNotEmpty())
            {
                MaterialChipGroup(
                    items = chips,
                    onChipSelected = {
                        onChipSelected(it)
                    }
                )
            }


        }

    }

    when {
        uiState.value.isLoading && enteredValue.isNotEmpty()-> {
            isLoading=true
        }

        !uiState.value.error.isNullOrEmpty() && enteredValue.isNotEmpty()-> {
            validationMessage= StringResources.ValidationStatus.INVALID.name
            validationStatus=StringResources.ValidationStatus.INVALID
            isLoading=false
        }

        uiState.value.data!=null && enteredValue.isNotEmpty()->{
            isLoading=false
            chips = chips.toMutableList().apply { add(enteredValue) }
            //chips= arrayListOf(enteredValue)
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
    textStyle: TextStyle=Utils.valueFontStyle(color = ColorResources.ColorSecondary, size = 12.sp),
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
        Row(modifier = modifier.wrapContentWidth(), horizontalArrangement = Arrangement.Center) {

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

            IconButton(onClick = {
                onCloseClick()
            }){
                Icon(
                    imageVector = Icons.Outlined.Close,
                    contentDescription = null,
                    tint = ColorResources.ColorPrimary
                )
            }
        }
    }
}