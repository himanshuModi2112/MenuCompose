package com.example.myapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.models.Category
import com.example.myapplication.models.SubCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val repository: CategoryRepository
) : ViewModel() {

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories = _categories.asStateFlow()

    private val _breadcrumb = MutableStateFlow(listOf("Home"))
    val breadcrumb = _breadcrumb.asStateFlow()

    private val _currentSubcategories = MutableStateFlow<List<Any>>(emptyList())
    val currentSubcategories = _currentSubcategories.asStateFlow()

    private var currentLevel: List<Category> = emptyList()

    init {
        viewModelScope.launch {
            val response = repository.getCategories()
            _categories.value = response.categories
            _currentSubcategories.value = response.categories
        }
    }

    fun navigateToCategory(name: String, subList: List<Any>) {
        _breadcrumb.value = _breadcrumb.value + name
        _currentSubcategories.value = subList
    }

    fun navigateBackTo(level: Int) {
        _breadcrumb.value = _breadcrumb.value.take(level + 1)
        _currentSubcategories.value = _categories.value // Simplified reset logic
    }

    fun navigateBack() {
        if (_breadcrumb.value.size > 1) {
            _breadcrumb.value = _breadcrumb.value.dropLast(1)
            // When going back, reset current list to previous level
            val path = _breadcrumb.value
            _currentSubcategories.value = findListForPath(path)
        }
    }

    // Helper to find correct list for breadcrumb path
    private fun findListForPath(path: List<String>): List<Any> {
        var list: List<Any> = _categories.value
        for (i in 1 until path.size) {
            val name = path[i]
            val node = when (val item = list.find {
                (it as? Category)?.name == name ||
                        (it as? SubCategory)?.name == name
            }) {
                is Category -> item.subcategories ?: item.items ?: emptyList()
                is SubCategory -> item.subcategories ?: item.items ?: emptyList()
                else -> emptyList()
            }
            list = node
        }
        return list
    }

}
