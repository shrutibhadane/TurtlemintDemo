package com.app.turtlemint.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.app.turtlemint.R
import com.app.turtlemint.model.CommentsResponseModelItem
import com.app.turtlemint.model.IssueResponseModelItem

@Database(entities = [IssueResponseModelItem::class, CommentsResponseModelItem::class], version = 4)
@TypeConverters(TypeConverterHelper::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun issueDao(): IssueDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getAppDataBase(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext, AppDatabase::class.java,
                        context.getString(R.string.app_name)
                    ).build()
                }
            }
            return INSTANCE
        }

        fun destroyDataBase() {
            INSTANCE = null
        }
    }
}
