package com.kimadrian.shopcalc.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kimadrian.shopcalc.ui.screens.CalculatorScreen
import com.kimadrian.shopcalc.ui.screens.InventoryScreen
import com.kimadrian.shopcalc.ui.viewmodel.MainViewModel

@Composable
fun BottomNavGraph(navController: NavHostController, paddingValues: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = BottomBarItems.Calculator.route,
        modifier = Modifier.padding(paddingValues = paddingValues)
    ) {
        composable(route = BottomBarItems.Calculator.route){
            CalculatorScreen()
        }
        composable(route = BottomBarItems.Inventory.route){
            InventoryScreen()
        }
    }
}