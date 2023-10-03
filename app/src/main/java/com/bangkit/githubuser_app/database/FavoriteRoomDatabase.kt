package com.bangkit.githubuser_app.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [FavoriteUser::class], version = 2)
abstract class FavoriteRoomDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        private const val DB_NAME = "favorite_database"
        @Volatile
        private var INSTANCE: FavoriteRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): FavoriteRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val migration1to2 = object : Migration(1, 2) {
                    override fun migrate(database: SupportSQLiteDatabase) {
                        TODO("Not yet implemented")
                    }
                }

                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    FavoriteRoomDatabase::class.java,
                    DB_NAME
                )
                    .addMigrations(migration1to2)
                    .build().also { INSTANCE = it }
            }
        }
    }
}