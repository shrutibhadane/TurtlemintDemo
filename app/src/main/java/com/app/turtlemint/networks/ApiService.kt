package com.app.turtlemint.networks

import com.app.turtlemint.model.CommentsResponseModel
import com.app.turtlemint.model.IssueResponseModel
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Url

interface ApiService {

    @Headers("Accept:application/json")
    @GET(APIConstants.GET_PEOPLE)
    suspend fun getIssues(): IssueResponseModel

    @Headers("Accept:application/json")
    @GET
    suspend fun getComments(@Url url: String): CommentsResponseModel
}
