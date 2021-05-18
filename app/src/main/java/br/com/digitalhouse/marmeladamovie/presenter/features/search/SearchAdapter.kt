package br.com.digitalhouse.marmeladamovie.presenter.features.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.digitalhouse.marmeladamovie.data.remote.model.movie.Movie
import br.com.digitalhouse.marmeladamovie.databinding.ItemSearchBinding

class SearchAdapter(private val clickMovie: (movie: Movie) -> Unit) :
    RecyclerView.Adapter<SearchViewHolder>() {

    var movies = listOf<Movie>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }


    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
            val movie = movies[position]
            holder.bind(movie)
            holder.itemView.setOnClickListener {
                clickMovie(movie)
            }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = ItemSearchBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return SearchViewHolder(binding)
    }

    override fun getItemCount() = movies.size
}