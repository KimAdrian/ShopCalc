package com.kimadrian.shopcalc.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarItems(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    data object Calculator: BottomBarItems(
        route = "calculator",
        title = "Price Calculator",
        icon = Icons.Default.Calculate
    )

    data object Inventory: BottomBarItems(
        route = "inventory",
        title = "Inventory",
        icon = Icons.Default.Book
    )


}

fun bottomBarItemsList()  = listOf(BottomBarItems.Calculator, BottomBarItems.Inventory)

