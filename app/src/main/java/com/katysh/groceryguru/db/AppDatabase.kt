package com.katysh.groceryguru.db

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.katysh.groceryguru.model.Entry
import com.katysh.groceryguru.model.ExpirationEntry
import com.katysh.groceryguru.model.Product

@Database(
    entities = [Product::class, ExpirationEntry::class, Entry::class],
    version = 3,
    exportSchema = false
)
@TypeConverters(value = [DateConverter::class])
abstract class AppDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao

    abstract fun expirationDao(): ExpirationEntryDao

    abstract fun entryDao(): EntryDao

    companion object {
        private const val DB_NAME = "grocery_guru_db"
        private var INSTANCE: AppDatabase? = null
        private val LOCK = Any()

        fun getInstance(application: Application): AppDatabase {
            INSTANCE?.let {
                return it
            }
            synchronized(LOCK) {
                INSTANCE?.let {
                    return it
                }
                val db = Room.databaseBuilder(
                    application,
                    AppDatabase::class.java,
                    DB_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = db
                return db
            }
        }
    }
}