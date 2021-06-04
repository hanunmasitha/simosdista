package com.example.simodista.core.domain.model

data class ReportForm(
    val id : Int? = null,
    val image_uri : String? = null,
    val user: User? = null,
    val date: String? = null,
    val status: Boolean? = null,
    val lat: Double? = null,
    val long: Double? = null,
    val description: String? = null
)