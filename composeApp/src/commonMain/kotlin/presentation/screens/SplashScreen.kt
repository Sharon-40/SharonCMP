package presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onSplashScreenFinished: () -> Unit) {

    LaunchedEffect(true) {
        delay(3000)
        onSplashScreenFinished()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorResources.ColorAccent),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = StringResources.AppName, style = TextStyle(color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold))
    }
}