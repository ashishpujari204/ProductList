package com.ashish.ashishproductlist

import android.app.Application
import com.ashish.ashishproductlist.di.repoImplementation
import com.ashish.ashishproductlist.di.retrofitModule
import com.ashish.ashishproductlist.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class ProductApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ProductApplication)
            androidLogger(Level.ERROR)
            modules(
                listOf(
                    viewModelModule,
                    retrofitModule,
                    repoImplementation
                )
            )
        }
    }
}