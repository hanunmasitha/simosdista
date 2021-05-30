package com.example.simodista.model

data class User(
    val name: String? = null,
    val email: String? = null,
    val phone: String? = null,
    @field:JvmField
    val isAdmin : Boolean = false
)