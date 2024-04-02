package com.kimadrian.shopcalc.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.kimadrian.shopcalc.R
import com.kimadrian.shopcalc.data.local.entity.Cart
import com.kimadrian.shopcalc.ui.components.CartItemCard
import com.kimadrian.shopcalc.ui.components.CheckOutCard
import com.kimadrian.shopcalc.ui.components.ImageAndLabel
import com.kimadrian.shopcalc.ui.viewmodel.MainViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorScreen(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel = koinViewModel()
    ) {

    var showDialog by remember {
        mutableStateOf(false)
    }
    var showCheckoutDialog by remember {
        mutableStateOf(false)
    }
    var isExpanded by remember {
        mutableStateOf(false)
    }
    var product by remember {
        mutableStateOf("")
    }
    var amount by remember {
        mutableStateOf("")
    }
    var unitPrice by remember {
        mutableDoubleStateOf(0.0)
    }
    var extendedFabVisibility by rememberSaveable { mutableStateOf(true) }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                // Hide FAB
                if (available.y < -1) {
                    extendedFabVisibility = false
                }

                // Show FAB
                if (available.y > 1) {
                    extendedFabVisibility = true
                }

                return Offset.Zero
            }
        }
    }


    val inventoryList by mainViewModel.productsStateFlow.collectAsState(initial = emptyList())
    val cartList by mainViewModel.cartItemsStateFlow.collectAsState()
    val sumOfTotalCost by mainViewModel.totalCostSumStateFlow.collectAsState()

    Scaffold { contentPadding ->
        val context = LocalContext.current
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(contentPadding)
        ){
            if (cartList.isEmpty()) {
                ImageAndLabel(
                    modifier = modifier.align(Alignment.Center),
                    imageVector = ImageVector.vectorResource(id = R.drawable.undraw_empty_cart),
                    label = "Please add items to cart")
            } else {
                Column {
                    CheckOutCard(
                        modifier = modifier,
                        cartList = cartList,
                        totalCost = sumOfTotalCost
                    )
                    LazyColumn(
                        content = {
                            items(cartList) { cart ->
                                CartItemCard(cart = cart)
                            }
                        },
                        modifier = modifier.nestedScroll(nestedScrollConnection)
                    )

                }
            }
            AnimatedVisibility(
                visible = extendedFabVisibility,
                enter = slideInVertically(initialOffsetY = { it * 2 }),
                exit = slideOutVertically(targetOffsetY = { it * 2 }),
                modifier = modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 12.dp, end = 8.dp)
            ) {
                ExtendedFloatingActionButton(
                    text = {
                        Text(text = "Add item to cart")
                    },
                    icon = {
                        Image(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add item",
                            colorFilter = ColorFilter.tint(Color.White))
                    },
                    onClick = {
                        showDialog = true
                    }
                )
            }

            if (showDialog) {
                Dialog(onDismissRequest = { showDialog = false }) {
                    Card(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(all = 8.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        ExposedDropdownMenuBox(
                            expanded = isExpanded,
                            onExpandedChange = {
                                isExpanded = it
                            },
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp, end = 8.dp)) {

                            OutlinedTextField(
                                value = product,
                                onValueChange = {
                                    product = it
                                },
                                readOnly = true,
                                label = {
                                    Text(text = "Select item")
                                },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                                },
                                modifier = modifier
                                    .menuAnchor()
                                    .fillMaxWidth()
                            )

                            ExposedDropdownMenu(
                                expanded = isExpanded,
                                onDismissRequest = {
                                    isExpanded = false
                                }) {

                                inventoryList.forEach { productName ->
                                    DropdownMenuItem(
                                        text = {
                                            Text(text = productName.productName)
                                        },
                                        onClick = {
                                            product = productName.productName
                                            unitPrice = productName.unitPrice
                                            isExpanded = false
                                        })
                                }

                            }
                        }

                        OutlinedTextField(
                            value = amount,
                            onValueChange = {amount = it},
                            label = {
                                Text(text = "Enter amount")
                            },
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp, end = 8.dp),
                            keyboardOptions =  KeyboardOptions(
                                imeAction = ImeAction.Next,
                                keyboardType = KeyboardType.Number
                            )
                        )

                        Row(modifier = modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly) {

                            Button(onClick = {
                                showDialog = false
                            },
                                modifier = modifier
                                    .padding()) {
                                Text(text = "Close")
                            }

                            Button(
                                onClick = {
                                    mainViewModel.insertItemInCart(
                                        Cart(
                                            id = 0,
                                            productName = product,
                                            unitPrice = unitPrice,
                                            amount = amount.toDouble(),
                                            totalCost = unitPrice * amount.toDouble()
                                        )
                                    )
                                    Toast.makeText(context, "Item added to cart successfully", Toast.LENGTH_SHORT).show()
                                },
                                modifier = modifier
                                    .padding()) {
                                Text(text = "Add")

                            }


                        }

                    }
                }
            }

        }
    }

}

@Preview(showSystemUi = true)
@Composable
fun PreviewCalculatorScreen(){
    CalculatorScreen()
}

