package com.example.simodista.core.domain.model

data class FeedbackForm(
    val id : Int? = null,
    val report: ReportForm? = null,
    val user: User? = null,
    val description: String? = null,
    val date: String? = null
)