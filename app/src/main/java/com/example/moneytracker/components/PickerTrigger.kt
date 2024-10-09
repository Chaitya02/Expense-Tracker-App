package com.example.moneytracker.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.UnfoldMore
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PickerTrigger(
    modifier: Modifier = Modifier,
    label: String,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.primary,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 3.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = label,
                color = MaterialTheme.colorScheme.background
            )
            Icon(
                imageVector = Icons.Rounded.UnfoldMore,
                contentDescription = "",
                modifier = Modifier.padding(start = 10.dp),
                tint = MaterialTheme.colorScheme.background
            )
        }
    }
}
