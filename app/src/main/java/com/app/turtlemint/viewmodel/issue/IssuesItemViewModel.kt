package com.app.turtlemint.viewmodel.issue

import androidx.lifecycle.ViewModel
import com.app.turtlemint.model.IssueResponseModelItem

class IssuesItemViewModel(
    val itemData: IssueResponseModelItem,
    private val viewModel: IssuesViewModel,
) : ViewModel() {

    fun itemOnClicked() {

        viewModel.itemOnClicked(itemData.title,
            itemData.body!!,
            itemData.commentsUrl,
            itemData.number)
    }

}
