package br.com.digitalhouse.marmeladamovie.presenter.features.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import br.com.digitalhouse.marmeladamovie.data.remote.model.movie.Movie
import br.com.digitalhouse.marmeladamovie.databinding.ItemFilmsSeriesBinding

class MovieAdapter(private val clickMovie: (movie: Movie) -> Unit) :
    PagingDataAdapter<Movie, MovieViewHolder>(MOVIE_COMPARATOR) {

    companion object {
        private val MOVIE_COMPARATOR = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        getItem(position)?.let { movie ->
            holder.bind(movie)
            holder.itemView.setOnClickListener {
                clickMovie(movie)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemFilmsSeriesBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MovieViewHolder(binding)
    }
}