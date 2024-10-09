package com.example.moneytracker

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.moneytracker.data.BottomNavigationItem
import com.example.moneytracker.screens.AddScreen
import com.example.moneytracker.screens.CategoriesScreen
import com.example.moneytracker.screens.ExpensesScreen
import com.example.moneytracker.screens.ReportsScreen
import com.example.moneytracker.screens.SettingsScreen
import com.example.moneytracker.ui.theme.Beige20

sealed class Screens(val route : String) {
    data object Expenses : Screens("expenses_route")
    data object Reports : Screens("reports_route")
    data object Add : Screens("add_route")
    data object Settings : Screens("settings_route")
    data object Categories : Screens("categories_route")
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BottomNavigationBar() {

    var showBottomBar by rememberSaveable { mutableStateOf(true) }

//initializing the default selected item
    var navigationSelectedItem by remember {
        mutableIntStateOf(0)
    }
    /**
     * by using the rememberNavController()
     * we can get the instance of the navController
     */
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    showBottomBar = when(currentDestination?.route) {
        "categories_route" -> false
        else -> true
    }

//scaffold to hold our bottom navigation Bar
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    //getting the list of bottom navigation items for our data class
                    BottomNavigationItem().bottomNavigationItems().forEachIndexed { index, navigationItem ->
                        //iterating all items with their respective indexes
                        NavigationBarItem(
                            selected = currentDestination?.route == navigationItem.route,
                            label = {
                                Text(
                                    text = navigationItem.label,
                                    color = Beige20
                                )
                            },
                            icon = {
                                Icon(
                                    navigationItem.icon,
                                    contentDescription = navigationItem.label,
                                    tint = if (currentDestination?.route == navigationItem.route) MaterialTheme.colorScheme.primary else Beige20
                                )
                            },
                            onClick = {
                                navigationSelectedItem = index

                                navController.navigate(navigationItem.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        //We need to setup our NavHost in here
        NavHost(
            navController = navController,
            startDestination = Screens.Expenses.route,
            modifier = Modifier.padding(paddingValues = paddingValues)) {
            composable(Screens.Expenses.route) {
                //call our composable screens here
                ExpensesScreen(navController = navController)
            }
            composable(Screens.Reports.route) {
                //call our composable screens here
                ReportsScreen(navController = navController)
            }
            composable(Screens.Add.route) {
                //call our composable screens here
                AddScreen(navController = navController)
            }
            composable(Screens.Settings.route) {
                //call our composable screens here
                SettingsScreen(navController = navController)
            }
            composable(Screens.Categories.route) {
                //call our composable screens here
                CategoriesScreen(navController = navController)
            }
        }
    }
}
