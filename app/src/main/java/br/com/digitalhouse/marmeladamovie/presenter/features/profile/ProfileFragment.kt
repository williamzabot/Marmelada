package br.com.digitalhouse.marmeladamovie.presenter.features.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import br.com.digitalhouse.marmeladamovie.R
import br.com.digitalhouse.marmeladamovie.presenter.features.loginfeats.login.LoginFragment
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()
        val currentUser = firebaseAuth.currentUser

        val nomeTv = view.findViewById<TextView>(R.id.txvNome)
        val emailTv = view.findViewById<TextView>(R.id.txvEmail)
        val profileImg = view.findViewById<ImageView>(R.id.imageView)

        view.findViewById<Button>(R.id.signOut).setOnClickListener {
            firebaseAuth.signOut()
            activity?.finish()
        }

        nomeTv.text = currentUser?.displayName
        emailTv.text = currentUser?.email

        activity?.let { Glide.with(it).load(currentUser?.photoUrl).into(profileImg) }
    }

}