package com.app.turtlemint.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

class CommentsResponseModel : ArrayList<CommentsResponseModelItem>()

@Entity(tableName = "comment")
data class CommentsResponseModelItem(
    @SerializedName("body")
    @ColumnInfo(name = "body")
    val body: String?,
    @SerializedName("created_at")
    @ColumnInfo(name = "created_at")
    val createdAt: String,
    @SerializedName("id")
    @ColumnInfo(name = "id")
    val id: Int,
    @SerializedName("node_id")
    @ColumnInfo(name = "node_id")
    val nodeId: String,
    @SerializedName("updated_at")
    @ColumnInfo(name = "updated_at")
    val updatedAt: String,
    @SerializedName("url")
    @ColumnInfo(name = "url")
    val url: String,
    @SerializedName("user")
    val user: User,
    @SerializedName("number")
    @ColumnInfo(name = "number")
    var number: String?,
    @PrimaryKey(autoGenerate = true)
    val dbId: Int?,
)
