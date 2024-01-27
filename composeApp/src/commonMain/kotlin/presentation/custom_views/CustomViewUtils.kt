package presentation.custom_views

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
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

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
fun QRPickerTextFiled(headerText:String="",headerColor:Color=Color.Black,valueText:String="",valueColor:Color=Color.Black,isMandatory:Boolean=false,enableCharCount:Boolean=false,maxLength:Int=100)
{

    var enteredValue by remember { mutableStateOf(valueText) }

    Column(modifier = Modifier.fillMaxWidth().padding(2.dp)) {

        Row(modifier = Modifier.fillMaxWidth(),verticalAlignment = Alignment.CenterVertically) {
            Text(headerText, style = Utils.headerFontStyle(color = headerColor))
            if (isMandatory)
            {
                Text("*", style = Utils.headerFontStyle(color = Color.Red))
            }
        }

        Row(modifier = Modifier.fillMaxWidth().border(border =  BorderStroke(1.dp, ColorResources.ColorPrimary), shape = RoundedCornerShape(8.dp))) {

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = enteredValue,
                onValueChange = {
                    if (it.length <= maxLength){
                        enteredValue = it
                    }
                },
                singleLine = true,
                trailingIcon = {
                    if (enteredValue.isNotEmpty()) {
                        IconButton(onClick = { enteredValue = "" }) {
                            Icon(
                                imageVector = Icons.Outlined.Delete,
                                contentDescription = null
                            )
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