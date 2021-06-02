package br.com.digitalhouse.marmeladamovie.presenter.extensions

import java.text.SimpleDateFormat
import java.util.*


fun String?.year(): String? {
    if (this != "" && this != null) {
        val parser = SimpleDateFormat("yyyy-MM-dd")
        val date = parser.parse(this)
        val formatter = SimpleDateFormat("yyyy", Locale.getDefault())
        return formatter.format(date)
    }
    return null
}

fun String.toDate(): String? {
    if (this != "" && this != null) {
        val parser = SimpleDateFormat("yyyy-MM-dd")
        val date = parser.parse(this)
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return formatter.format(date)
    }
    return null
}

fun String.toValidId() : String {
    return this.replace(".", "").replace("@", "").replace("-", "")
}
