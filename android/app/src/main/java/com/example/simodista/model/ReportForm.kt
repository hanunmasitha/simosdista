package com.example.simodista.model

import com.google.type.DateTime

data class ReportForm(
    val id : Int? = null,
    val image_uri : String? = null,
    val user: User? = null,
    val date: String? = null,
    val status: Boolean? = null
)