package com.katysh.groceryguru.db

import android.app.Application
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.DeleteTable
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.AutoMigrationSpec
import com.katysh.groceryguru.model.Entry
import com.katysh.groceryguru.model.Portion
import com.katysh.groceryguru.model.Product

@Database(
    entities = [Product::class, Entry::class, Portion::class],
    version = 7,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 6, to = 7, spec = YourAutoMigrationSpec::class)
    ]
)
@TypeConverters(value = [Converters::class])
abstract class AppDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao

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
                    .build()
                INSTANCE = db
                return db
            }
        }
    }
}

@DeleteTable(tableName = "expiration_entry")
class YourAutoMigrationSpec : AutoMigrationSpec