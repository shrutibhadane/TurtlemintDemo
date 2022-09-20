package com.app.turtlemint.view.issues

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.app.turtlemint.R
import com.app.turtlemint.databinding.ActivityMainBinding
import com.app.turtlemint.utils.NetworkListener
import com.app.turtlemint.utils.NetworkUtils
import com.app.turtlemint.utils.Status
import com.app.turtlemint.utils.UIConstants
import com.app.turtlemint.view.comments.CommentsActivity
import com.app.turtlemint.viewmodel.issue.IssuesViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: IssuesViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        binding.lavIssuesLoader.setAnimation(R.raw.loader)
        viewModel = ViewModelProvider(this)[IssuesViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.executePendingBindings()

        registerObserver()

        getIssueAPICall()
    }

    private fun registerObserver() {
        viewModel.buttonClicked.observe(this) {
            if (it.content[0] as String == UIConstants.ISSUE_ITEM_CLICK_EVENT) {
                val title = it.content[1] as String
                val description = it.content[2] as String
                val commentUrl = it.content[3] as String
                val number = it.content[4] as String
                val intent = Intent(this, CommentsActivity::class.java)
                intent.putExtra(UIConstants.COMMENT_URL, commentUrl)
                intent.putExtra(UIConstants.TITLE, title)
                intent.putExtra(UIConstants.DESCRIPTION, description)
                intent.putExtra(UIConstants.ISSUE_NUMBER, number)
                startActivity(intent)
            }
        }
    }

    private fun getIssueAPICall() {
        if (!NetworkUtils.isInternetAvailable(this)) {
            NetworkUtils.showNoInternetDialog(
                this,
                object : NetworkListener {
                    override fun tryAgain() {
                        getIssueAPICall()
                    }
                }
            )
        }

        viewModel.getIssues(this).observe(this) {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        if (resource.data?.isNotEmpty() == true) {
                            viewModel.setIssueListValue(resource.data)
                        } else {
                            viewModel.setCommentAvailableStatus()
                        }
                        binding.lavIssuesLoader.visibility = View.GONE
                        binding.lavIssuesLoader.cancelAnimation()
                    }
                    Status.ERROR -> {

                        binding.lavIssuesLoader.visibility = View.GONE
                        binding.lavIssuesLoader.cancelAnimation()

                        Toast.makeText(this, resource.message.orEmpty(), Toast.LENGTH_SHORT)
                            .show()
                    }
                    Status.LOADING -> {
                        binding.lavIssuesLoader.visibility = View.VISIBLE
                        binding.lavIssuesLoader.playAnimation()

                    }
                }
            }
        }
    }
}
