package com.kimadrian.shopcalc.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kimadrian.shopcalc.data.local.dao.CartDao
import com.kimadrian.shopcalc.data.local.dao.InventoryDao
import com.kimadrian.shopcalc.data.local.entity.Cart
import com.kimadrian.shopcalc.data.local.entity.Product

@Database(entities = [Product::class, Cart::class], version = 5, exportSchema = false)
abstract class ShopDatabase: RoomDatabase() {

    abstract fun inventoryDao(): InventoryDao
    abstract fun cartDao(): CartDao

    companion object {
        const val DATABASE_NAME: String = "shop_db"
    }
}