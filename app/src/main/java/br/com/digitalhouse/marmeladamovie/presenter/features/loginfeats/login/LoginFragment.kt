package br.com.digitalhouse.marmeladamovie.presenter.features.loginfeats.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.digitalhouse.marmeladamovie.R
import br.com.digitalhouse.marmeladamovie.databinding.FragmentLoginBinding
import br.com.digitalhouse.marmeladamovie.presenter.extensions.hideKeyboard
import br.com.digitalhouse.marmeladamovie.presenter.features.home.HomeActivity
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginFragment : Fragment() {

    companion object {
        private const val LOGIN_GOOGLE = "LOGIN GOOGLE"
        private const val FIREBASE_AUTH = "FIREBASE AUTH"
    }

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val callbackManager = CallbackManager.Factory.create()
    private lateinit var googleSignInClient: GoogleSignInClient
    private val loginManager = LoginManager.getInstance()
    private val navController by lazy { findNavController() }
    private var tryLoginFacebook = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).hideKeyboard()

        val googleSignOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), googleSignOptions)

        binding.apply {
            iconGoogle.setOnClickListener {
                googleSignInClient.signOut()
                startActivityForResult(googleSignInClient.signInIntent, 200)
            }

            buttonFacebook.setOnClickListener {
                tryLoginFacebook = true
                loginFacebook()
            }

            txtRegister.setOnClickListener {
                navController.navigate(LoginFragmentDirections.loginToRegister())
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 200) {
            GoogleSignIn.getSignedInAccountFromIntent(data)
                .addOnSuccessListener {
                    val credential = GoogleAuthProvider.getCredential(it.idToken, null)
                    loginFirebaseAuth(credential)
                }.addOnFailureListener {
                    Log.e(LOGIN_GOOGLE, it.message.toString())
                }
        }

        if (tryLoginFacebook) {
            callbackManager.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun loginFacebook() {
        loginManager.logInWithReadPermissions(this, arrayListOf("email", "public_profile"))
        loginManager.registerCallback(callbackManager, object :
            FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult?) {
                loginResult?.let {
                    val credential = FacebookAuthProvider.getCredential(it.accessToken.token)
                    loginFirebaseAuth(credential)
                }
            }

            override fun onCancel() {
            }

            override fun onError(error: FacebookException) {
                Toast.makeText(requireContext(), error.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun loginFirebaseAuth(credential: AuthCredential) {
        firebaseAuth.signInWithCredential(credential)
            .addOnSuccessListener {
                goToHome()
                Toast.makeText(requireContext(), "Success", Toast.LENGTH_LONG).show()
            }.addOnFailureListener {
                Log.e(FIREBASE_AUTH, "failure: ${it.message}")
                Toast.makeText(
                    requireContext(), it.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun goToHome() {
        val intent = Intent(requireContext(), HomeActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }
}
