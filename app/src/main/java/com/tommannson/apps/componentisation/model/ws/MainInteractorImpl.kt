package com.tommannson.apps.componentisation.model.ws


import com.jmoraes.componentizationsample.model.ws.MainInteractor
import com.tommannson.apps.componentisation.model.ws.github.GithubSearchResult
import com.jmoraes.componentizationsample.model.ws.github.dao.GithubApi
import com.tommannson.apps.componentisation.model.ws.dao.PostService
import com.tommannson.apps.componentisation.model.ws.dao.ServiceFactory

import io.reactivex.Observable
import retrofit2.Response

class MainInteractorImpl : MainInteractor {

    override fun fromWebservice(): Observable<Response<GithubSearchResult>> {

        val service = ServiceFactory.createRetrofitService(GithubApi::class.java, PostService.SERVICE_ENDPOINT)
        return service.search("{hello_angular}")
    }
}
