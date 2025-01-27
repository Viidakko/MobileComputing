package com.example.composetutorial

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase

@Entity(tableName = "user_data")
data class User (
    @PrimaryKey val id: Int = 1,
    val username: String,
    val imageUri: String
)

@Dao
interface UserDao {
    @Query("SELECT * FROM user_data WHERE id = :id")
    suspend fun getUserData(id: Int): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserData(userData: User)
}

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}

fun getDatabase(context: Context): AppDatabase {
    return Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        "user_data_db"
    ).build()
}