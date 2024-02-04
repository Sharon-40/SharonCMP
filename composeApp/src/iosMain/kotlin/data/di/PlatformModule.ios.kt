package data.di


import app_db.AppDatabase
import data.Utils
import data.local_db.SqlDriverFactory
import data.preferences.LocalSharedStorage
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        single<HttpClient> {
            HttpClient {

                install(ContentNegotiation) {
                    json(
                        kotlinx.serialization.json.Json {
                            ignoreUnknownKeys = true
                        },contentType = ContentType.Application.Json,)
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
        single { SqlDriverFactory(null).createSqlDriver() }
        single { AppDatabase.invoke(get()) }
        single { Utils(null) }
    }