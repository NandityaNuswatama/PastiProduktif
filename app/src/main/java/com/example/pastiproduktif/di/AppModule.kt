package com.example.pastiproduktif.di

import android.app.Application
import androidx.room.Room
import com.example.pastiproduktif.db.ActivityRepository
import com.example.pastiproduktif.db.AssessmentDao
import com.example.pastiproduktif.db.AssessmentDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


//Provide all the app level dependency here
@Module
class AppModule {


    // Method #1
    @Singleton
    @Provides
    fun providesAppDatabase(app:Application):AssessmentDatabase{
        return Room.databaseBuilder(app,AssessmentDatabase::class.java,"note_database").build()
    }

    // Method #2
    @Singleton
    @Provides
    fun providesNoteDao(db: AssessmentDatabase): AssessmentDao {
        return db.dao()
    }

    // Method #3
    @Provides
    fun providesRepository(dao: AssessmentDao):ActivityRepository{
        return ActivityRepository(dao)
    }
}