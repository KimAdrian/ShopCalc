package com.kimadrian.shopcalc.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "cart",
    foreignKeys = [
        ForeignKey(
            entity = Product::class,
            parentColumns = ["productName", "unitPrice"],
            childColumns = ["productName", "unitPrice"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["productName","unitPrice"], unique = true)]
)
data class Cart(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "productName")
    val productName: String,
    @ColumnInfo(name = "unitPrice")
    val unitPrice: Double,
    @ColumnInfo(
        name = "amount",
        defaultValue = "0.0"
    )
    val amount: Double,
    @ColumnInfo(
        name = "totalCost",
        defaultValue = "0.0"
    )
    val totalCost: Double
)
