package com.kimadrian.shopcalc

import android.app.Application
import com.kimadrian.shopcalc.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ShopCalcApp: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@ShopCalcApp)
            modules(appModule)
        }
    }
}