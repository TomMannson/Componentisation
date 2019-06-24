package com.tommannson.apps.componentisation.model.ws.github

import com.google.gson.annotations.SerializedName
import com.jmoraes.componentizationsample.model.ws.github.GithubSearchItem

data class GithubSearchResult(
        @SerializedName("total_count") val totalCount: Int,
        @SerializedName("incomplete_results") val incompleteResults: Boolean,
        val items: Array<GithubSearchItem>
)
