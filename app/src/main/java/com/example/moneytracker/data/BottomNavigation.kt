package com.example.moneytracker.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.moneytracker.Screens

data class BottomNavigationItem(
    val label : String = "",
    val icon : ImageVector = Icons.Filled.Home,
    val route : String = ""
) {

    fun bottomNavigationItems() : List<BottomNavigationItem> {
        return listOf(
            BottomNavigationItem(
                label = "Expenses",
                icon = Icons.Filled.AccountBalanceWallet,
                route = Screens.Expenses.route
            ),
            BottomNavigationItem(
                label = "Reports",
                icon = Icons.Filled.Description,
                route = Screens.Reports.route
            ),
            BottomNavigationItem(
                label = "Add",
                icon = Icons.Filled.Add,
                route = Screens.Add.route
            ),
            BottomNavigationItem(
                label = "Settings",
                icon = Icons.Filled.Settings,
                route = Screens.Settings.route
            ),
        )
    }
}