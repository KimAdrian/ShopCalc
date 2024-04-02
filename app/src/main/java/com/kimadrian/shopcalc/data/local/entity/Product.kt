package com.kimadrian.shopcalc.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "inventory",
    indices = [Index(value = ["productName","unitPrice"], unique = true)]
)
data class Product(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(
        name = "productName",
        defaultValue = "empty"
    )
    val productName: String,
    @ColumnInfo(
        name = "unitPrice",
        defaultValue = "0.0"
    )
    val unitPrice: Double
)
