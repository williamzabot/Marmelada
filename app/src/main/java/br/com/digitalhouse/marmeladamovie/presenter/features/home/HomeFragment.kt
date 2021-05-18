package br.com.digitalhouse.marmeladamovie.presenter.features.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import br.com.digitalhouse.marmeladamovie.databinding.FragmentHomeBinding
import br.com.digitalhouse.marmeladamovie.presenter.extensions.hideKeyboard
import br.com.digitalhouse.marmeladamovie.presenter.extensions.navigateWithAnimations
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<HomeViewModel>()
    private val navController by lazy { findNavController() }
    private val adapter by lazy {
        MovieAdapter { movie ->
            navController.navigate(HomeFragmentDirections.homeToDetail(movie))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerviewMovies.adapter = adapter
        binding.recyclerviewMovies.requestFocus()
        (activity as AppCompatActivity).hideKeyboard()
        observeEvents()
        binding.edittextSearch.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                navController.navigateWithAnimations(HomeFragmentDirections.homeToSearch())
            }
        }
    }

    private fun observeEvents() {
        viewModel.movies.observe(viewLifecycleOwner, Observer {
            adapter.submitData(lifecycle, it)
        })
    }

}