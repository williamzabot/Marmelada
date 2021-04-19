package br.com.digitalhouse.marmeladamovie.presenter.extensions

import java.text.SimpleDateFormat
import java.util.*


fun String.year(): String {
    val parser = SimpleDateFormat("yyyy-MM-dd")
    val date = parser.parse(this)
    val formatter = SimpleDateFormat("yyyy", Locale.getDefault())
    return formatter.format(date)
}

fun String.toDate(): String {
    val parser = SimpleDateFormat("yyyy-MM-dd")
    val date = parser.parse(this)
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return formatter.format(date)
}
