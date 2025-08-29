package com.example.myapplication.models

data class Item(
    val id: Int,
    val name: String,
    val price: Int? = null,
    val items: List<Item>? = null
)

data class SubCategory(
    val id: Int,
    val name: String,
    val isCategory: Int? = null,
    val items: List<Item>? = null,
    val subcategories: List<SubCategory>? = null
)

data class Category(
    val id: Int,
    val name: String,
    val isSuperCategory: Int? = null,
    val subcategories: List<SubCategory>? = null,
    val items: List<Item>? = null
)

data class CategoryResponse(
    val categories: List<Category>
)