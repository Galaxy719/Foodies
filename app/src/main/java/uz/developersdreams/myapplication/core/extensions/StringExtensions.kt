package uz.developersdreams.myapplication.core.extensions

import uz.developersdreams.myapplication.core.util.Constants.SEARCH_TEXT_MAX_LENGTH
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

/** DecimalFormat ------------------------------------------------------------------------------! */
fun Int.decimalFormatToText(): String {
    return try {
        val symbols = DecimalFormatSymbols(Locale.US)
        val formatter = DecimalFormat("#,###.##", symbols)
        formatter.format(this).replace(',', ' ').plus(" ₽")
    }catch (_ : Exception) {
        ""
    }
}

fun String.getMaxValueLength(maxVal: Int = SEARCH_TEXT_MAX_LENGTH): Boolean {
    return this.length > maxVal
}