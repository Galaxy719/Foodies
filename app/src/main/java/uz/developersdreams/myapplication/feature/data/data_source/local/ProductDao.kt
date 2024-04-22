package uz.developersdreams.myapplication.feature.data.data_source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import uz.developersdreams.myapplication.feature.domain.model.product.ProductItem

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<ProductItem>)

    @Query("SELECT * FROM product_table WHERE id = :id")
    suspend fun getProductById(id: Int): ProductItem?

    @Query("SELECT * FROM product_table " +
            "WHERE (:categoryId IS NULL OR categoryId = :categoryId)" +
            "AND (:name IS NULL OR name LIKE '%' || :name || '%')" +
            "AND (:inCart IS NULL OR valueOfInCart > :inCart)"
    )
    fun getProducts(
        categoryId: Int? = null,
        name: String? = null,
        inCart: Int? = null
    ): Flow<List<ProductItem>>

    @Query("DELETE FROM product_table")
    suspend fun deleteProducts()
}