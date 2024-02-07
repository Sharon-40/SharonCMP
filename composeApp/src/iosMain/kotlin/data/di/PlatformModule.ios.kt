package data.di


import app_db.AppDatabase
import data.PlatformUtils
import data.local_db.SqlDriverFactory
import data.oauth.AccessTokenModel
import data.oauth.OAuthConfig
import data.preferences.LocalSharedStorage
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.forms.submitForm
import io.ktor.http.ContentType
import io.ktor.http.Parameters
import io.ktor.serialization.kotlinx.json.json
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        factory<HttpClient> {
            HttpClient {

                install(ContentNegotiation) {
                    json(
                        kotlinx.serialization.json.Json {
                            ignoreUnknownKeys = true
                        },contentType = ContentType.Application.Json,)
                }

                install(HttpTimeout) {
                    requestTimeoutMillis = 300000
                    connectTimeoutMillis = 300000
                    socketTimeoutMillis = 300000
                }

                val localSharedStorage=get<LocalSharedStorage>()

                /*defaultRequest {
                    header(
                        "Authorization",
                        "Bearer ${localSharedStorage.getAccessToken()}"
                    )
                }*/

                install(Auth){
                    bearer {
                        loadTokens {
                            BearerTokens(localSharedStorage.getAccessToken(),localSharedStorage.getRefreshToken())
                        }

                        refreshTokens {
                            val refreshTokenInfo: AccessTokenModel = client.submitForm(
                                url = OAuthConfig.TOKEN_END_POINT,
                                formParameters = Parameters.build {
                                    append("grant_type", "refresh_token")
                                    append("client_id", OAuthConfig.CLIENT_ID)
                                    append("refresh_token", localSharedStorage.getRefreshToken())
                                }
                            ) { markAsRefreshTokenRequest() }.body()

                            with(localSharedStorage)
                            {
                                saveAccessToken(refreshTokenInfo.accessToken)
                                saveRefreshToken(refreshTokenInfo.refreshToken)
                            }

                            BearerTokens(localSharedStorage.getAccessToken(),localSharedStorage.getRefreshToken())
                        }
                    }
                }
            }
        }
        single { SqlDriverFactory(null).createSqlDriver() }
        single { AppDatabase.invoke(get()) }
        single { PlatformUtils(null) }
    }