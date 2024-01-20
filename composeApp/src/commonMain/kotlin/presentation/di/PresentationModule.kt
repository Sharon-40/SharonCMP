package presentation.di

import domain.use_cases.MainUseCase
import org.koin.dsl.module
import presentation.screens.ProductListViewModel

val presentationModule = module {

    factory { ProductListViewModel(get<MainUseCase>()) }
}