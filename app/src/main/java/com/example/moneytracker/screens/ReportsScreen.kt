package com.example.moneytracker.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Today
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.moneytracker.components.ReportPage
import com.example.moneytracker.models.Recurrence
import com.example.moneytracker.ui.theme.Beige20
import com.example.moneytracker.viewmodels.ReportsViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ReportsScreen(navController: NavController, reportsViewModel: ReportsViewModel = viewModel()) {

    val state by reportsViewModel.uiState.collectAsState()

    val recurrences = listOf(
        Recurrence.Weekly, Recurrence.Monthly, Recurrence.Yearly
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
                        "Reports",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = TextStyle(
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Medium
                        )
                    )
                },
                actions = {
                    IconButton(onClick = reportsViewModel::openRecurrenceMenu) {
                        Icon(imageVector = Icons.Filled.Today, contentDescription = "", tint = Beige20)
                    }
                    DropdownMenu(expanded = state.recurrenceMenuOpened, onDismissRequest = reportsViewModel::closeRecurrenceMenu) {
                        recurrences.forEach{ recurrence ->
                            DropdownMenuItem(text = { Text(text = recurrence.name) },
                                onClick = {
                                    reportsViewModel.setRecurrence(recurrence)
                                    reportsViewModel.closeRecurrenceMenu()
                                })
                        }
                    }
                }
            )
        },
    ) { innerPadding ->

        val pagerState = rememberPagerState(pageCount = {
            when (state.recurrence) {
                Recurrence.Weekly -> 53
                Recurrence.Monthly -> 12
                Recurrence.Yearly -> 1
                else -> 53
            }
        })

        HorizontalPager(
            state = pagerState,
            reverseLayout = true
        ) {page ->
            ReportPage(page = page, innerPadding = innerPadding, recurrence = state.recurrence)
        }
    }
}