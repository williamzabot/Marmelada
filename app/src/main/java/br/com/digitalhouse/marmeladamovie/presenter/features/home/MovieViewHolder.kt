package br.com.digitalhouse.marmeladamovie.presenter.features.home

import androidx.recyclerview.widget.RecyclerView
import br.com.digitalhouse.marmeladamovie.data.remote.model.Movie
import br.com.digitalhouse.marmeladamovie.databinding.ItemFilmsSeriesBinding
import br.com.digitalhouse.marmeladamovie.presenter.extensions.load
import com.bumptech.glide.Glide

class MovieViewHolder(binding: ItemFilmsSeriesBinding) :
    RecyclerView.ViewHolder(binding.root) {
    private val txtTitle = binding.movieTitle
    private val txtDate = binding.movieData
    private val img = binding.movieImg

    fun bind(movie: Movie) {
        val url = "https://image.tmdb.org/t/p/w154${movie.poster_path}"
        txtTitle.text = movie.title
        txtDate.text = movie.release_date
        img.load(url)
    }
}