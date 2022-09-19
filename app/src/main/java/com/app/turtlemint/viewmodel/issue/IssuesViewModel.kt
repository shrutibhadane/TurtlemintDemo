package com.app.turtlemint.viewmodel.issue

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.app.turtlemint.database.AppDatabase
import com.app.turtlemint.model.IssueResponseModel
import com.app.turtlemint.model.IssueResponseModelItem
import com.app.turtlemint.repository.IssuesRepository
import com.app.turtlemint.utils.Events
import com.app.turtlemint.utils.NetworkUtils
import com.app.turtlemint.utils.Resource
import com.app.turtlemint.utils.UIConstants
import com.app.turtlemint.view.issues.IssuesAdapter
import kotlinx.coroutines.Dispatchers

class IssuesViewModel : ViewModel() {
    private var issueList: List<IssueResponseModelItem> = ArrayList()
    var issuesAdapter = IssuesAdapter(issueList, this)

    private val _buttonClicked = MutableLiveData<Events>()
    val buttonClicked = _buttonClicked

    val isIssueAvailable = MutableLiveData(false)

    fun getIssues(context: Context) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val result = Resource.success(data = IssuesRepository.getIssues(context))
            if (NetworkUtils.isInternetAvailable(context)) {
                insertDb(context, result)
            }
            emit(result)
        } catch (throwable: Throwable) {
            emit(Resource.error(data = null, message = throwable.message.orEmpty()))
        }
    }

    private fun insertDb(context: Context, result: Resource<IssueResponseModel>) {
        val db = AppDatabase.getAppDataBase(context)
        if (db?.issueDao()?.loadAllIssues()?.isNotEmpty() == true) {
            db.issueDao().deleteIssueAll()
        }

        result.data?.forEach {
            db?.issueDao()?.insertIssue(it)
        }
    }

    fun itemOnClicked(title: String, body: String, commentsUrl: String, number: String) {
        _buttonClicked.value =
            Events(UIConstants.ISSUE_ITEM_CLICK_EVENT, title, body, commentsUrl, number)
    }

    fun setIssueListValue(issueList: IssueResponseModel) {
        isIssueAvailable.value = false
        this.issueList = issueList
        issuesAdapter.updateData(this.issueList)
    }

    fun setCommentAvailableStatus() {
        isIssueAvailable.value = true
    }
}
