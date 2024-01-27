import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import data.font

object Utils {

    @Composable
    fun headerFontStyle(): TextStyle {
        return TextStyle(
            color = Color.Black,
            fontSize = 14.sp,
            fontFamily = getSemiBoldFont(),
            fontWeight = FontWeight.SemiBold
        )
    }

    @Composable
    fun valueFontStyle(): TextStyle {
        return TextStyle(
            color = Color.Black,
            fontSize = 16.sp,
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