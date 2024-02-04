package presentation.screens.oauth

import ColorResources
import StringResources
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import data.Utils
import data.logs.LogUtils
import data.model.StandardResponse
import data.model.UserModel
import data.oauth.AccessTokenModel
import data.preferences.LocalSharedStorage
import data.respository.MainRepository
import data.utils.NetworkResult
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode
import moe.tlaster.precompose.navigation.Navigator
import presentation.components.CustomCircleProgressbar
import presentation.components.ToolBarWithBack
import presentation.navigation.NavigationRoute

@Composable
fun OAuth2Screen(navigator: Navigator, mainRepository: MainRepository, localSharedStorage: LocalSharedStorage, utils: Utils) {

    Scaffold {

        Column(modifier = Modifier.fillMaxSize().background(ColorResources.Background).padding(10.dp)) {

            CustomCircleProgressbar()

            LaunchedEffect(Unit)
            {

                try {
                    val response = mainRepository.getAccessTokenByCode(localSharedStorage.getAuthCode())

                    if (response.status == HttpStatusCode.OK) {

                        val result = response.body<AccessTokenModel>()

                        LogUtils.logDebug(StringResources.RESPONSE, result.toString())

                        with(localSharedStorage)
                        {
                            saveAccessToken(result.accessToken)
                            saveRefreshToken(result.refreshToken)

                            saveUserId("JN")
                            saveUserName("JN")
                        }

                        navigator.navigate(NavigationRoute.BussRules.route)

                    } else {
                        utils.makeToast(response.status.toString())
                        LogUtils.logDebug(StringResources.RESPONSE_ERROR, response.status.toString())
                    }
                }catch (e:Exception)
                {
                    LogUtils.logDebug(StringResources.RESPONSE_ERROR, e.message.toString())
                    utils.makeToast(e.toString())
                }
            }
        }
    }

}