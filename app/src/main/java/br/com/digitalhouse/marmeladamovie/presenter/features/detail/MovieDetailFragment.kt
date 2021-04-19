package br.com.digitalhouse.marmeladamovie.presenter.features.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import br.com.digitalhouse.marmeladamovie.R
import br.com.digitalhouse.marmeladamovie.databinding.FragmentDetailBinding
import br.com.digitalhouse.marmeladamovie.presenter.extensions.load
import br.com.digitalhouse.marmeladamovie.presenter.extensions.toDate
import br.com.digitalhouse.marmeladamovie.presenter.extensions.year

class MovieDetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<MovieDetailFragmentArgs>()

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
        activity?.findViewById<Toolbar>(R.id.toolbar_main)?.title = args.movie.original_title
        val urlPoster = "https://image.tmdb.org/t/p/w154${args.movie.poster_path}"
        val urlBanner = "https://image.tmdb.org/t/p/w500${args.movie.backdrop_path}"
        binding.apply {
            imageviewDetail.load(urlPoster)
            bannerDetail.load(urlBanner)
            detailTitle.text = args.movie.title
            detailSynopsis.text = args.movie.overview
            detailYear.text = args.movie.release_date.year()
            detailDate.text = args.movie.release_date.toDate()
            ratingBar.rating = (args.movie.vote_average / 2).toFloat()
        }

    }
}