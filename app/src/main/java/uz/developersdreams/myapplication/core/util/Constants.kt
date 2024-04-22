package uz.developersdreams.myapplication.core.util

object Constants {
    const val APP_DATABASE = "app_database"
    const val BASE_URL = "https://anika1d.github.io/WorkTestServer/"

    /** Entities ------------------------------------------------------------------------------! **/
    const val CATEGORY_TABLE = "category_table"
    const val TAG_TABLE = "tag_table"
    const val PRODUCT_TABLE = "product_table"
    const val PRODUCT_IN_CART_TABLE = "product_in_cart_table"

    /** Screen ---------------------------------------------------------------------------------! */
    const val SPLASH_SCREEN = "splash_screen"
    const val CATALOG_SCREEN = "catalog_screen"
    const val SEARCH_SCREEN = "search_screen"
    const val CARD_PRODUCT_SCREEN = "card_product_screen"
    const val CART_SCREEN = "cart_screen"

    /** Others ---------------------------------------------------------------------------------! */
    const val IS_DATA_LOADED = "is_data_loaded"
    const val PRODUCT_ID = "product_id"
    const val SEARCH_TEXT_MAX_LENGTH = 30
    const val DELAY_FOR_SPLASH_SCREEN = 2000L
    const val PRODUCT_IN_CART = 0 // if value of valueOfInCart > 0, productInCart, else not
}