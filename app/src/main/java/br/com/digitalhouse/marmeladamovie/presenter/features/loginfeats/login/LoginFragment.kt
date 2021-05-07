package br.com.digitalhouse.marmeladamovie.presenter.features.loginfeats.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.digitalhouse.marmeladamovie.R
import br.com.digitalhouse.marmeladamovie.presenter.features.home.HomeActivity
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var callBackManager : CallbackManager
    private lateinit var googleSignInClient: GoogleSignInClient

    private val loginMenager = LoginManager.getInstance()

    private val buttonFacebook by lazy { view?.findViewById<ImageView>(R.id.buttonFacebook) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val googleSignOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = activity?.let { GoogleSignIn.getClient(it, googleSignOptions) }!!
        firebaseAuth = FirebaseAuth.getInstance()

        view.findViewById<ImageView>(R.id.icon_google).setOnClickListener {
            sign()
        }

        view.findViewById<ImageView>(R.id.buttonFacebook).setOnClickListener {
            signInFacebook()
        }

        view.findViewById<TextView>(R.id.txt_register).setOnClickListener {
            val action = LoginFragmentDirections.loginToRegister()
            findNavController().navigate(action)
        }
    }

    override fun onStart() {
        super.onStart()
//        val currentUser = firebaseAuth.currentUser
        firebaseAuth.currentUser?.let {
            firebaseAuth.updateCurrentUser(it)
        }
    }

    private fun sign(){
        val sigInIntent = googleSignInClient.signInIntent
        startActivityForResult(sigInIntent, 200)
    }

    private fun signInFacebook(){
        loginFacebook()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

       callBackManager.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == 200) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception = task.exception
            if (task.isSuccessful){
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)!!
                    Log.d("googleSign", "firebaseAuthWithGoogle:" + account.idToken)
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
                    Log.w("googleSign", "Google sign in failed", e)
                }
            }else{
                Log.w("googleSign", exception.toString())
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        activity?.let {
            firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(it) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("GoogleSign", "signInWithCredential:success")
                        val intent = Intent(activity, HomeActivity::class.java)
                        startActivity(intent)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("GoogleSign", "signInWithCredential:failure", task.exception)
                    }
                }
        }

        firebaseAuth = FirebaseAuth.getInstance()
        callBackManager = CallbackManager.Factory.create()

        buttonFacebook?.setOnClickListener {
            loginFacebook()
        }
    }

    private fun loginFacebook() {
        loginMenager.logInWithReadPermissions(this, arrayListOf("email", "public_profile"))
        loginMenager.registerCallback(callBackManager, object :
        FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult?) {
                if (loginResult != null) {
                    handleFacebookAcessToken(loginResult.accessToken)
                }
            }
            override fun onCancel() {
                Toast.makeText(activity,"Processo cancelado", Toast.LENGTH_LONG).show()
            }

            override fun onError(error: FacebookException) {
                Toast.makeText(activity,"Dados incorretos", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun handleFacebookAcessToken(token : AccessToken) {
        val credential = FacebookAuthProvider.getCredential(token.token)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(activity, "Sucess", Toast.LENGTH_LONG).show()
                    val user = firebaseAuth.currentUser
                    val intent = Intent(activity, HomeActivity::class.java)
                    startActivity(intent)
                    firebaseAuth.updateCurrentUser(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("facebook", "signInWithCredential:failure", task.exception)
                    Toast.makeText(
                        activity, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}
