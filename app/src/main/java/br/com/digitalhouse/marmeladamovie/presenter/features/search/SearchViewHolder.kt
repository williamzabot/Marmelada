package br.com.digitalhouse.marmeladamovie.presenter.features.search

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import br.com.digitalhouse.marmeladamovie.data.remote.model.movie.Movie
import br.com.digitalhouse.marmeladamovie.databinding.ItemSearchBinding
import br.com.digitalhouse.marmeladamovie.presenter.extensions.load
import br.com.digitalhouse.marmeladamovie.presenter.extensions.toDate
import br.com.digitalhouse.marmeladamovie.presenter.extensions.year

class SearchViewHolder(binding: ItemSearchBinding) :
    RecyclerView.ViewHolder(binding.root) {
    private val txtTitle = binding.searchTitle
    private val txtDate = binding.searchDate
    private val txtYear = binding.searchYear
    private val ratingBar = binding.searchRatingBar
    private val img = binding.searchImage
    private val separate = binding.separateYear
    private val description = binding.searchDescription

    fun bind(movie: Movie) {
        val url = "https://image.tmdb.org/t/p/w154${movie.poster_path}"
        img.load(url)
        ratingBar.rating = (movie.vote_average / 2).toFloat()
        if (movie.media_type == "tv") {
            separate.visibility = View.GONE
            txtTitle.text = movie.name
            txtDate.visibility = View.GONE
            txtYear.visibility = View.GONE
            description.visibility = View.VISIBLE
            description.text = movie.overview
            ratingBar.visibility = View.GONE
        } else {
            txtTitle.text = movie.title
            txtDate.text = movie.release_date?.toDate()
            txtYear.text = movie.release_date?.year()
        }
    }
}