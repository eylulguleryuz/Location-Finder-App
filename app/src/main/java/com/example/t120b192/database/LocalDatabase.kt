package com.example.t120b192.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.t120b192.components.Converters
import com.example.t120b192.DAOs.MeasurementDAO
import com.example.t120b192.DAOs.StrengthDAO
import com.example.t120b192.DAOs.UserDAO
import com.example.t120b192.entities.Measurement
import com.example.t120b192.entities.Strength
import com.example.t120b192.entities.User

@Database(entities = [Measurement::class, Strength::class, User::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun measurementDao(): MeasurementDAO
    abstract fun userDao(): UserDAO
    abstract fun strengthDao(): StrengthDAO

    companion object {
        @Volatile
        private var INSTANCE: LocalDatabase? = null

        fun getDatabase(context: Context): LocalDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LocalDatabase::class.java,
                    "local_database"
                )
                    .addCallback(MeasurementDatabaseCallback())
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class MeasurementDatabaseCallback : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                // You can perform any initialization logic here.
            }
        }
    }
}
