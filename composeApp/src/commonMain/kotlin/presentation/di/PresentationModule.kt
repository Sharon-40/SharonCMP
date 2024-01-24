package presentation.di

import com.liftric.kvault.KVault
import data.prefrences.LocalSharedStorage
import domain.use_cases.MainUseCase
import org.koin.dsl.module
import presentation.viewmodels.LoginViewModel
import presentation.viewmodels.ProductListViewModel

val presentationModule = module {

    factory { ProductListViewModel(get<MainUseCase>()) }

    factory { LoginViewModel(get<MainUseCase>()) }

    factory<LocalSharedStorage> { LocalSharedStorage(get<KVault>()) }
}