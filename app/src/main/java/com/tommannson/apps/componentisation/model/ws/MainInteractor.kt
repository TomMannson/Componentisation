package com.jmoraes.componentizationsample.model.ws

import com.tommannson.apps.componentisation.model.ws.github.GithubSearchResult

import io.reactivex.Observable
import retrofit2.Response

/**
 * Created by katarzyna.jedrzejews on 2015-11-10.
 */
interface MainInteractor {

    fun fromWebservice(): Observable<Response<GithubSearchResult>>

}
