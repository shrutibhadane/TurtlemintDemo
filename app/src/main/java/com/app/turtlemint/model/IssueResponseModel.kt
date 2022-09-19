package com.app.turtlemint.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

class IssueResponseModel : ArrayList<IssueResponseModelItem>()

@Entity(tableName = "issue")
data class IssueResponseModelItem(

    @SerializedName("body")
    @ColumnInfo(name = "body")
    val body: String?,
    @SerializedName("comments")
    @ColumnInfo(name = "comments")
    val comments: String,
    @SerializedName("number")
    @ColumnInfo(name = "number")
    val number: String,
    @SerializedName("comments_url")
    @ColumnInfo(name = "comments_url")
    val commentsUrl: String,
    @SerializedName("created_at")
    @ColumnInfo(name = "created_at")
    val createdAt: String,
    @SerializedName("id")
    @ColumnInfo(name = "id")
    val id: Int,
    @SerializedName("title")
    @ColumnInfo(name = "title")
    val title: String,
    @SerializedName("updated_at")
    @ColumnInfo(name = "updated_at")
    val updatedAt: String,
    @SerializedName("url")
    @ColumnInfo(name = "url")
    val url: String,
    @SerializedName("user")
    val user: User?,
    @PrimaryKey(autoGenerate = true)
    val dbId: Int?,
)
