package com.kimadrian.shopcalc.di

import androidx.room.Room
import com.kimadrian.shopcalc.data.local.dao.CartDao
import com.kimadrian.shopcalc.data.local.dao.InventoryDao
import com.kimadrian.shopcalc.data.local.database.ShopDatabase
import com.kimadrian.shopcalc.data.repository.CartRepository
import com.kimadrian.shopcalc.data.repository.InventoryRepository
import com.kimadrian.shopcalc.ui.viewmodel.MainViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single {
        Room.databaseBuilder(
            context = androidApplication(),
            klass = ShopDatabase::class.java,
            name = ShopDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    single<InventoryDao> {
        val shopDB = get<ShopDatabase>()
        shopDB.inventoryDao()
    }

    single<CartDao> {
        val shopDB = get<ShopDatabase>()
        shopDB.cartDao()
    }

    single {
        provideInventoryRepository(get())
    }

    single {
        provideCartRepository(get())
    }

    viewModel {
        MainViewModel(get(), get())
    }
}

private fun provideInventoryRepository(inventoryDao: InventoryDao): InventoryRepository {
    return InventoryRepository(inventoryDao)
}

private fun provideCartRepository(cartDao: CartDao): CartRepository {
    return CartRepository(cartDao)
}