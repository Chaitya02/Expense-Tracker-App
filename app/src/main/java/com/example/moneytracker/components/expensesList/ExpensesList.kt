package com.example.moneytracker.components.expensesList

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.moneytracker.components.ExpensesDayGroup
import com.example.moneytracker.models.Expense
import com.example.moneytracker.models.groupedByDay

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ExpensesList(expenses: List<Expense>, modifier: Modifier = Modifier) {
    val groupedExpenses = expenses.groupedByDay()

    Column(modifier = modifier) {
        if (groupedExpenses.isEmpty()) {
            Text(text = "No data for selected date range.", modifier = Modifier.padding(top = 32.dp))
        } else {
            groupedExpenses.keys.forEach { date ->
                groupedExpenses[date]?.let {
                    ExpensesDayGroup(
                        date = date,
                        dayExpenses = it,
                        modifier = Modifier.padding(top = 24.dp)
                    )
                }
            }
        }
    }
}
