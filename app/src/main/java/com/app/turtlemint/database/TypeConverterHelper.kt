package com.app.turtlemint.database

import androidx.room.TypeConverter
import com.app.turtlemint.model.User
import org.json.JSONObject

class TypeConverterHelper {
    @TypeConverter
    fun fromSource(source: User): String {
        return JSONObject().apply {
            put("avatar_url", source.avatarUrl)
            put("id", source.id)
            put("login", source.login)
            put("node_id", source.nodeId)
            put("type", source.type)
            put("url", source.url)
        }.toString()
    }

    @TypeConverter
    fun toSource(source: String): User {
        val json = JSONObject(source)
        return User(
            json.get("avatar_url").toString(),
            json.getString("id").toInt(),
            json.get("login").toString(),
            json.get("node_id").toString(),
            json.get("type").toString(),
            json.get("url").toString(),
            ""
        )
    }
}
