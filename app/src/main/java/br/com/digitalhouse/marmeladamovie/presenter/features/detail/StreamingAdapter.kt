package br.com.digitalhouse.marmeladamovie.presenter.features.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import br.com.digitalhouse.marmeladamovie.R
import br.com.digitalhouse.marmeladamovie.data.remote.model.movie.streaming.Flatrate
import br.com.digitalhouse.marmeladamovie.presenter.extensions.load

class StreamingAdapter(private val streaming: List<Flatrate>) :
    RecyclerView.Adapter<StreamingAdapter.StreamingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StreamingViewHolder {
        return StreamingViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_streaming, parent, false)
        )
    }

    override fun getItemCount() = streaming.size

    override fun onBindViewHolder(holder: StreamingViewHolder, position: Int) {
        if (itemCount <= 3) {
            holder.bind(streaming[position])
        }
    }

    class StreamingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val img = itemView.findViewById<ImageView>(R.id.imageView_streaming)

        fun bind(streaming: Flatrate) {
            val url = "https://image.tmdb.org/t/p/w154${streaming.logo_path}"
            img.load(url)
        }
    }
}