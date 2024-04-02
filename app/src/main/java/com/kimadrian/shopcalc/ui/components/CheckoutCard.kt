package com.kimadrian.shopcalc.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kimadrian.shopcalc.data.local.entity.Cart

@Composable
fun CheckOutCard(
    modifier: Modifier = Modifier,
    cartList: List<Cart>,
    totalCost: Double
) {
    var totalAmount by remember {
        mutableStateOf("")
    }
    var receivedAmount by remember {
        mutableStateOf("")
    }
    var balance by remember {
        mutableStateOf("")
    }
    var total by remember {
        mutableDoubleStateOf(0.0)
    }
    var received by remember {
        mutableDoubleStateOf(0.0)
    }
    var calculatedBalance by remember {
        mutableDoubleStateOf(0.0)
    }
    totalAmount = total.toString()

    if (receivedAmount.isNotEmpty()) {
        received = receivedAmount.toDouble()
    }
    calculatedBalance = received - totalCost
    if (calculatedBalance < 0  ) {
        balance = "0"
    } else {
        balance = calculatedBalance.toString()
    }

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
            Text(
                text = "Total:"
            )

            Text(
                text = totalCost.toString()
            )
        }

        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(6.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Received amount"
            )

            TextField(
                value = receivedAmount,
                onValueChange = {
                    receivedAmount = it
                },
                textStyle = TextStyle.Default.copy(fontSize = 18.sp),
                placeholder = {
                    Text(
                        text = "Enter amount"
                    )
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Number
                ),
                modifier = modifier.padding(start = 8.dp)
            )
        }

        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(6.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Balance"
            )
            Text(
                text = balance
            )
        }

    }

}

//@Preview(showSystemUi = true)
//@Composable
//fun PreviewCheckOutCard() {
//    CheckOutCard()
//}