package com.assignment.model

import com.google.gson.annotations.SerializedName

data class FactsResponseModel(
    @SerializedName("title")
    val title: String,

    @SerializedName("rows")
    val rows: List<Rows>
)
{
    data class Rows(
        @SerializedName("title")
        val title: String?,
        @SerializedName("description")
        val description: String?,
        @SerializedName("imageHref")
        val imageHref: String?
    )
}
