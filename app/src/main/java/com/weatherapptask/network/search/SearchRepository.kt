package com.weatherapptask.network.search


import com.weatherapptask.network.search.model.response.ResponseSearch
import com.weatherapptask.utilits.Response

interface SearchRepository {
    suspend fun search(
        query: String?
    ): Response<ResponseSearch>


    private class Implementation(
        private val searchApi: SearchApi
    ) : SearchRepository {

        override suspend fun search(query: String?): Response<ResponseSearch> {
            return Response.handle(
                getResponse = {
                    searchApi.search(query = query)
                },
                onFailure = {
                    Response.error(it.throwable, it.message, it.responseCode)
                })
        }
    }

    companion object {
        @JvmStatic
        fun create(
            searchApi: SearchApi
        ): SearchRepository = Implementation(searchApi)
    }
}