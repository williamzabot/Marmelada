package br.com.digitalhouse.marmeladamovie.presenter.extensions

import android.content.Context
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import br.com.digitalhouse.marmeladamovie.R
import com.bumptech.glide.Glide

fun AppCompatActivity.hideKeyboard() {
    (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
        .hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
}

fun ImageView.load(url: String) {
    Glide.with(this).load(url).error(R.drawable.notfound).into(this)
}
