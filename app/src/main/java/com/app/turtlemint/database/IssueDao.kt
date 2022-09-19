package com.app.turtlemint.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.app.turtlemint.model.CommentsResponseModelItem
import com.app.turtlemint.model.IssueResponseModelItem

@Dao
interface IssueDao {
    @Query("select * from issue")
    fun loadAllIssues(): List<IssueResponseModelItem>

    @Query("select * from comment where number = :number")
    fun loadAllComment(number: String): List<CommentsResponseModelItem>

    @Insert
    fun insertIssue(vararg issues: IssueResponseModelItem)

    @Insert
    fun insertComment(vararg comments: CommentsResponseModelItem)

    @Query("DELETE FROM issue")
    fun deleteIssueAll()

    @Query("DELETE FROM comment where number = :number")
    fun deleteCommentAll(number: String)
}
