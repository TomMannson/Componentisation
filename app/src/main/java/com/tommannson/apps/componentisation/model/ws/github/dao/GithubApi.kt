package com.jmoraes.componentizationsample.model.ws.github.dao

import com.jmoraes.componentizationsample.model.ws.github.GithubSearchResult
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi{

    @GET("search/repositories")
    fun search(@Query("q", encoded = true) q: String): Observable<Response<GithubSearchResult>>
}
