package com.app.turtlemint.repository

import android.content.Context
import com.app.turtlemint.database.AppDatabase
import com.app.turtlemint.model.CommentsResponseModel
import com.app.turtlemint.model.IssueResponseModel
import com.app.turtlemint.networks.RetrofitBuilder
import com.app.turtlemint.utils.NetworkUtils

object IssuesRepository {

    suspend fun getIssues(context: Context): IssueResponseModel {
        return if (NetworkUtils.isInternetAvailable(context)) {
            RetrofitBuilder.apiService.getIssues()
        } else {
            val dbResponse = IssueResponseModel()
            AppDatabase.getAppDataBase(context)?.issueDao()?.loadAllIssues()?.let {
                dbResponse.addAll(it)
            } ?: run {
                dbResponse.addAll(listOf())
            }
            dbResponse
        }
    }

    suspend fun getComments(url: String, number: String, context: Context): CommentsResponseModel {
        return if (NetworkUtils.isInternetAvailable(context)) {
            RetrofitBuilder.apiService.getComments(url)
        } else {
            val dbResponse = CommentsResponseModel()
            AppDatabase.getAppDataBase(context)?.issueDao()?.loadAllComment(number)?.let {
                dbResponse.addAll(it)
            } ?: run {
                dbResponse.addAll(listOf())
            }
            dbResponse
        }
    }
}
