package com.kimadrian.shopcalc.ui.components

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.kimadrian.shopcalc.data.local.entity.Cart
import com.kimadrian.shopcalc.data.local.entity.Product
import com.kimadrian.shopcalc.ui.viewmodel.MainViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun CartItemCard(
    modifier: Modifier = Modifier,
    cart: Cart,
    mainViewModel: MainViewModel = koinViewModel()
) {
    val context = LocalContext.current
    var showDialog by remember {
        mutableStateOf(false)
    }
    var productName by remember {
        mutableStateOf("")
    }
    var itemCount by remember {
        mutableStateOf("")
    }
    var unitPrice by remember {
        mutableStateOf("")
    }
    var totalCost by remember {
        mutableStateOf("")
    }
    productName = cart.productName
    itemCount = cart.amount.toString()
    unitPrice = cart.unitPrice.toString()
    totalCost = cart.totalCost.toString()
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(6.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = productName,
                    fontSize = 16.sp
                )
                Text(
                    text = "$${unitPrice}",
                    fontSize = 16.sp
                )
            }

            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = itemCount,
                    fontSize = 16.sp
                )
                Text(
                    text = "$${totalCost}",
                    fontSize = 16.sp
                )
            }

            FloatingActionButton(
                onClick = {
                    showDialog = true
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit item")
            }

            FloatingActionButton(
                onClick = {
                    mainViewModel.deleteItemInCart(cart)
                    Toast.makeText(context, "Item deleted", Toast.LENGTH_SHORT).show()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete item")
            }
        }
    }

    if (showDialog) {
        Dialog(onDismissRequest = { showDialog = false }) {
            Card(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(all = 8.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                OutlinedTextField(
                    value = productName,
                    onValueChange = { productName = it },
                    label = {
                        Text(text = "Product/Item")
                    },
                    readOnly = true,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                    )
                )
                OutlinedTextField(
                    value = itemCount,
                    onValueChange = { itemCount = it },
                    label = {
                        Text(text = "Update amount")
                    },
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Number
                    )
                )

                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = {
                            showDialog = false
                        }
                    ) {
                        Text(text = "Cancel")
                    }
                    Button(
                        onClick = {
                            mainViewModel.updateItemInCart(
                                Cart(
                                    id = cart.id,
                                    productName = productName,
                                    unitPrice = cart.unitPrice,
                                    amount = itemCount.toDouble(),
                                    totalCost = cart.unitPrice * itemCount.toDouble()
                                )
                            )
                            Toast.makeText(context, "Item details updated", Toast.LENGTH_SHORT).show()
                        }
                    ) {
                        Text(text = "Update")
                    }

                }
            }
        }
    }

}

@Preview(showSystemUi = true)
@Composable
fun PreviewCardItem() {
    LazyColumn(content = {
        item {
            CartItemCard(
                cart = Cart(
                    id = 0,
                    productName = "Bread",
                    unitPrice = 50.0,
                    amount = 3.0,
                    totalCost = 150.0
                )
            )
        }
        item {
            CartItemCard(
                cart = Cart(
                    id = 0,
                    productName = "Bread",
                    unitPrice = 50.0,
                    amount = 3.0,
                    totalCost = 150.0
                )
            )
        }
        item {
            CartItemCard(
                cart = Cart(
                    id = 0,
                    productName = "Bread",
                    unitPrice = 50.0,
                    amount = 3.0,
                    totalCost = 150.0
                )
            )
        }
    })
}