package com.example.moviedemo.models

import com.google.gson.annotations.SerializedName

data class MovieList(
    @SerializedName("Response")
    var response: String = "",
    @SerializedName("Search")
    var search: List<Search> = ArrayList(),
    @SerializedName("totalResults")
    var totalResults: String = ""
)