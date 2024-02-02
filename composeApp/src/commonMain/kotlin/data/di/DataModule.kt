package data.di

import app_db.AppDatabase
import com.russhwolf.settings.Settings
import data.local_db.LocalDbDao
import data.network.ApiInterfaceImpl
import data.preferences.LocalSharedStorage
import data.respository.MainRepository
import io.ktor.client.HttpClient
import org.koin.dsl.module

val dataModule = module {

    factory { Settings() }

    factory<LocalSharedStorage> { LocalSharedStorage(get<Settings>()) }

    factory<ApiInterfaceImpl> { ApiInterfaceImpl(get<HttpClient>(),get<LocalSharedStorage>()) }

    factory<LocalDbDao> { LocalDbDao(get<AppDatabase>()) }

    factory<MainRepository> { MainRepository(get<ApiInterfaceImpl>(),get<LocalDbDao>()) }

}