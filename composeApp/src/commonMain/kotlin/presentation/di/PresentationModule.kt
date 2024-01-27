package presentation.di

import domain.use_cases.MainUseCase
import org.koin.dsl.module
import presentation.viewmodels.CustomComponentsViewModel
import presentation.viewmodels.LoginViewModel
import presentation.viewmodels.ProductListViewModel

val presentationModule = module {

    factory { ProductListViewModel(get<MainUseCase>()) }

    factory { LoginViewModel(get<MainUseCase>()) }

    factory { CustomComponentsViewModel(get<MainUseCase>()) }
}