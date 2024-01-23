package data.di

import app_db.AppDatabase
import data.local_db.SqlDriverFactory
import data.prefrences.LocalSharedStorageFactory
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        single { SqlDriverFactory(null).createSqlDriver() }
        single { AppDatabase.invoke(get()) }
        single { LocalSharedStorageFactory(null).createStoreInstance() }
    }