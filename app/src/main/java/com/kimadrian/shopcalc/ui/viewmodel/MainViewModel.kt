package com.kimadrian.shopcalc.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.asIntState
import androidx.compose.runtime.asLongState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.kimadrian.shopcalc.data.local.entity.Cart
import com.kimadrian.shopcalc.data.local.entity.Product
import com.kimadrian.shopcalc.data.repository.CartRepository
import com.kimadrian.shopcalc.data.repository.InventoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.core.KoinApplication.Companion.init

class MainViewModel(
    private val inventoryRepository: InventoryRepository,
    private val cartRepository: CartRepository
): ViewModel() {

    private val _productsStateFlow = MutableStateFlow<List<Product>>(emptyList())
    val productsStateFlow: StateFlow<List<Product>> = _productsStateFlow

    private val _cartItemsStateFlow = MutableStateFlow<List<Cart>>(emptyList())
    val cartItemsStateFlow: StateFlow<List<Cart>> = _cartItemsStateFlow

    private val _totalCostSumStateFlow = MutableStateFlow(0.0)
    val totalCostSumStateFlow: StateFlow<Double> = _totalCostSumStateFlow

    fun insertProduct(product: Product) = viewModelScope.launch(Dispatchers.IO) {
        inventoryRepository.insertProduct(product)
    }

    fun updateProductDetails(product: Product) = viewModelScope.launch(Dispatchers.IO) {
        inventoryRepository.updateProduct(product)
    }

    fun  deleteProduct(product: Product) = viewModelScope.launch(Dispatchers.IO) {
        inventoryRepository.deleteProduct(product)
    }

    fun insertItemInCart(cart: Cart) = viewModelScope.launch(Dispatchers.IO) {
        cartRepository.insertItem(cart)
    }

    fun updateItemInCart(cart: Cart) = viewModelScope.launch(Dispatchers.IO) {
        cartRepository.updateItem(cart)
    }

    fun deleteItemInCart(cart: Cart) = viewModelScope.launch(Dispatchers.IO) {
        cartRepository.deleteItem(cart)
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            inventoryRepository.getAllProducts().collect { productsList ->
                _productsStateFlow.value = productsList
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.getAllItems().collect { cartList ->
                _cartItemsStateFlow.value = cartList
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.getSumOfTotalCost().collect { sumOfTotalCost ->
                _totalCostSumStateFlow.value = sumOfTotalCost
            }
        }
    }

}