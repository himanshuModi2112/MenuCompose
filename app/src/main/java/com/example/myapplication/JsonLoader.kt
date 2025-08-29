package com.example.myapplication

import android.content.Context
import com.google.gson.Gson
import com.example.myapplication.models.CategoryResponse

object JsonLoader {
    fun loadCategories(context: Context): CategoryResponse {
        val json = context.assets.open("sample.json")
            .bufferedReader().use { it.readText() }
        return Gson().fromJson(json, CategoryResponse::class.java)
    }
}
