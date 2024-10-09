package com.example.moneytracker.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.moneytracker.ui.theme.Beige20
import com.example.moneytracker.ui.theme.Red40

@Composable
fun TableRow(modifier: Modifier = Modifier, label: String? = null, onClick: ((String) -> Unit)? = null, hasArrow: Boolean = false, isDestructive: Boolean = false, detailContent: (@Composable RowScope.() -> Unit)? = null, content: (@Composable RowScope.() -> Unit)? = null) {

    val textColor = if(isDestructive) Red40 else MaterialTheme.colorScheme.background

    val rowModifier = if (onClick != null) {
        modifier.fillMaxWidth().clickable {
            if (label != null) {
                onClick(label)
            }
        }
    } else {
        modifier.fillMaxWidth()
    }

    Row(
        modifier = rowModifier.padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (label != null) {
            Text(text = label, color = textColor, modifier = Modifier.padding(vertical = 10.dp, horizontal = 16.dp))
        }
        if (content != null) {
            content()
        }
        if (hasArrow) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "",
                modifier = Modifier.padding(vertical = 10.dp),
                tint = Beige20
            )
        }
        if (detailContent != null) {
            detailContent()
        }
    }
}