package com.kimadrian.shopcalc.ui.components

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.kimadrian.shopcalc.data.local.entity.Product
import com.kimadrian.shopcalc.ui.viewmodel.MainViewModel
import org.koin.androidx.compose.koinViewModel
import java.nio.file.Files.delete


@Composable
fun InventoryItemCard(
    modifier: Modifier = Modifier,
    product: Product,
    mainViewModel: MainViewModel = koinViewModel()
) {

    var showDialog by remember {
        mutableStateOf(false)
    }
    var id by remember {
        mutableIntStateOf(0)
    }
    var productName by remember {
        mutableStateOf("")
    }
    var unitPrice by remember {
        mutableStateOf("")
    }
    id = product.id
    productName = product.productName
    unitPrice = product.unitPrice.toString()

    Card(
        modifier = modifier
            .padding(4.dp)
            .size(90.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        val context = LocalContext.current
        Column(
            modifier = modifier
                .padding(start = 4.dp, end = 4.dp, top = 4.dp)
                .fillMaxSize()
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    modifier = modifier
                        .weight(1f),
                    text = productName,
                    fontSize = 15.sp
                )
                Text(
                    modifier = modifier.weight(1f),
                    text = "$ $unitPrice"
                )
            }
            Row(
                modifier = modifier.fillMaxHeight(),
                verticalAlignment = Alignment.Bottom
            ) {
                Button(
                    modifier = modifier
                        .weight(1f)
                        .padding(end = 2.dp),
                    onClick = {
                        showDialog = true
                    }
                ) {
                    Text(
                        text = "Edit",
                        color = Color.White)
                }
                Button(
                    modifier = modifier
                        .weight(1f)
                        .padding(start = 2.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
                    onClick = {
                        mainViewModel.deleteProduct(product)
                        Toast.makeText(context, "Product deleted successfully", Toast.LENGTH_SHORT).show()
                    }
                ) {
                    Text(
                        text = "Delete",
                        color = Color.White
                    )
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
                        onValueChange = {productName = it},
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

                        androidx.compose.material3.Button(
                            onClick = {
                            showDialog = false
                        }) {
                            Text(text = "Cancel")
                        }
                        androidx.compose.material3.Button(
                            onClick = {
                                mainViewModel.updateProductDetails(
                                    Product(
                                        id = id,
                                        productName = productName,
                                        unitPrice = unitPrice.toDouble()
                                    )
                                )
                                Toast.makeText(context, "Product details updated", Toast.LENGTH_SHORT).show()
                            }) {
                            Text(text = "Update")
                        }

                    }


                }
            }
        }
    }

}

@Preview
@Composable
fun EditDialog() {
    androidx.compose.material3.Button(
        onClick = {

        },
        modifier = Modifier
            .padding()) {
        Text(text = "Cancel")
    }
    
}

//@Preview(showSystemUi = true)
//@Composable
//fun PreviewInventoryCard() {
//    LazyVerticalGrid(
//        columns = GridCells.Fixed(count = 2),
//        content = {
//            item {
//                InventoryItemCard(
//                    product = Product(
//                        id = 0,
//                        productName = "Bread",
//                        unitPrice = 55.00
//                    )
//                )
//            }
//
//            item {
//                InventoryItemCard(
//                    product = Product(
//                        id = 0,
//                        productName = "Bread",
//                        unitPrice = 55.00
//                    )
//                )
//            }
//
//            item {
//                InventoryItemCard(
//                    product = Product(
//                        id = 0,
//                        productName = "Bread",
//                        unitPrice = 55.00
//                    )
//                )
//            }
//        }
//    )
//
//
//}