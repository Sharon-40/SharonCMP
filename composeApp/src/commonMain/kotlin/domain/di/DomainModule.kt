package domain.di

import data.respository.MainRepository
import domain.use_cases.MainUseCase
import org.koin.dsl.module

val domainModule = module {

    factory { MainUseCase(get<MainRepository>()) }

}