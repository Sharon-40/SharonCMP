package presentation.screens.oauth

import ColorResources
import StringResources
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import com.multiplatform.webview.web.LoadingState
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewState
import data.Utils
import data.logs.LogUtils
import data.oauth.OAuthConfig
import data.preferences.LocalSharedStorage

@Composable
fun OAuth2WebView(utils: Utils, localSharedStorage: LocalSharedStorage, onAuthorizationCodeReceived: (String) -> Unit) {

    val url="${OAuthConfig.AUTH_END_POINT}?response_type=code&client_id=${OAuthConfig.CLIENT_ID}&redirect_uri=${OAuthConfig.REDIRECT_URL}"

    val webViewState = rememberWebViewState(url)

    LogUtils.logDebug(StringResources.RESPONSE,url)

    Scaffold(topBar = {
        TopAppBar(
            contentColor = Color.White,
            backgroundColor = ColorResources.ColorPrimary
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Image(imageVector = Icons.Default.Done, contentDescription = null, modifier = Modifier.clickable {

                    val currentUrl = webViewState.lastLoadedUrl
                    LogUtils.logDebug(StringResources.RESPONSE,currentUrl.toString())
                    if (currentUrl?.startsWith(OAuthConfig.REDIRECT_URL) == true) {
                        utils.getQueryParameter(currentUrl,"code")?.let {
                            LogUtils.logDebug(StringResources.RESPONSE,it)
                            localSharedStorage.saveAuthCode(it)
                            onAuthorizationCodeReceived(it)
                        }
                    }

                }, colorFilter = ColorFilter.tint(color = Color.White))
            }

        }
    }) {

        Column(Modifier.fillMaxSize()) {

            val loadingState = webViewState.loadingState
            if (loadingState is LoadingState.Loading) {
                LinearProgressIndicator(
                    progress = loadingState.progress,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            WebView(webViewState)
        }
    }



}