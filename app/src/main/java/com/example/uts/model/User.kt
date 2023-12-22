package com.example.uts.model

data class User(
    val id: String? = null,
    val email: String? = null,
    val nama: String? = null,
    val role:Enum<Roles>
)


