package br.com.digitalhouse.marmeladamovie.presenter.features.profile

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import br.com.digitalhouse.marmeladamovie.R
import br.com.digitalhouse.marmeladamovie.presenter.extensions.toValidId
import br.com.digitalhouse.marmeladamovie.presenter.features.loginfeats.LoginActivity
import br.com.digitalhouse.marmeladamovie.presenter.model.User
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val nomeTv = view.findViewById<TextView>(R.id.txvNome)
        val emailTv = view.findViewById<TextView>(R.id.txvEmail)
        val profileImg = view.findViewById<ImageView>(R.id.imageView)
        val realtime =
            FirebaseDatabase.getInstance()
                .getReference("users/${auth.currentUser?.email?.toValidId()}")

        realtime.get().addOnSuccessListener {
            val user = it.getValue(User::class.java)
            if (user != null) {
                nomeTv.text = user.name
                emailTv.text = user.email
            } else {
                userOfGoogleOrFace(profileImg, nomeTv, emailTv)
            }
        }.addOnFailureListener {
            userOfGoogleOrFace(profileImg, nomeTv, emailTv)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.exit_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.exitMenu -> {
                auth.signOut()
                val intent = Intent(requireContext(), LoginActivity::class.java)
                startActivity(intent)
                activity?.finish()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    private fun userOfGoogleOrFace(
        profileImg: ImageView,
        nomeTv: TextView,
        emailTv: TextView
    ) {
        Glide.with(requireContext()).load(auth.currentUser?.photoUrl).into(profileImg)
        nomeTv.text = auth.currentUser?.displayName
        emailTv.text = auth.currentUser?.email
    }

}