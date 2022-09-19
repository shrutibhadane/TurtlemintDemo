package com.app.turtlemint.view.issues

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.turtlemint.databinding.ItemIssuesBinding
import com.app.turtlemint.model.IssueResponseModelItem
import com.app.turtlemint.viewmodel.issue.IssuesItemViewModel
import com.app.turtlemint.viewmodel.issue.IssuesViewModel

class IssuesAdapter(
    private var issueList: List<IssueResponseModelItem>,
    private val viewModel: IssuesViewModel,
) :

    RecyclerView.Adapter<IssuesAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: ItemIssuesBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = ItemIssuesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.viewModel = IssuesItemViewModel(issueList[position], viewModel)
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return issueList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(list: List<IssueResponseModelItem>) {
        issueList = list
        notifyDataSetChanged()
    }
}
