package com.example.uts.model

import com.google.gson.annotations.SerializedName

data class Movies(

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("data")
	val data: List<DataItem?>? = null
)

data class DataItem(

	@field:SerializedName("phase")
	val phase: Int? = null,

	@field:SerializedName("overview")
	val overview: String? = null,

	@field:SerializedName("cover_url")
	val coverUrl: String? = null,

	@field:SerializedName("imdb_id")
	val imdbId: String? = null,

	@field:SerializedName("chronology")
	val chronology: Int? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("box_office")
	val boxOffice: String? = null,

	@field:SerializedName("trailer_url")
	val trailerUrl: String? = null,

	@field:SerializedName("duration")
	val duration: Int? = null,

	@field:SerializedName("directed_by")
	val directedBy: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("release_date")
	val releaseDate: String? = null,

	@field:SerializedName("post_credit_scenes")
	val postCreditScenes: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("saga")
	val saga: String? = null
)
