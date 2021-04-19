package br.com.digitalhouse.marmeladamovie.presenter.features.home

import androidx.recyclerview.widget.RecyclerView
import br.com.digitalhouse.marmeladamovie.data.remote.model.Movie
import br.com.digitalhouse.marmeladamovie.databinding.ItemFilmsSeriesBinding
import br.com.digitalhouse.marmeladamovie.presenter.extensions.load
import com.bumptech.glide.Glide

class MovieViewHolder(private val binding: ItemFilmsSeriesBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: Movie) {
        val url = "https://image.tmdb.org/t/p/w154${movie.poster_path}"
        binding.apply {
            movieTitle.text = movie.title
            movieData.text = movie.release_date
            movieImg.load(url)
        }
    }
}