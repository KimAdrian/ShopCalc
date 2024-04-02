package com.kimadrian.shopcalc.data.repository

import com.kimadrian.shopcalc.data.local.dao.InventoryDao
import com.kimadrian.shopcalc.data.local.entity.Product
import kotlinx.coroutines.flow.Flow

class InventoryRepository(private val inventoryDao: InventoryDao) {
    suspend fun insertProduct(product: Product){
        inventoryDao.insertProduct(product)
    }

   fun getAllProducts(): Flow<List<Product>> {
        return inventoryDao.getAllProducts()
    }

    suspend fun updateProduct(product: Product) {
        inventoryDao.updateProduct(product)
    }

    suspend fun deleteProduct(product: Product) {
        inventoryDao.deleteProduct(product)
    }
}