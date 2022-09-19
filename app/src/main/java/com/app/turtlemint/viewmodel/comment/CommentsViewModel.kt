package com.app.turtlemint.viewmodel.comment

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.app.turtlemint.database.AppDatabase
import com.app.turtlemint.model.CommentsResponseModel
import com.app.turtlemint.model.CommentsResponseModelItem
import com.app.turtlemint.repository.IssuesRepository
import com.app.turtlemint.utils.Events
import com.app.turtlemint.utils.NetworkUtils
import com.app.turtlemint.utils.Resource
import com.app.turtlemint.utils.UIConstants
import com.app.turtlemint.view.comments.CommentsAdapter
import kotlinx.coroutines.Dispatchers

class CommentsViewModel : ViewModel() {

    private var commentList: List<CommentsResponseModelItem> = ArrayList()
    var commentAdapter = CommentsAdapter(commentList)

    val isCommentAvailable = MutableLiveData(false)
    val issueTitle = MutableLiveData("")
    val issueDescription = MutableLiveData("")

    private val _buttonClicked = MutableLiveData<Events>()
    val buttonClicked = _buttonClicked

    fun getComments(url: String, number: String, context: Context) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val result = Resource.success(data = IssuesRepository.getComments(url, number, context))
            if (NetworkUtils.isInternetAvailable(context)) {
                insertDb(context, result, number)
            }
            emit(result)
        } catch (throwable: Throwable) {
            emit(Resource.error(data = null, message = throwable.message.orEmpty()))
        }
    }

    private fun insertDb(
        context: Context,
        result: Resource<CommentsResponseModel>,
        number: String,
    ) {
        val db = AppDatabase.getAppDataBase(context)
        if (db?.issueDao()?.loadAllComment(number)?.isNotEmpty() == true) {
            db.issueDao().deleteCommentAll(number)
        }
        result.data?.forEach {
            it.number = number
            db?.issueDao()?.insertComment(it)
        }
    }

    fun setCommentListValue(commentsList: CommentsResponseModel) {
        isCommentAvailable.value = false
        this.commentList = commentsList
        commentAdapter.updateData(this.commentList)
    }

    fun setCommentAvailableStatus() {
        isCommentAvailable.value = true
    }

    fun setIssueTitle(title: String) {
        issueTitle.value = title
    }

    fun setIssueDescription(description: String) {
        issueDescription.value = description
    }

    fun seeMoreClicked() {
        _buttonClicked.value = Events(UIConstants.DESCRIPTION_SEE_MORE_CLICK_EVENT)
    }

    fun seeLessClicked() {
        _buttonClicked.value = Events(UIConstants.DESCRIPTION_SEE_LESS_CLICK_EVENT)
    }
}
