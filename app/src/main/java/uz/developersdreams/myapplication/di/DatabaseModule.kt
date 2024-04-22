package uz.developersdreams.myapplication.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.developersdreams.myapplication.core.util.Constants
import uz.developersdreams.myapplication.feature.data.data_source.local.AppDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @[Provides Singleton]
    fun provideAppDatabase(
        app: Application
    ): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            Constants.APP_DATABASE
        ).build()
    }

    @[Provides Singleton]
    fun provideCategoryDao(db: AppDatabase) = db.categoryDao

    @[Provides Singleton]
    fun provideTagDao(db: AppDatabase) = db.tagDao

    @[Provides Singleton]
    fun provideProductDao(db: AppDatabase) = db.productDao
}