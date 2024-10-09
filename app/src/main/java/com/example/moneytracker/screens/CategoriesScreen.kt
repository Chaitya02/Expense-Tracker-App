package com.example.moneytracker.screens

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.automirrored.rounded.Send
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.moneytracker.components.TableRow
import com.example.moneytracker.ui.theme.Beige20
import com.example.moneytracker.ui.theme.Green40
import com.example.moneytracker.ui.theme.Grey20
import com.example.moneytracker.ui.theme.Red40
import com.example.moneytracker.viewmodels.CategoriesViewModel
import com.github.skydoves.colorpicker.compose.AlphaTile
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun CategoriesScreen(navController: NavController, categoryViewModel: CategoriesViewModel = viewModel()) {

    val state by categoryViewModel.uiState.collectAsState()
    val colorPickerController = rememberColorPickerController()

    val context = LocalContext.current

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
                        "Categories",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = TextStyle(
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Medium
                        )
                    )
                },
                navigationIcon = {
                    Surface (
                        onClick = { navController.popBackStack() },
                        color = Color.Transparent,
                        shape = MaterialTheme.shapes.extraLarge
                    ) {
                        Row (
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBackIos,
                                contentDescription = "",
                                modifier = Modifier
                                    .padding(start = 6.dp)
                                    .size(14.dp),
                                tint = Grey20
                            )
                            Text(text = "Settings", color = Grey20, style = TextStyle(
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Medium
                            ))
                        }
                    }
                }
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column (
                modifier = Modifier.weight(1f)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .padding(16.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.primary)
                        .fillMaxWidth()
                ) {
                    itemsIndexed(state.categories, key = { _, category -> category.name }) {index, category ->

                        val dismissState = rememberDismissState(
                            confirmStateChange = {
                                if (it == DismissValue.DismissedToStart) {
                                    categoryViewModel.deleteCategory(category)
                                    true
                                } else {
                                    false
                                }
                            }
                        )

                        SwipeToDismiss(
                            state = dismissState,
                            modifier = Modifier,
                            background = {
                                val color = when (dismissState.dismissDirection) {
                                    DismissDirection.EndToStart -> Color(0xFFFF1744)
                                    else -> Color.Transparent
                                }
                                val direction = dismissState.dismissDirection

                                Row(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(color)
                                        .padding(12.dp, 8.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Spacer(modifier = Modifier)
                                    if (direction == DismissDirection.EndToStart) Icon(
                                        Icons.Filled.Delete,
                                        contentDescription = "Delete",
                                        tint = Beige20
                                    )
                                }
                            },
                            dismissContent = {
                                TableRow(modifier = Modifier
                                    .clip(MaterialTheme.shapes.small)
                                    .padding(vertical = 3.dp)) {
                                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(horizontal = 16.dp)) {
                                        Surface(
                                            color = category.color,
                                            shape = CircleShape,
                                            border = BorderStroke(
                                                width = 2.dp,
                                                color = Color.White
                                            ),
                                            modifier = Modifier.size(18.dp)
                                        ) {}
                                        Text(
                                            text = category.name,
                                            color = MaterialTheme.colorScheme.background,
                                            modifier = Modifier.padding(vertical = 10.dp, horizontal = 16.dp),
                                            style = TextStyle(
                                                fontSize = 15.sp,
                                                fontWeight = FontWeight.Normal
                                            )
                                        )
                                    }
                                }
                            },
                            directions = setOf(DismissDirection.EndToStart)
                        )

                        if (index < state.categories.size - 1 ) {
                            Divider(
                                modifier = Modifier.padding(start = 16.dp),
                                thickness = 1.dp,
                                color = MaterialTheme.colorScheme.background
                            )
                        }
                    }
                }
            }
            Row (modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp)
                .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                if (state.colorPickerShowing) {
                    Dialog(onDismissRequest = categoryViewModel::hideColorPicker) {
                        // on below line we are creating a column,
                        Surface(
                            color = Green40,
                            shape = RoundedCornerShape(10.dp)
                        ) {
                        Column(
                            // on below line we are adding a modifier to it,
                            modifier = Modifier
                                // on below line we are adding a padding.
                                .padding(all = 28.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            // on below line we are adding a row.
                            Text(text = "Choose a Color ?", style = TextStyle(
                                fontWeight = FontWeight.Medium,
                                fontSize = 24.sp
                            ),
                                color = MaterialTheme.colorScheme.background
                                )
                            Row(
                                // on below line we are adding a modifier
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 24.dp),
                                // on below line we are adding horizontal
                                // and vertical alignment.
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // on below line we are adding a alpha tile.
                                AlphaTile(
                                    // on below line we are
                                    // adding modifier to it
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        // on below line
                                        // we are adding a height.
                                        .height(45.dp)
                                        // on below line we are adding clip.
                                        .clip(RoundedCornerShape(6.dp)),
                                    // on below line we are adding controller.
                                    controller = colorPickerController
                                )
                            }
                            // on below line we are
                            // adding horizontal color picker.
                            HsvColorPicker(
                                // on below line we are
                                // adding a modifier to it
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(300.dp)
                                    .padding(10.dp),
                                // on below line we are
                                // adding a controller
                                controller = colorPickerController,
                                // on below line we are
                                // adding on color changed.
                                onColorChanged = {envelope ->
                                    categoryViewModel.setNewCategoryColor(envelope.color)
                                }
                            )

                            TextButton(onClick = categoryViewModel::hideColorPicker,
                                modifier = Modifier
                                    .fillMaxWidth(),
                                colors = ButtonColors(
                                    containerColor = Beige20,
                                    contentColor = MaterialTheme.colorScheme.primary,
                                    disabledContainerColor = Color.Transparent,
                                    disabledContentColor = Red40
                                )
                            ) {
                                Text(text = "Done", color = MaterialTheme.colorScheme.primary,
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                        }
                }
                    }
                }
                Surface (
                    onClick = categoryViewModel::showColorPicker,
                    shape = CircleShape,
                    color = state.newCategoryColor,
                    modifier = Modifier.size(width = 25.dp, height = 25.dp),
                    border = BorderStroke(
                        width = 2.dp,
                        color = Color.Black
                    )
                ) {}
                Surface (
                    color = Color.Transparent,
                    modifier = Modifier
                        .height(50.dp)
                        .weight(1f)
                        .padding(start = 16.dp),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Column (
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        OutlinedTextField(
                            value = state.newCategoryName,
                            onValueChange = categoryViewModel::setNewCategoryName,
                            modifier = Modifier.fillMaxSize(),
                            placeholder = { Text(text = "Category Name", style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal
                            )
                            ) },
                            maxLines = 1,
                            shape = MaterialTheme.shapes.medium,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Done
                            )
                        )
                    }
                }
                IconButton(onClick = {
                    if (state.newCategoryName == "") {
                        Toast.makeText(context, "Please enter a category name.", Toast.LENGTH_SHORT).show()
                    } else {
                        categoryViewModel.createNewCategory()
                    } },
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .shadow(shape = MaterialTheme.shapes.extraLarge, elevation = 1.dp)
                        .background(Grey20)) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.Send,
                        contentDescription = "Create Category",
                        tint = Green40,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }
        }
    }

}
