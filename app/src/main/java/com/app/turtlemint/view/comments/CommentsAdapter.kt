package com.app.turtlemint.view.comments

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.turtlemint.databinding.ItemCommentsBinding
import com.app.turtlemint.model.CommentsResponseModelItem
import com.app.turtlemint.viewmodel.comment.CommentsItemViewModel

class CommentsAdapter(private var commentsList: List<CommentsResponseModelItem>) :
    RecyclerView.Adapter<CommentsAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: ItemCommentsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = ItemCommentsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.viewModel = CommentsItemViewModel(commentsList[position])
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return commentsList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(list: List<CommentsResponseModelItem>) {
        commentsList = list
        notifyDataSetChanged()
    }
}
