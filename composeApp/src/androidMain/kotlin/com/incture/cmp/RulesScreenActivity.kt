package com.incture.cmp

import ColorResources
import StringResources
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sap.cloud.mobile.foundation.mobileservices.ServiceListener
import com.sap.cloud.mobile.foundation.mobileservices.ServiceResult
import com.sap.cloud.mobile.foundation.user.User
import com.sap.cloud.mobile.foundation.user.UserService
import data.prefrences.LocalSharedStorage
import org.koin.android.ext.android.inject
import presentation.viewmodels.LoginStateHolder
import presentation.viewmodels.LoginViewModel

class RulesScreenActivity : ComponentActivity() {

    private val localSharedStorage: LocalSharedStorage by inject()
    private val viewModel: LoginViewModel by inject()

    var state: LoginStateHolder by mutableStateOf(LoginStateHolder(isLoading = true))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UI()
        }

        init()

    }

    private fun init()
    {
        getUserId()
    }

    @Preview
    @Composable
    private fun UI() {
        MaterialTheme {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                RulesScreen()
            }
        }
    }

    @Composable
    fun RulesScreen() {
        Column(
            modifier = Modifier.fillMaxSize().background(ColorResources.ColorAccent),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (state.isLoading)
            {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }else if (state.error.isNotEmpty())
            {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column {
                        Text(text = state.error)
                        Button(onClick = {

                        }){
                            Text(StringResources.LogOut)
                        }
                    }
                }
            }
        }
    }

    private fun getUserId()
    {
        UserService().retrieveUser(object : ServiceListener<User> {
            override fun onServiceDone(result: ServiceResult<User>) {
                if (result is ServiceResult.SUCCESS) {
                    localSharedStorage.saveUserId(result.data?.id?:"")
                    localSharedStorage.saveUserName(result.data?.userName?:"")
                    getProfileData(localSharedStorage.getUserId())
                } else if (result is ServiceResult.FAILURE) {
                    state= LoginStateHolder(error = result.message)
                }
            }
        })
    }

    fun getProfileData(userId:String)
    {
        viewModel.getProfile(userId)

        when {
            viewModel.uiState.value.isLoading -> {
                state= LoginStateHolder(isLoading = true)
            }

            viewModel.uiState.value.error.isNotEmpty() -> {
                state= LoginStateHolder(error=viewModel.uiState.value.error)
            }

            else -> {

                viewModel.uiState.value.data?.let { userDetails->

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

}




