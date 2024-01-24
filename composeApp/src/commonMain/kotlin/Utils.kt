import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

object Utils {



    fun headerFontStyle():TextStyle
    {
        return TextStyle(color = Color.Black, fontSize = 14.sp, fontWeight = FontWeight.Normal)
    }

    fun valueFontStyle():TextStyle
    {
        return TextStyle(color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }
}