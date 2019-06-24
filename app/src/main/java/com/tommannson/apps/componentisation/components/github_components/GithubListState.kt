package com.tommannson.apps.componentisation.components.github_components

import com.tommannson.apps.componentisation.model.ws.github.GithubSearchResult

data class GithubListState(val github: GithubSearchResult?,
                           var loading: Boolean)
