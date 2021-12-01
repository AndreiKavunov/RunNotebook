package ru.kavunov.runnotebook.bd

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [NotebookTable::class, PhotoNoteBTable::class, TrainingTable::class, PhotoTables::class], version = 27)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun notebookDAO(): NotebookDAO
    abstract fun photoNotBDAO(): PhotoNotBDAO
    abstract fun photoDAO(): PhotoDAO
    abstract fun trainDAO(): TrainDAO

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null
        private const val DATABASE_NAME = "DataBase.db"
        fun getDataseClient(context: Context) : AppDatabase {

            if (INSTANCE != null) return INSTANCE as AppDatabase

            synchronized(this) {

                INSTANCE = Room
                    .databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build()

                return INSTANCE as AppDatabase

            }
        }
    }



}