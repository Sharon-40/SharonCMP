package data.di

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app_db.AppDatabase
import com.sap.cloud.mobile.foundation.common.ClientProvider
import data.Utils
import data.local_db.SqlDriverFactory
import data.preferences.KVaultFactory
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module

    get() = module {

        factory<HttpClient> {

            HttpClient(OkHttp) {

                install(ContentNegotiation) {
                    json(kotlinx.serialization.json.Json {
                        ignoreUnknownKeys = true
                    })
                }

                engine {
                    config {
                        ClientProvider.get().interceptors.forEach {
                            addInterceptor(it)
                        }
                    }
                }
            }
        }

        single { SqlDriverFactory(get<Context>()).createSqlDriver() }
        single { AppDatabase.invoke(get<SqlDriver>()) }
        single { KVaultFactory(get<Context>()).createStoreInstance()}

        single { Utils(get<Context>())}
    }