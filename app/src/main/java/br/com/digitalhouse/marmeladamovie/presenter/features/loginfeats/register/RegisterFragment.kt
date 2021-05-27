package br.com.digitalhouse.marmeladamovie.presenter.features.loginfeats.register

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import br.com.digitalhouse.marmeladamovie.R
import br.com.digitalhouse.marmeladamovie.presenter.extensions.toValidId
import br.com.digitalhouse.marmeladamovie.presenter.model.User
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegisterFragment : Fragment(R.layout.fragment_register) {

    private val auth = FirebaseAuth.getInstance()
    private val duration = Toast.LENGTH_LONG
    private val realtime = FirebaseDatabase.getInstance().reference

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btn = view.findViewById<Button>(R.id.register_button)
        val emailCheck = view.findViewById<TextInputEditText>(R.id.email_register)
        val passwordCheck = view.findViewById<TextInputEditText>(R.id.passowrd_register)
        val nameCheck = view.findViewById<TextInputEditText>(R.id.name_register)

        btn?.setOnClickListener {
            if (emailCheck.text != null && emailCheck.text!!.isNotEmpty() &&
                passwordCheck.text != null && passwordCheck.text!!.isNotBlank() &&
                nameCheck.text != null && nameCheck.text!!.isNotBlank()) {
                auth.createUserWithEmailAndPassword(emailCheck.text.toString(), passwordCheck.text.toString())
                    .addOnSuccessListener {
                        val user = User(nameCheck.text.toString(), emailCheck.text.toString())
                        realtime.child("users").child(emailCheck.text.toString().toValidId()).setValue(user)
                        Toast.makeText(
                            requireContext(),
                            "Você foi registrado com sucesso!",
                            duration
                        ).show()
                        emailCheck.text?.clear()
                        passwordCheck.text?.clear()
                        nameCheck.text?.clear()
                    }.addOnFailureListener {
                        val msg = answers(it.message.toString())
                        Toast.makeText(requireContext(), msg, duration).show()
                        Log.e("ERRO LOGIN", it.message.toString())
                    }
            } else {
                Toast.makeText(requireContext(), "Todos os campos devem ser preenchidos!", duration)
                    .show()
            }
        }
    }


    private fun answers(answer: String): String {

        return when {
            answer.contains("least 6 characters") -> "Não é permitido senha com menos de 6 digitos"
            answer.contains("address is badly") -> "Email inválido!"
            answer.contains("interrupted connection") -> "Sem conexão com a Internet"
            answer.contains("address is already") -> "Email já cadastrado"
            else -> "ERRO!"
        }

    }
}