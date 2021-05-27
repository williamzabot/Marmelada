package br.com.digitalhouse.marmeladamovie.presenter.features.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import br.com.digitalhouse.marmeladamovie.R
import br.com.digitalhouse.marmeladamovie.presenter.features.loginfeats.LoginActivity
import br.com.digitalhouse.marmeladamovie.presenter.model.User
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val auth = FirebaseAuth.getInstance()
    private val realtime = FirebaseDatabase.getInstance().getReference("users/${auth.currentUser.email}")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val nomeTv = view.findViewById<TextView>(R.id.txvNome)
        val emailTv = view.findViewById<TextView>(R.id.txvEmail)
        val profileImg = view.findViewById<ImageView>(R.id.imageView)

        realtime.get().addOnSuccessListener {
            val user = it.getValue(User::class.java)
            nomeTv.text = user?.name
            emailTv.text = user?.email
        }.addOnFailureListener {
            Glide.with(requireContext()).load(auth.currentUser?.photoUrl).into(profileImg)
            nomeTv.text = auth.currentUser?.displayName
            emailTv.text = auth.currentUser?.email
        }

        view.findViewById<Button>(R.id.signOut).setOnClickListener {
            auth.signOut()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }

}