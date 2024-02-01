package data.di

import app_db.AppDatabase
import com.liftric.kvault.KVault
import data.local_db.LocalDbDao
import data.network.ApiInterfaceImpl
import data.preferences.LocalSharedStorage
import data.respository.MainRepository
import io.ktor.client.HttpClient
import org.koin.dsl.module

val dataModule = module {

    factory<ApiInterfaceImpl> { ApiInterfaceImpl(get<HttpClient>(),get<LocalSharedStorage>()) }

    factory<LocalDbDao> { LocalDbDao(get<AppDatabase>()) }

    factory<MainRepository> { MainRepository(get<ApiInterfaceImpl>(),get<LocalDbDao>()) }

    factory<LocalSharedStorage> { LocalSharedStorage(get<KVault>()) }
}