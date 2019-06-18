package com.jmoraes.componentizationsample.model.ws.github

import com.google.gson.annotations.SerializedName

data class GithubSearchItem(
        var name: String,
        @SerializedName("full_name") var fullName: String
)
