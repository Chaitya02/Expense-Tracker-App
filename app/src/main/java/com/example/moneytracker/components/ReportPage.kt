package com.example.moneytracker.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.moneytracker.components.charts.MonthlyChart
import com.example.moneytracker.components.charts.WeeklyChart
import com.example.moneytracker.components.charts.YearlyChart
import com.example.moneytracker.components.expensesList.ExpensesList
import com.example.moneytracker.models.Recurrence
import com.example.moneytracker.utils.formatDayForRange
import com.example.moneytracker.viewmodels.ReportPageViewModel
import com.example.moneytracker.viewmodels.viewModelFactory
import java.text.DecimalFormat
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReportPage(
    page: Int,
    innerPadding: PaddingValues,
    recurrence: Recurrence,
    reportPageViewModel: ReportPageViewModel = viewModel(
        key = "$page-${recurrence.name}",
        factory = viewModelFactory {
            ReportPageViewModel(page, recurrence)
        }
    )
) {
    val state = reportPageViewModel.uiState.collectAsState().value

    Column(
        modifier = Modifier
            .padding(innerPadding)
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                Text(text = "${state.dateStart.formatDayForRange()} - ${state.dateEnd.formatDayForRange()}", style = MaterialTheme.typography.titleSmall)
                Row(
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    Text(text = "INR", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(end = 4.dp))
                    Text(text = DecimalFormat("0.#").format(state.totalInRange), style = MaterialTheme.typography.headlineMedium)
                }
            }
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(text = "Avg/day", style = MaterialTheme.typography.titleSmall)
                Row(
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    Text(text = "INR", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(end = 4.dp))
                    Text(text = DecimalFormat("0.#").format(state.avgPerDay), style = MaterialTheme.typography.headlineMedium)
                }
            }
        }

        Box(modifier = Modifier
            .height(180.dp)
            .padding(vertical = 16.dp)) {
            when (recurrence) {
                Recurrence.Weekly -> WeeklyChart(expenses = state.expenses)
                Recurrence.Monthly -> MonthlyChart(expenses = state.expenses, month = LocalDate.now())
                Recurrence.Yearly -> YearlyChart(expenses = state.expenses)
                else -> Unit
            }
        }

        ExpensesList(
            expenses = state.expenses,
            modifier = Modifier
                .weight(1f)
                .verticalScroll(
                    rememberScrollState()
                )
        )
    }
}
