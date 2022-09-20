package com.app.turtlemint.view.comments

import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.app.turtlemint.R
import com.app.turtlemint.databinding.ActivityCommentsBinding
import com.app.turtlemint.utils.NetworkListener
import com.app.turtlemint.utils.NetworkUtils
import com.app.turtlemint.utils.Status
import com.app.turtlemint.utils.UIConstants
import com.app.turtlemint.viewmodel.comment.CommentsViewModel

class CommentsActivity : AppCompatActivity() {
    private lateinit var viewModel: CommentsViewModel
    private lateinit var binding: ActivityCommentsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommentsBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        binding.lavCommentsLoader.setAnimation(R.raw.loader)
        viewModel = ViewModelProvider(this)[CommentsViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.executePendingBindings()

        // calling the action bar
        val actionBar: ActionBar? = supportActionBar
        // showing the back button in action bar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val commentUrl = intent.getStringExtra(UIConstants.COMMENT_URL).orEmpty()
        val title = intent.getStringExtra(UIConstants.TITLE).orEmpty()
        val descriptionBody = intent.getStringExtra(UIConstants.DESCRIPTION).orEmpty()
        val number = intent.getStringExtra(UIConstants.ISSUE_NUMBER).orEmpty()
        viewModel.setIssueTitle(title)

        if (descriptionBody.length > 150) {
            binding.tvSeeMore.visibility = View.VISIBLE
            binding.tvSeeLess.visibility = View.GONE
        } else {
            binding.tvSeeMore.visibility = View.GONE
            binding.tvSeeLess.visibility = View.GONE
        }

        //set issue description
        binding.tvIssueDescription.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(150))
        viewModel.setIssueDescription(descriptionBody)

        //set seeMore seeLess click listerners
        seeMoreOrLessClick(descriptionBody)

        getCommentsAPICall(commentUrl, number)

    }

    private fun seeMoreOrLessClick(descriptionBody: String) {

        viewModel.buttonClicked.observe(this) {
            if (it.content[0] as String == UIConstants.DESCRIPTION_SEE_MORE_CLICK_EVENT) {

                binding.tvIssueDescription.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(
                    descriptionBody.length))
                binding.tvIssueDescription.text = String.format("%s", descriptionBody)
                binding.tvSeeMore.visibility = View.GONE
                binding.tvSeeLess.visibility = View.VISIBLE

            } else if (it.content[0] as String == UIConstants.DESCRIPTION_SEE_LESS_CLICK_EVENT) {

                binding.tvIssueDescription.filters =
                    arrayOf<InputFilter>(InputFilter.LengthFilter(150))
                binding.tvIssueDescription.text = String.format("%s", descriptionBody)
                binding.tvSeeMore.visibility = View.VISIBLE
                binding.tvSeeLess.visibility = View.GONE
            }
        }
    }

    // this event will enable the back function to the button on press
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun getCommentsAPICall(commentUrl: String, number: String) {
        if (!NetworkUtils.isInternetAvailable(this)) {
            NetworkUtils.showNoInternetDialog(
                this,
                object : NetworkListener {
                    override fun tryAgain() {
                        getCommentsAPICall(commentUrl, number)
                    }
                }
            )
        }

        viewModel.getComments(commentUrl, number, this).observe(this) {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        if (resource.data?.isNotEmpty() == true) {
                            viewModel.setCommentListValue(resource.data)
                        } else {
                            viewModel.setCommentAvailableStatus()
                        }
                        binding.lavCommentsLoader.visibility = View.GONE
                        binding.lavCommentsLoader.cancelAnimation()
                    }
                    Status.ERROR -> {

                        binding.lavCommentsLoader.visibility = View.GONE
                        binding.lavCommentsLoader.cancelAnimation()
                        Toast.makeText(this, resource.message.orEmpty(), Toast.LENGTH_SHORT)
                            .show()
                    }
                    Status.LOADING -> {
                        binding.lavCommentsLoader.visibility = View.VISIBLE
                        binding.lavCommentsLoader.playAnimation()
                    }
                }
            }
        }
    }
}
