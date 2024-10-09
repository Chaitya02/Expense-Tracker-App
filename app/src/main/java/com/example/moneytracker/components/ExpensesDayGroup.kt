package com.example.moneytracker.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.moneytracker.models.DayExpenses
import com.example.moneytracker.utils.formatDay
import java.text.DecimalFormat
import java.time.LocalDate


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ExpensesDayGroup(date: LocalDate, dayExpenses: DayExpenses, modifier: Modifier = Modifier) {
    Column {
        Text(text = date.formatDay(), style = MaterialTheme.typography.titleMedium)
        Divider(modifier = Modifier.padding(top = 10.dp, bottom = 4.dp))

        Column {
            dayExpenses.expenses.forEach { expense ->
                ExpenseRow(expense = expense, modifier = Modifier.padding(top = 12.dp))
            }
        }

        Divider(modifier = Modifier.padding(top = 16.dp, bottom = 4.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Total:", style = MaterialTheme.typography.bodyMedium)
            Text(text = "INR ${DecimalFormat("0.#").format(dayExpenses.total)}", style = MaterialTheme.typography.titleMedium)
        }
    }
}
