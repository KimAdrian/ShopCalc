package com.kimadrian.shopcalc.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kimadrian.shopcalc.R
import com.kimadrian.shopcalc.data.local.entity.Product
import com.kimadrian.shopcalc.ui.components.ImageAndLabel
import com.kimadrian.shopcalc.ui.components.InventoryItemCard
import com.kimadrian.shopcalc.ui.viewmodel.MainViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryScreen(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel = koinViewModel()
) {

    var showDialog by remember {
        mutableStateOf(false)
    }

    var itemName by remember {
        mutableStateOf("")
    }
    var unitPrice by remember {
        mutableStateOf("")
    }

    var isExpanded by remember {
        mutableStateOf(false)
    }

    
    val inventoryList by mainViewModel.productsStateFlow.collectAsState(initial = emptyList())


    Scaffold { contentPadding ->
        val context = LocalContext.current
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues = contentPadding)
        ){
            if (inventoryList.isEmpty()){
                ImageAndLabel(
                    modifier = modifier.align(Alignment.Center),
                    imageVector = ImageVector.vectorResource(R.drawable.undraw_empty),
                    label = "Please add some products/items")
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2), 
                    content = {
                        items(inventoryList) { product ->
                            InventoryItemCard(
                                product = product
                            )
                        }
                    })
            }

            ExtendedFloatingActionButton(
                text = {
                    Text(text = "Add new item")
                },
                icon = {
                    Image(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add item",
                        colorFilter = ColorFilter.tint(Color.White))
                },
                onClick = {
                          showDialog = true
                },
                modifier = modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 12.dp, end = 8.dp))

            if (showDialog) {
                Dialog(onDismissRequest = { showDialog = false }) {
                    Card(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(all = 8.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        OutlinedTextField(
                            value = itemName,
                            onValueChange = {itemName = it},
                            label = {
                                Text(text = "Product/Item name")
                            },
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp, end = 8.dp),
                            keyboardOptions =  KeyboardOptions(
                                imeAction = ImeAction.Next,
                            )
                        )

                        OutlinedTextField(
                            value = unitPrice,
                            onValueChange = {unitPrice = it},
                            label = {
                                Text(text = "Price per unit")
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

                            Button(
                                onClick = {
                                    showDialog = false
                                },
                                modifier = modifier
                                    .padding()) {
                                Text(text = "Close")
                            }

                            Button(
                                onClick = {
                                    mainViewModel.insertProduct(
                                        product = Product(
                                            id = 0,
                                            productName = itemName,
                                            unitPrice = unitPrice.toDouble()
                                        )
                                    )
                                    Toast.makeText(context, "Product added successfully", Toast.LENGTH_SHORT).show()
                                },
                                modifier = modifier
                                    .padding()) {
                                Text(text = "Save")
                            }

                        }
                    }
                }
            }

        }
    }
}