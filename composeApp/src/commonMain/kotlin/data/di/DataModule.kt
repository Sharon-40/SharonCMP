package data.di

import app_db.AppDatabase
import data.local_db.LocalDBInterface
import data.local_db.LocalDbDao
import data.network.ApiInterfaceImpl
import data.network.ApiInterface
import data.respository.MainRepository
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import org.koin.dsl.module

val dataModule = module {

    single<HttpClient> {
        HttpClient {
            install(ContentNegotiation) {
                json()
            }
        }
    }

    factory<ApiInterfaceImpl> { ApiInterfaceImpl(get<HttpClient>()) }

    factory<LocalDbDao> { LocalDbDao(get<AppDatabase>()) }

    factory<MainRepository> { MainRepository(get<ApiInterfaceImpl>(),get<LocalDbDao>()) }
}