package br.com.digitalhouse.marmeladamovie.presenter.features.home

import androidx.recyclerview.widget.RecyclerView
import br.com.digitalhouse.marmeladamovie.data.remote.model.movie.Movie
import br.com.digitalhouse.marmeladamovie.databinding.ItemFilmsSeriesBinding
import br.com.digitalhouse.marmeladamovie.presenter.extensions.load

class MovieViewHolder(binding: ItemFilmsSeriesBinding) :
    RecyclerView.ViewHolder(binding.root) {
    private val txtTitle = binding.movieTitle
    private val img = binding.movieImg

    fun bind(movie: Movie) {
        val url = "https://image.tmdb.org/t/p/w154${movie.poster_path}"
        txtTitle.text = movie.title
        img.load(url)
    }
}