package com.app.turtlemint.utils

import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.app.turtlemint.R
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*

object BindingAdapterUtils {

    @JvmStatic
    @BindingAdapter("setImageUrl")
    fun setImageUrl(view: AppCompatImageView, url: String) {
        Glide.with(view.context)
            .load(url)
            .dontAnimate()
            .dontTransform()
            .placeholder(
                ContextCompat.getDrawable(
                    view.context,
                    R.drawable.ic_baseline_account_box_24
                )
            )
            .into(view)
    }

    @JvmStatic
    @BindingAdapter("setDataTime")
    fun setDataTime(view: AppCompatTextView, text: String) {
        try {
            if (text.isNotEmpty()) {
                val oldDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
                val parseDate = oldDate.parse(text)
                parseDate?.let {
                    val newFormat = SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.ENGLISH)
                    val newDate = newFormat.format(parseDate)
                    view.text = newDate
                }
            }
        } catch (e: Exception) {
            view.text = ""
        }
    }

    @JvmStatic
    @BindingAdapter("setCommentDataTime")
    fun setCommentDataTime(view: AppCompatTextView, text: String) {
        try {
            if (text.isNotEmpty()) {
                val oldDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
                val parseDate = oldDate.parse(text)
                parseDate?.let {
                    val newFormat = SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.ENGLISH)
                    val newDate = newFormat.format(parseDate)
                    view.text =
                        String.format(view.context.getString(R.string.commented_on_value), newDate)
                }
            }
        } catch (e: Exception) {
            view.text = ""
        }
    }

    @JvmStatic
    @BindingAdapter("setCommentUrl")
    fun setCommentUrl(view: AppCompatTextView, text: String) {
        try {
            if (text.isNotEmpty()) {
                view.text =
                    String.format(view.context.getString(R.string.github_comment_value), text)
            }
        } catch (e: Exception) {
            view.text = ""
        }
    }

    @JvmStatic
    @BindingAdapter("setIssue")
    fun setIssue(view: AppCompatTextView, text: String) {
        try {
            if (text.isNotEmpty()) {
                view.text = String.format(view.context.getString(R.string.issue), text)
            }
        } catch (e: Exception) {
            view.text = ""
        }
    }

    @JvmStatic
    @BindingAdapter("setDescription")
    fun setDescription(view: AppCompatTextView, text: String) {
        try {
            if (text.isNotEmpty()) {
                view.text = String.format(view.context.getString(R.string.description), text)
            }
        } catch (e: Exception) {
            view.text = ""
        }
    }

    @JvmStatic
    @BindingAdapter("setCommentsCount")
    fun setCommentsCount(view: AppCompatTextView, text: String) {
        try {
            if (text.isNotEmpty()) {
                view.text = String.format(view.context.getString(R.string.comments_count), text)
            }
        } catch (e: Exception) {
            view.text = ""
        }
    }
}
