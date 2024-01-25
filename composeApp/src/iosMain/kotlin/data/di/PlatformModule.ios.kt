package data.di

import app_db.AppDatabase
import data.local_db.SqlDriverFactory
import data.prefrences.LocalSharedStorageFactory
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        single<HttpClient> {
            HttpClient {
                install(ContentNegotiation) {
                    json()
                }
            }
        }
        single { SqlDriverFactory(null).createSqlDriver() }
        single { AppDatabase.invoke(get()) }
        single { LocalSharedStorageFactory(null).createStoreInstance() }
    }