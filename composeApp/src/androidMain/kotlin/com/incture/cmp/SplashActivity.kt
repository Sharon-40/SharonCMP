package com.incture.cmp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.incture.cmp.sap.WelcomeActivity
import kotlinx.coroutines.delay
import ColorResources
import StringResources

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UI()
        }
    }

    @Preview
    @Composable
    private fun UI() {
        MaterialTheme {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                SplashScreen {
                    launchWelcomeActivity()
                }
            }
        }
    }

    @Composable
    fun SplashScreen(onSplashScreenFinished: () -> Unit) {

        LaunchedEffect(true) {
            delay(3000) // Simulate a 3-second splash screen duration
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

    private fun launchWelcomeActivity() {
        val intent = Intent(applicationContext, WelcomeActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        startActivity(intent)
    }
}



