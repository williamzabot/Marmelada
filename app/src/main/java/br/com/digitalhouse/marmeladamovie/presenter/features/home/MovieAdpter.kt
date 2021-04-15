package br.com.digitalhouse.marmeladamovie.presenter.features.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.digitalhouse.marmeladamovie.data.remote.model.Movie
import br.com.digitalhouse.marmeladamovie.databinding.ItemFilmsSeriesBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    var movies = listOf<Movie>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class MovieViewHolder(private val binding: ItemFilmsSeriesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            val url = "https://image.tmdb.org/t/p/w154${movie.poster_path}"
            binding.apply {
                movieTitle.text = movie.title
                movieData.text = movie.release_date
                Glide.with(movieImg)
                    .load(url)
                    .into(movieImg)}
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemFilmsSeriesBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        return holder.bind(movies[position])
    }

    override fun getItemCount(): Int {
        return movies.size
    }
}