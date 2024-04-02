package com.kimadrian.shopcalc.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.kimadrian.shopcalc.data.local.entity.Cart
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(cart: Cart)

    @Query("Select * FROM cart")
    fun getAllItems(): Flow<List<Cart>>

    @Update
    suspend fun updateItem(cart: Cart)

    @Delete
    suspend fun deleteItem(cart: Cart)
    
    @Query("Select SUM(totalCost) FROM cart")
    fun getSumOfTotalCost(): Flow<Double>

}