package com.example.moneytracker.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.moneytracker.components.PickerTrigger
import com.example.moneytracker.components.expensesList.ExpensesList
import com.example.moneytracker.models.Recurrence
import com.example.moneytracker.ui.theme.Beige20
import com.example.moneytracker.viewmodels.ExpensesViewModel
import java.text.DecimalFormat


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpensesScreen(navController: NavController, expensesViewModel: ExpensesViewModel = viewModel()) {

    val state by expensesViewModel.uiState.collectAsState()
    expensesViewModel.setExpenses()

    var recurrenceMenuOpened by remember {
        mutableStateOf(false)
    }

    val recurrences = listOf(
        Recurrence.Daily, Recurrence.Weekly, Recurrence.Monthly, Recurrence.Yearly
    )

    Scaffold(
        modifier = Modifier,
        topBar = {
            MediumTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Beige20,
                ),
                title = {
                    Text(
                        "Expenses",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = TextStyle(
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Total for:",
                    style = MaterialTheme.typography.bodyMedium
                )
                PickerTrigger(
                    modifier = Modifier.padding(start = 16.dp),
                    label = state.recurrence.target,
                    onClick = {
                        recurrenceMenuOpened = !recurrenceMenuOpened
                    }
                )
                DropdownMenu(expanded = recurrenceMenuOpened, onDismissRequest = { recurrenceMenuOpened = false }) {
                    recurrences.forEach{ recurrence ->
                        DropdownMenuItem(text = { Text(text = recurrence.target) },
                            onClick = {
                                expensesViewModel.setRecurrence(recurrence)
                                recurrenceMenuOpened = false
                            })
                    }
                }
            }
            Row(
                modifier = Modifier.padding(vertical = 32.dp)
            ) {
                Text(text = "₹", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.secondary, modifier = Modifier.padding(end = 4.dp, top = 3.dp))
                Text(text = DecimalFormat("0.#").format(state.sumTotal), style = MaterialTheme.typography.titleLarge)
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

}
