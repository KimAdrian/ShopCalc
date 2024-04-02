package com.kimadrian.shopcalc.data.repository

import com.kimadrian.shopcalc.data.local.dao.CartDao
import com.kimadrian.shopcalc.data.local.entity.Cart
import kotlinx.coroutines.flow.Flow

class CartRepository(private val cartDao: CartDao) {
    suspend fun insertItem(cart: Cart) {
        cartDao.insertItem(cart)
    }

    fun getAllItems(): Flow<List<Cart>> {
        return cartDao.getAllItems()
    }

    suspend fun updateItem(cart: Cart) {
        cartDao.updateItem(cart)
    }

    suspend fun deleteItem(cart: Cart) {
        cartDao.deleteItem(cart)
    }

    fun getSumOfTotalCost(): Flow<Double> {
        return cartDao.getSumOfTotalCost()
    }
}