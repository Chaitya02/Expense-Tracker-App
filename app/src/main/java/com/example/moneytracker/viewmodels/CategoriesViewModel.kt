package com.example.moneytracker.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneytracker.db
import com.example.moneytracker.models.Category
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmResults
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class CategoriesState (
    val newCategoryColor: Color = Color.White,
    val newCategoryName: String = "",
    val colorPickerShowing: Boolean = false,
//    val categories: MutableList<Category> = mutableListOf(
//        Category("Groceries", Color.Green),
//        Category("Bills", Color.Red),
//        Category("Subscriptions", Color.Yellow),
//        Category("Take out", Color.Blue),
//        Category("Hobbies", Color.Cyan)
//    )
    val categories: List<Category> = listOf()
)

@RequiresApi(Build.VERSION_CODES.O)
class CategoriesViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(CategoriesState())
    val uiState: StateFlow<CategoriesState> = _uiState.asStateFlow()

    init {
        _uiState.update { currentState ->
            currentState.copy(
                categories = db.query<Category>().find()
            )
        }

        viewModelScope.launch(Dispatchers.IO) {
            db.query<Category>().asFlow().collect { changes ->
                _uiState.update { currentState ->
                    currentState.copy(
                        categories = changes.list
                    )
                }
            }
        }
    }

    fun setNewCategoryColor(color: Color) {
        _uiState.update { currentState ->
            currentState.copy(
                newCategoryColor = color
            )
        }
    }

    fun setNewCategoryName(name: String) {
        _uiState.update { currentState ->
            currentState.copy(
                newCategoryName = name
            )
        }
    }

    fun showColorPicker() {
        _uiState.update { currentState ->
            currentState.copy(
                colorPickerShowing = true
            )
        }
    }

    fun hideColorPicker() {
        _uiState.update { currentState ->
            currentState.copy(
                colorPickerShowing = false
            )
        }
    }

    fun createNewCategory() {
        viewModelScope.launch(Dispatchers.IO) {
            db.write {
                this.copyToRealm(Category(
                    _uiState.value.newCategoryName,
                    _uiState.value.newCategoryColor
                ))
            }
            _uiState.update { currentState ->
                currentState.copy(
                    newCategoryColor = Color.White,
                    newCategoryName = ""
                )
            }
        }
    }

    fun deleteCategory(category: Category) {
        viewModelScope.launch(Dispatchers.IO) {
            db.write {
                val deletingCategory = this.query<Category>("_id == $0", category._id).find().first()
                delete(deletingCategory)
            }
        }
    }
}