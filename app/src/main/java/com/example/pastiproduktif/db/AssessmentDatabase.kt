package com.example.pastiproduktif.db

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.pastiproduktif.entity.Assessment


@Database(entities = [Assessment::class], version = 4, exportSchema = false)
abstract class AssessmentDatabase: RoomDatabase() {
    abstract fun dao(): AssessmentDao
    
    companion object{
        @Volatile
        private var INSTANCE: AssessmentDatabase? = null

        val MIGRATION_4_5 = object: Migration(3, 4){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "ALTER TABLE assessment_table ADD COLUMN time TEXT DEFAULT null"
                )
                database.execSQL(
                    "ALTER TABLE assessment_table ADD COLUMN rating INTEGER NOT NULL DEFAULT 2"
                )
            }
        }

        fun getDatabase(context: Context): AssessmentDatabase{
            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(context.applicationContext, AssessmentDatabase::class.java, "assessment_table")
                    .addMigrations(MIGRATION_4_5)
                    .build()
            }
            return INSTANCE as AssessmentDatabase
        }
    }
}

