package presentation.di

import data.preferences.LocalSharedStorage
import domain.use_cases.MainUseCase
import org.koin.dsl.module
import presentation.viewmodels.BinToBinViewModel
import presentation.viewmodels.CustomComponentsViewModel
import presentation.viewmodels.LoginViewModel
import presentation.viewmodels.ProductListViewModel
import presentation.viewmodels.PutAwayViewModel

val presentationModule = module {

    factory { ProductListViewModel(get<MainUseCase>()) }

    factory { LoginViewModel(get<MainUseCase>()) }

    factory { CustomComponentsViewModel(get<MainUseCase>()) }

    factory { BinToBinViewModel(get<MainUseCase>(),get<LocalSharedStorage>()) }

    factory { PutAwayViewModel(get<MainUseCase>()) }
}