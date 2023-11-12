package com.example.uts.api.model

data class Hero(
	val result: List<ResultItem?>? = null
)

data class ResultItem(
	val image: String? = null,
	val id: Int? = null,
	val title: String? = null
)

