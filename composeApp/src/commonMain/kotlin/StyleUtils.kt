import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.font

object StyleUtils {

    @Composable
    fun getRegularFontStyle(color:Color=Color.Black, size:TextUnit=12.sp): TextStyle {
        return TextStyle(
            color = color,
            fontSize = size,
            fontFamily = getRegularFont(),
            fontWeight = FontWeight.Normal
        )
    }

    @Composable
    fun getSemiBoldFontStyle(color:Color=Color.Black, size:TextUnit=14.sp): TextStyle {
        return TextStyle(
            color = color,
            fontSize = size,
            fontFamily = getSemiBoldFont(),
            fontWeight = FontWeight.SemiBold
        )
    }

    @Composable
    fun getBoldFontStyle(color:Color=Color.Black, size:TextUnit=16.sp): TextStyle {
        return TextStyle(
            color = color,
            fontSize = size,
            fontFamily = getBoldFont(),
            fontWeight = FontWeight.Bold
        )
    }

    @Composable
    fun getRegularFont(): FontFamily {
        return FontFamily(font("OpenSans Regular", "opensans_regular", FontWeight.Normal, FontStyle.Normal))
    }

    @Composable
    fun getSemiBoldFont(): FontFamily {
        return FontFamily(font("OpenSans SemiBold", "opensans_semibold", FontWeight.SemiBold, FontStyle.Normal))
    }

    @Composable
    fun getBoldFont(): FontFamily {
        return FontFamily(font("OpenSans Bold", "opensans_bold", FontWeight.Bold, FontStyle.Normal))
    }

    fun getStandardModifier():Modifier
    {
        return Modifier.fillMaxSize().background(ColorResources.Background).padding(10.dp)
    }

    @Composable
    fun getTypography(): Typography {
        val OpenSans = FontFamily(
            font(
                name = "OpenSans Regular",
                res = "opensans_regular",
                weight = FontWeight.Normal,
                style = FontStyle.Normal
            ),
            font(
                name = "OpenSans SemiBold",
                res = "opensans_semibold",
                weight = FontWeight.SemiBold,
                style = FontStyle.Normal
            ), font(
                name = "OpenSans Bold",
                res = "opensans_bold",
                weight = FontWeight.Bold,
                style = FontStyle.Normal
            )
        )

        return Typography(h1 = TextStyle(
            color = Color.Black,
            fontSize = 14.sp,
            fontFamily = OpenSans,
            fontWeight = FontWeight.Normal
        ), h2 = TextStyle(
            color = Color.Black,
            fontSize = 14.sp,
            fontFamily = OpenSans,
            fontWeight = FontWeight.SemiBold
        ), h3  = TextStyle(
            color = Color.Black,
            fontSize = 16.sp,
            fontFamily = OpenSans,
            fontWeight = FontWeight.Bold
        ))
    }
}