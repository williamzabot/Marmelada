package br.com.digitalhouse.marmeladamovie.presenter.features.loginfeats.login

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.digitalhouse.marmeladamovie.R
import br.com.digitalhouse.marmeladamovie.presenter.features.loginfeats.LoginActivity
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth


class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var callBackManager : CallbackManager
    private lateinit var activity : LoginActivity

    private val loginMenager = LoginManager.getInstance()

    private val buttonFacebook by lazy { view?.findViewById<ImageView>(R.id.buttonFacebook) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.txt_register).setOnClickListener {
            val action = LoginFragmentDirections.loginToRegister()
            findNavController().navigate(action)

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
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(activity, "Sucess", Toast.LENGTH_LONG).show()
                    val user = firebaseAuth.currentUser
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

    override fun onStart() {
        super.onStart()
        val currentUser = firebaseAuth.currentUser
        firebaseAuth.updateCurrentUser(currentUser)
    }

}