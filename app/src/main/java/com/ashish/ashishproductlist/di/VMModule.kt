package com.ashish.ashishproductlist.di

import com.ashish.ashishproductlist.rest.RepositoryImplementation
import com.ashish.ashishproductlist.ui.productlist.ProductListViewModel
import org.koin.dsl.module

val viewModelModule = module {
    factory { ProductListViewModel(get()) }
}

val repoImplementation = module {
    factory { RepositoryImplementation(get()) }
}
