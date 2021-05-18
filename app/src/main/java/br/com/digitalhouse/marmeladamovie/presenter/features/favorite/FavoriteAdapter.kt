package br.com.digitalhouse.marmeladamovie.presenter.features.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.digitalhouse.marmeladamovie.data.local.entity.MovieFavorite
import br.com.digitalhouse.marmeladamovie.databinding.ItemFavoriteBinding
import br.com.digitalhouse.marmeladamovie.presenter.extensions.load
import br.com.digitalhouse.marmeladamovie.presenter.extensions.toDate
import br.com.digitalhouse.marmeladamovie.presenter.extensions.year

class FavoriteAdapter(private val clickFavorite: (movie: MovieFavorite) -> Unit) :
    RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    var favorites = listOf<MovieFavorite>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemFavoriteBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return FavoriteViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return favorites.size
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(favorites[position])
    }

    inner class FavoriteViewHolder(binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val txtTitle = binding.favoriteTitle
        private val txtDate = binding.favoriteDate
        private val txtYear = binding.favoriteYear
        private val ratingBar = binding.favoriteRatingBar
        private val img = binding.favoriteImage

        fun bind(movie: MovieFavorite) {
            val url = "https://image.tmdb.org/t/p/w154${movie.poster_path}"
            if (movie.title == null) {
                txtTitle.text = movie.name
            } else {
                txtTitle.text = movie.title
            }
            txtDate.text = movie.release_date?.toDate()
            txtYear.text = movie.release_date?.year()
            img.load(url)
            ratingBar.rating = (movie.vote_average / 2).toFloat()

            itemView.setOnClickListener {
                clickFavorite(movie)
            }
        }
    }
}