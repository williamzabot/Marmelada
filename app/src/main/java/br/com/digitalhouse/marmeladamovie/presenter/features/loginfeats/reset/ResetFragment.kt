package br.com.digitalhouse.marmeladamovie.presenter.features.loginfeats.reset

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import br.com.digitalhouse.marmeladamovie.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class ResetFragment : Fragment(R.layout.fragment_reset) {

    private val auth = FirebaseAuth.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val reset = view.findViewById<TextInputEditText>(R.id.reset_email)
        val button = view.findViewById<Button>(R.id.button_reset)

        button.setOnClickListener {
            if (reset.text != null && reset.text!!.isNotBlank()) {
                auth.sendPasswordResetEmail(reset.text.toString())
                    .addOnSuccessListener {
                        reset.text?.clear()
                        Toast.makeText(requireContext(), "O link foi enviado para seu email", Toast.LENGTH_LONG).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                    }
            }
        }
    }

}