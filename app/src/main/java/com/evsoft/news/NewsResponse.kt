package com.evsoft.news

import com.google.gson.annotations.SerializedName

data class NewsResponse (
    @SerializedName("status") val status: String,
    @SerializedName("totalResults") val totalResults: Int,
    @SerializedName("results") val results: ArrayList<Article>
)
