package br.com.digitalhouse.marmeladamovie.presenter.features.detail

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import br.com.digitalhouse.marmeladamovie.R
import br.com.digitalhouse.marmeladamovie.data.local.entity.MovieFavorite
import br.com.digitalhouse.marmeladamovie.data.local.entity.toMovie
import br.com.digitalhouse.marmeladamovie.data.remote.model.movie.Movie
import br.com.digitalhouse.marmeladamovie.databinding.FragmentDetailBinding
import br.com.digitalhouse.marmeladamovie.presenter.extensions.load
import br.com.digitalhouse.marmeladamovie.presenter.extensions.toDate
import br.com.digitalhouse.marmeladamovie.presenter.extensions.year
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<MovieDetailFragmentArgs>()
    private val viewModel by viewModels<MovieDetailViewModel>()
    private lateinit var movie: Movie
    private var favorite: MovieFavorite? = null
    private var heartAdd: Drawable? = null
    private var heartOk: Drawable? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeEvents()
        heartClick()
        binding.detailSeeTrailer.setOnClickListener {
            openYoutubeLink("/results?search_query=trailer${args.movie?.title}")
        }
    }

    private fun openYoutubeLink(youtubeID: String) {
        try {
            startActivity(Intent(ACTION_VIEW, Uri.parse("vnd.youtube:$youtubeID")))
        } catch (ex: ActivityNotFoundException) {
            startActivity(
                Intent(
                    ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v=$youtubeID")
                )
            )
        }
    }

    private fun initView() {
        initDrawables()
        getMovie()
        viewModel.getStreaming(movie.id)
        loadData()
    }

    private fun initDrawables() {
        heartAdd = ContextCompat.getDrawable(requireContext(), R.drawable.heartadd)
        heartOk = ContextCompat.getDrawable(requireContext(), R.drawable.heartok)
    }

    private fun getMovie() {
        movie = if (args.movie != null && args.favorite == null) {
            viewModel.checkIsFavorite(args.movie!!.id)
            args.movie!!
        } else {
            favorite = args.favorite
            args.favorite?.toMovie()!!
        }
    }

    private fun heartClick() {
        binding.detailFavorite.setOnClickListener {
            if (movie.favorite) {
                favorite?.let {
                    viewModel.desfavorite(it)
                    binding.detailFavorite.setImageDrawable(heartAdd)
                    movie.favorite = false
                }
            } else {
                viewModel.favorite(movie)
                binding.detailFavorite.setImageDrawable(heartOk)
                Toast.makeText(
                    requireContext(),
                    "Adicionado aos seus favoritos",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun observeEvents() {
        viewModel.isFavorite.observe(viewLifecycleOwner, Observer {
            favorite = it
            movie = it.toMovie()
            binding.detailFavorite.setImageDrawable(heartOk)
        })

        viewModel.streaming.observe(viewLifecycleOwner) { data ->
            try {
                val players = data.flatrate
                if (players != null && players.isNotEmpty()) {
                    binding.recyclerViewStreamings.adapter = StreamingAdapter(players)
                    if (players.size <= 2) {
                        binding.recyclerViewStreamings.layoutParams.width =
                            RecyclerView.LayoutParams.WRAP_CONTENT
                    }
                } else {
                    binding.titleStreamings.visibility = View.GONE
                }
            } catch (ex: Exception) {
                binding.titleStreamings.visibility = View.GONE
            }
        }
    }

    private fun loadData() {
        activity?.findViewById<Toolbar>(R.id.toolbar_main)?.title = movie.original_title
        val urlPoster = "https://image.tmdb.org/t/p/w154${movie.poster_path}"
        val urlBanner = "https://image.tmdb.org/t/p/w500${movie.backdrop_path}"
        binding.apply {
            imageviewDetail.load(urlPoster)
            bannerDetail.load(urlBanner)
            detailSynopsis.text = movie.overview
            detailYear.text = movie.release_date?.year()
            detailDate.text = movie.release_date?.toDate()
            ratingBar.rating = (movie.vote_average / 2).toFloat()
            if (movie.favorite) {
                detailFavorite.setImageDrawable(heartOk)
            } else {
                detailFavorite.setImageDrawable(heartAdd)
            }
            if (movie.media_type == "tv") {
                detailTitle.text = movie.name
            } else {
                detailTitle.text = movie.title
            }
        }
    }
}