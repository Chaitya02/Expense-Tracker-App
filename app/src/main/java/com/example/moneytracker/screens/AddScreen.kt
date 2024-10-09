package com.example.moneytracker.screens

import android.app.DatePickerDialog
import android.os.Build
import android.widget.DatePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.moneytracker.components.TableRow
import com.example.moneytracker.components.UnstyledTextField
import com.example.moneytracker.models.Recurrence
import com.example.moneytracker.ui.theme.Beige20
import com.example.moneytracker.ui.theme.Green40
import com.example.moneytracker.ui.theme.Red40
import com.example.moneytracker.viewmodels.AddViewModel
import java.time.LocalDate
import java.util.Calendar

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(navController: NavController, addViewModel: AddViewModel = viewModel()) {

    val state by addViewModel.uiState.collectAsState()
    addViewModel.setCategories()

    if (state.categories?.isEmpty() == true) {
        state.category?.name = ""
    }

    val recurrences = listOf(
        Recurrence.None, Recurrence.Daily, Recurrence.Weekly, Recurrence.Monthly, Recurrence.Yearly
    )

    // val categories = listOf("Groceries", "Bills", "Hobbies", "Take out")

    val mContext = LocalContext.current

    val mYear: Int
    val mMonth: Int
    val mDay: Int

    val mCalendar = Calendar.getInstance()

    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH) + 1
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    val mDatePickerDialog = remember {
        DatePickerDialog(
            mContext,
            { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
                val mDate = LocalDate.of(selectedYear, selectedMonth + 1, selectedDay)
//            val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
//            val localDate = LocalDate.parse(mDate, formatter)
                addViewModel.setDate(mDate)
            }, state.date.year, state.date.monthValue, state.date.dayOfMonth
        )
    }

    mDatePickerDialog.datePicker.maxDate = mCalendar.timeInMillis

    Scaffold(
        modifier = Modifier,
        topBar = {
            MediumTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.background,
                ),
                title = {
                    Text(
                        "Add",
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(), // Ensure the LazyColumn fills the available space
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {

                Column(
                    modifier = Modifier.padding(innerPadding),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.primary)
                            .fillMaxWidth()
                    ) {
                        TableRow(label = "Amount", detailContent = {
                            UnstyledTextField(
                                text = state.amount,
                                onTextChange = addViewModel::setAmount,
                                placeholder = "0.0",
                                modifier = Modifier.fillMaxWidth(),
                                keyboardType = KeyboardType.Number
                            )
                        })
                        HorizontalDivider(
                            modifier = Modifier.padding(start = 16.dp),
                            thickness = 1.dp,
                            color = MaterialTheme.colorScheme.background
                        )
                        TableRow(label = "Recurrence", detailContent = {

                            var recurrenceMenuOpened by remember {
                                mutableStateOf(false)
                            }

                            TextButton(
                                modifier = Modifier.padding(end = 5.dp),
                                shape = RoundedCornerShape(10.dp),
                                onClick = { recurrenceMenuOpened = true }
                            ) {
                                Text(
                                    color = MaterialTheme.colorScheme.background,
                                    text = state.recurrence.name ?: Recurrence.None.name
                                )
                                DropdownMenu(
                                    expanded = recurrenceMenuOpened,
                                    onDismissRequest = { recurrenceMenuOpened = false }) {
                                    recurrences.forEach { recurrence ->
                                        DropdownMenuItem(text = { Text(text = recurrence.name) },
                                            onClick = {
                                                addViewModel.setRecurrence(recurrence)
                                                recurrenceMenuOpened = false
                                            })
                                    }
                                }
                            }
                        })
                        HorizontalDivider(
                            modifier = Modifier.padding(start = 16.dp),
                            thickness = 1.dp,
                            color = MaterialTheme.colorScheme.background
                        )

//                var datePickerShowing by remember {
//                    mutableStateOf(false)
//                }
                        TableRow(label = "Date", detailContent = {
                            TextButton(
                                shape = RoundedCornerShape(10.dp),
                                onClick = {
                                    mDatePickerDialog.show()
                                }
                            ) {
                                Text(
                                    color = MaterialTheme.colorScheme.background,
                                    text = state.date.toString()
                                )
                            }
//                    if (datePickerShowing) {
//                        DatePickerDialog(
//                            onDismissRequest = { datePickerShowing = false },
//                            onDateChange = {
//                                addViewModel.setDate(it)
//                                datePickerShowing = false },
//                            initialDate = state.date,
//                            title = { Text(text = "Select date", style = MaterialTheme.typography.titleLarge) }
//                        )
//                    }
                        })
                        HorizontalDivider(
                            modifier = Modifier.padding(start = 16.dp),
                            thickness = 1.dp,
                            color = MaterialTheme.colorScheme.background
                        )
                        TableRow(label = "Note", detailContent = {
                            UnstyledTextField(
                                text = state.note,
                                onTextChange = addViewModel::setNote,
                                placeholder = "Leave some notes",
                                modifier = Modifier.fillMaxWidth(),
                                keyboardType = KeyboardType.Text
                            )
                        })
                        HorizontalDivider(
                            modifier = Modifier.padding(start = 16.dp),
                            thickness = 1.dp,
                            color = MaterialTheme.colorScheme.background
                        )
                        TableRow(label = "Category", detailContent = {
                            var categoriesMenuOpened by remember {
                                mutableStateOf(false)
                            }

                            TextButton(
                                modifier = Modifier.padding(end = 5.dp),
                                shape = RoundedCornerShape(10.dp),
                                onClick = { categoriesMenuOpened = true }
                            ) {
                                Text(
                                    color = state.category?.color
                                        ?: MaterialTheme.colorScheme.background,
                                    text = state.category?.name ?: "Select a category first"
                                )
                                DropdownMenu(expanded = categoriesMenuOpened,
                                    onDismissRequest = { categoriesMenuOpened = false }) {
                                    state.categories?.forEach { category ->
                                        DropdownMenuItem(text = {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Surface(
                                                    modifier = Modifier.size(10.dp),
                                                    shape = CircleShape,
                                                    color = category.color
                                                ) {}
                                                Text(
                                                    category.name,
                                                    modifier = Modifier.padding(start = 8.dp)
                                                )
                                            }
                                        }, onClick = {
                                            addViewModel.setCategory(category)
                                            categoriesMenuOpened = false
                                        })
                                    }
                                }
                            }
                        })
                    }

                    Button(
                        modifier = Modifier.padding(18.dp),
                        shape = RoundedCornerShape(10.dp),
                        onClick = addViewModel::submitExpense,
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Beige20,
                            containerColor = Green40,
                            disabledContainerColor = Red40,
                            disabledContentColor = Beige20
                        ),
                        enabled = (state.category != null) and (state.amount != "")
                    ) {
                        Text(
                            text = "Submit expense",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }}
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun AddScreenPreview() {
    val navController = rememberNavController()
    AddScreen(navController = navController)
}
