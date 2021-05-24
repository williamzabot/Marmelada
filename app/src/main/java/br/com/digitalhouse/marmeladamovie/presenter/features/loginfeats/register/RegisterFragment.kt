package br.com.digitalhouse.marmeladamovie.presenter.features.loginfeats.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import br.com.digitalhouse.marmeladamovie.R
import com.google.firebase.auth.FirebaseAuth

class RegisterFragment : Fragment() {

    private val auth = FirebaseAuth.getInstance()
    val btn = view?.findViewById<Button>(R.id.register_button)
    val email = view?.findViewById<com.google.android.material.textfield.TextInputLayout>(R.id.register_email)
    val password = view?.findViewById<com.google.android.material.textfield.TextInputLayout>(R.id.register_passowrd)
    val duration = Toast.LENGTH_LONG

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
        btnClickListener()
    }

    fun btnClickListener() {

        btn?.setOnClickListener {

            if (email != null && password != null) {
                auth.createUserWithEmailAndPassword(email.toString(), password.toString())
                    .addOnSuccessListener {
                        val toast = Toast.makeText(requireContext(), "Você foi registrado com sucesso!", duration)
                        toast.show()
                    }


            }

        }

    }
}