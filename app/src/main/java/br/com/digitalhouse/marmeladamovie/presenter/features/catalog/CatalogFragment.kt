package br.com.digitalhouse.marmeladamovie.presenter.features.catalog

import android.os.Bundle
import android.view.View
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import br.com.digitalhouse.marmeladamovie.R

class CatalogFragment : Fragment(R.layout.fragment_catalog){

    private lateinit var movies : CardView
    private lateinit var series : CardView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movies = view.findViewById(R.id.catalogo_filmes)
        series = view.findViewById(R.id.catalogo_series)


    }
}