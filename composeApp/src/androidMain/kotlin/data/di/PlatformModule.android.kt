package data.di

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app_db.AppDatabase
import com.sap.cloud.mobile.foundation.common.ClientProvider
import data.Utils
import data.local_db.SqlDriverFactory
import data.preferences.LocalSharedStorage
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import org.koin.core.module.Module
import org.koin.dsl.module
import java.time.Duration

actual val platformModule: Module

    get() = module {

        factory<HttpClient> {

            HttpClient(OkHttp) {

                install(ContentNegotiation) {
                    json(
                        kotlinx.serialization.json.Json {
                        ignoreUnknownKeys = true
                    },contentType = ContentType.Application.Json,)
                }


                engine {
                    config {
                       /* ClientProvider.get().interceptors.forEach {
                            addInterceptor(it)
                        }*/
                        connectTimeout(Duration.ofMinutes(5))
                        writeTimeout(Duration.ofMinutes(5))
                        readTimeout(Duration.ofMinutes(5))
                    }
                }

                val localSharedStorage=get<LocalSharedStorage>()

                defaultRequest {
                    header(
                        "Authorization",
                        "Bearer ${localSharedStorage.getAccessToken()}"
                    )
                }
            }
        }

        single { SqlDriverFactory(get<Context>()).createSqlDriver() }
        single { AppDatabase.invoke(get<SqlDriver>()) }
        single { Utils(get<Context>())}
    }