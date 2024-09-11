package com.example.appcontact.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun NewContactScreen(modifier: Modifier = Modifier, onCancelClick: ()->Unit = {}, onOkClick: (String, String)->Unit = {_,_->}) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(10.dp)) {
        var name by remember {
            mutableStateOf("")
        }
        var phoneNum by remember {
            mutableStateOf("")
        }
        OutlinedTextField(value = name, onValueChange = {name = it}, label = { Text(text = "Name")}, modifier = modifier.fillMaxWidth())
        OutlinedTextField(value = phoneNum, onValueChange = {phoneNum = it}, label = { Text(text = "Phone Number")}, modifier = modifier.fillMaxWidth(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))

        Row(modifier = modifier.wrapContentSize().align(Alignment.CenterHorizontally), horizontalArrangement = Arrangement.spacedBy(5.dp)) {
            Button(onClick = {onOkClick(name, phoneNum)}) {
                Text(text = "Ok")
            }
            Button(onClick = onCancelClick) {
                Text(text = "Cancel")
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun NewContactScreenPreview(modifier: Modifier = Modifier) {
    NewContactScreen()
}