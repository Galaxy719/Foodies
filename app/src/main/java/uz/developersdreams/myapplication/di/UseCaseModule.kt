package uz.developersdreams.myapplication.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.developersdreams.myapplication.feature.domain.repository.CategoryRepository
import uz.developersdreams.myapplication.feature.domain.repository.ProductRepository
import uz.developersdreams.myapplication.feature.domain.repository.TagRepository
import uz.developersdreams.myapplication.feature.domain.use_case.category.CategoryUseCases
import uz.developersdreams.myapplication.feature.domain.use_case.category.GetCategoriesFromApi
import uz.developersdreams.myapplication.feature.domain.use_case.category.GetCategoriesFromLocal
import uz.developersdreams.myapplication.feature.domain.use_case.product.AddProduct
import uz.developersdreams.myapplication.feature.domain.use_case.product.GetProductById
import uz.developersdreams.myapplication.feature.domain.use_case.product.GetProductsFromApi
import uz.developersdreams.myapplication.feature.domain.use_case.product.GetProductsFromLocal
import uz.developersdreams.myapplication.feature.domain.use_case.product.ProductUseCases
import uz.developersdreams.myapplication.feature.domain.use_case.tag.GetTagsFromApi
import uz.developersdreams.myapplication.feature.domain.use_case.tag.GetTagsFromLocal
import uz.developersdreams.myapplication.feature.domain.use_case.tag.TagUseCases
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @[Provides Singleton]
    fun provideCategoryUseCases(repository: CategoryRepository): CategoryUseCases {
        return CategoryUseCases(
            getCategoriesFromApi = GetCategoriesFromApi(repository),
            getCategoriesFromLocal = GetCategoriesFromLocal(repository)
        )
    }

    @[Provides Singleton]
    fun provideTagUseCases(repository: TagRepository): TagUseCases {
        return TagUseCases(
            getTagsFromApi = GetTagsFromApi(repository),
            getTagsFromLocal = GetTagsFromLocal(repository)
        )
    }

    @[Provides Singleton]
    fun provideProductUseCases(repository: ProductRepository): ProductUseCases {
        return ProductUseCases(
            getProductsFromApi = GetProductsFromApi(repository),
            getProductById = GetProductById(repository),
            getProductsFromLocal = GetProductsFromLocal(repository),
            addProduct = AddProduct(repository)
        )
    }
}