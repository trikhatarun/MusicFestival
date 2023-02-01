package com.boundinteractive.eacodingtest.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.boundinteractive.eacodingtest.ui.data.Band
import com.boundinteractive.eacodingtest.ui.data.RecordLabel

@Composable
fun SuccessScreen(data: List<RecordLabel>) {
    if (data.isEmpty()) {
        Text(
            text = "No data received",
            modifier = Modifier.padding(16.dp),
            fontWeight = FontWeight.Bold
        )
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(data, itemContent = { item ->
                if (item.name.isBlank()) {
                    RecordLabelItem(name = "Unsigned Artists", bands = item.bands)
                } else {
                    RecordLabelItem(name = item.name, bands = item.bands)
                }
            })
        }
    }
}

@Composable
fun RecordLabelItem(name: String, bands: List<Band>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = name, fontWeight = FontWeight.Bold)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp)
        ) {
            bands.forEach {
                Column {
                    Text(text = it.name)
                    it.festivals.forEach {
                        Text(text = it, modifier = Modifier.padding(start = 16.dp))
                    }
                }
            }
        }
    }
}