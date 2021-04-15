package br.com.digitalhouse.marmeladamovie.presenter.features.loginfeats.login

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.digitalhouse.marmeladamovie.R

class LoginFragment : Fragment(R.layout.fragment_login) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.txt_register).setOnClickListener {
            val action = LoginFragmentDirections.loginToRegister()
            findNavController().navigate(action)

        }
    }
}