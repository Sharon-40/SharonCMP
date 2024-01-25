package com.incture.cmp

import StringResources
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import data.model.UserModel
import data.prefrences.LocalSharedStorage
import org.koin.android.ext.android.inject
import presentation.viewmodels.LoginViewModel

class RulesScreenActivity : ComponentActivity() {

    private val localSharedStorage: LocalSharedStorage by inject()
    private val viewModel: LoginViewModel by inject()

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

            Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {

                RulesScreen(viewModel,localSharedStorage.getUserId()) { userDetails->

                    userDetails.userParameters?.forEach {
                        if (it.parameterID == "WRK")//plant
                        {
                            localSharedStorage.savePlant(it.parameterValue)
                        } else if (it.parameterID == "LGN")//warehouse
                        {
                            localSharedStorage.saveWareHouse(it.parameterValue)
                        }
                    }

                    localSharedStorage.savePrinter(userDetails.outputDevice)

                    launchDashBoardActivity(applicationContext)
                }
            }
        }
    }

    @Composable
    fun RulesScreen(
        viewModel: LoginViewModel,
        userId: String,
        onComplete: (UserModel) -> Unit
    ) {

        val uiState = viewModel.uiState.collectAsState()

        viewModel.getProfile(userId)

        when {
            uiState.value.isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            !uiState.value.error.isNullOrEmpty() -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column {
                        Text(text = uiState.value.error.toString())
                        Button(onClick = {

                        }){
                            Text(StringResources.LogOut)
                        }
                    }
                }
            }

            uiState.value.data!=null ->{
                onComplete(uiState.value.data!!)
            }

        }

    }


}




