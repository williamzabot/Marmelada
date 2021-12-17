package br.com.digitalhouse.marmeladamovie.presenter.features.favorite

import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import br.com.digitalhouse.marmeladamovie.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment(R.layout.fragment_favorite) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewNoFavorites: ConstraintLayout
    private val navController by lazy { findNavController() }
    private val viewModel by viewModels<FavoriteViewModel>()
    private val adapter by lazy {
        FavoriteAdapter { favorite ->
            val action = FavoriteFragmentDirections.actionFavoriteToDetail(favorite = favorite)
            navController.navigate(action)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerView_favorites)
        viewNoFavorites = view.findViewById(R.id.view_nofavorites)
        recyclerView.adapter = adapter
        observeEvents()
        viewModel.getFavorites()
    }

    private fun observeEvents() {
        viewModel.favorites.observe(viewLifecycleOwner) { favorites ->
            if (favorites.isNotEmpty()) {
                adapter.favorites = favorites
                viewNoFavorites.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
            }
        }
    }

}