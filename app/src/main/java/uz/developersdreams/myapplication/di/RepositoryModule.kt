package uz.developersdreams.myapplication.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.developersdreams.myapplication.feature.data.data_source.local.CategoryDao
import uz.developersdreams.myapplication.feature.data.data_source.local.ProductDao
import uz.developersdreams.myapplication.feature.data.data_source.local.TagDao
import uz.developersdreams.myapplication.feature.data.data_source.remote.ApiHelper
import uz.developersdreams.myapplication.feature.data.repository_impl.CategoryRepositoryImpl
import uz.developersdreams.myapplication.feature.data.repository_impl.ProductRepositoryImpl
import uz.developersdreams.myapplication.feature.data.repository_impl.TagRepositoryImpl
import uz.developersdreams.myapplication.feature.domain.repository.CategoryRepository
import uz.developersdreams.myapplication.feature.domain.repository.ProductRepository
import uz.developersdreams.myapplication.feature.domain.repository.TagRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @[Provides Singleton]
    fun provideCategoryRepository(
        apiHelper: ApiHelper,
        categoryDao: CategoryDao
    ): CategoryRepository {
        return CategoryRepositoryImpl(apiHelper, categoryDao)
    }

    @[Provides Singleton]
    fun provideTagRepository(
        apiHelper: ApiHelper,
        tagDao: TagDao
    ): TagRepository {
        return TagRepositoryImpl(apiHelper, tagDao)
    }

    @[Provides Singleton]
    fun provideProductRepository(
        apiHelper: ApiHelper,
        productDao: ProductDao,
    ): ProductRepository {
        return ProductRepositoryImpl(apiHelper, productDao)
    }
}