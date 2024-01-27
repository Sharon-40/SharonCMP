package presentation.custom_views

import ColorResources
import StringResources
import Utils
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
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
fun QRPickerTextFiled(headerText:String="",headerColor:Color=Color.Black,valueText:String="",isMandatory:Boolean=false,enableCharCount:Boolean=false,maxLength:Int=100,validation:Boolean=false,validationType:String?=null)
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