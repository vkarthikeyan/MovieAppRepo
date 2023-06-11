package com.example.movieapp.data.model

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("error")
    val error: String = "",
    @SerializedName("message")
    val message: String = ""
)