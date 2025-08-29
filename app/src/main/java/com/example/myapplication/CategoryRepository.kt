package com.example.myapplication

import android.content.Context
import com.example.myapplication.models.CategoryResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepository @Inject constructor(
    private val context: Context
) {
    fun getCategories(): CategoryResponse {
        return JsonLoader.loadCategories(context)
    }
}