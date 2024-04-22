package uz.developersdreams.myapplication.feature.domain.use_case.product

data class ProductUseCases(
    val getProductsFromApi: GetProductsFromApi,
    val getProductById: GetProductById,
    val getProductsFromLocal: GetProductsFromLocal,
    val addProduct: AddProduct
)
