package uz.developersdreams.myapplication.core.extensions

import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import uz.developersdreams.myapplication.feature.domain.model.category.CategoryItem
import uz.developersdreams.myapplication.feature.domain.model.product.ProductItem
import uz.developersdreams.myapplication.feature.domain.model.tag.TagItem


fun NavController.navigateBack() {
    if (this.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
        this.popBackStack()
    }
}

/** Category -----------------------------------------------------------------------------------! */
fun List<CategoryItem>.changeChosenCategory(index: Int = 0): List<CategoryItem> {
    if (this.isEmpty() || index > this.lastIndex) return this
    this.find { it.isSelected }?.isSelected = false
    this[index].isSelected = true
    return this
}

fun List<CategoryItem>.getChosenCategoryIdByIndex(index: Int = 0): Int {
    if (index > this.lastIndex) return -1
    return this[index].id
}

fun List<CategoryItem>.getChosenCategoryId(): Int {
    return this.find { it.isSelected }?.id ?: -1
}

/** ChangeTags ---------------------------------------------------------------------------------! */
fun List<TagItem>.onTagSelection(index: Int): List<TagItem> {
    if (index > this.lastIndex) return this

    val tag = this[index].copy(
        isSelected = !this[index].isSelected
    )
    val tempTags = this.toMutableList()
    tempTags[index] = tag
    return tempTags.toList()
}

fun List<TagItem>.getSelectedTags(): List<Int> {
    val tempList = mutableListOf<Int>()
    this.forEach {
        if (it.isSelected) {
            tempList.add(it.id)
        }
    }
    return tempList.toList()
}
/** ProductInCart ------------------------------------------------------------------------------! */
fun List<ProductItem>.addProductToCart(id: Int): ProductItem? {
    val tempProduct = this.find { it.id == id } ?: return null

    return tempProduct.copy(
        valueOfInCart = tempProduct.valueOfInCart + 1
    )
}

fun List<ProductItem>.removeProductFromCart(id: Int): ProductItem? {
    val tempProduct = this.find { it.id == id } ?: return null
    return tempProduct.copy(
        valueOfInCart = tempProduct.valueOfInCart - 1
    )
}

fun List<ProductItem>.calculateValueOfProductsInCart(): Int {
    var value = 0
    this.forEach { value += it.priceCurrent * it.valueOfInCart }
    return value
}