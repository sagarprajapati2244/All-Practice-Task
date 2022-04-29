package com.ups.firebaselib

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class FirebaseFacebookAuth(private val context: Context) {
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val callbackManager = CallbackManager.Factory.create()
    private var facebookListener: FacebookListener? = null

    fun facebookLogin(loginContext: Activity, facebookListener: FacebookListener?) {
        this.facebookListener = facebookListener
        LoginManager.getInstance().logInWithReadPermissions(
            loginContext,
            listOf("public_profile", "email")
        )
        LoginManager.getInstance()
            .registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                override fun onCancel() {
                    Log.e("FacebookError", "Facebook Cancel")
                    facebookListener?.onFacebookCancel()
                }

                override fun onError(error: FacebookException) {
                    facebookListener!!.onFacebookError(error)
                    Log.e("FacebookError", "Facebook Error${error.message.toString()}")
                }

                override fun onSuccess(result: LoginResult) {
                    handleFacebookAccessToken(result.accessToken)
                }

            })
    }

    fun handleFacebookRemoveToken(facebookListener: FacebookListener?) {
        this.facebookListener = facebookListener
        val user = FirebaseAuth.getInstance().currentUser
        if (AccessToken.getCurrentAccessToken() != null) {
            GraphRequest(AccessToken.getCurrentAccessToken(),
                "/me/permissions/",
                null,
                HttpMethod.DELETE,
                {
                    AccessToken.setCurrentAccessToken(null)
                    LoginManager.getInstance().logOut()

                }).executeAsync()

            facebookListener!!.onFacebookLogOut(user)
        }
    }

    private fun handleFacebookAccessToken(accessToken: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(accessToken.token)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.e("FacebookAuth", "signInWithCredential:success")
                    val user = firebaseAuth.currentUser
                    facebookListener!!.onFacebookSuccess(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.e("FacebookAuth", "signInWithCredential:failure", it.exception)
                    Toast.makeText(
                        context, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    fun alreadySignOrNot(): Boolean {
        val user = FirebaseAuth.getInstance().currentUser
        return if (user!=null)
        {
            Log.e("userLogin","--User Logged In--$user")
            true
        }
        else
        {
            Log.e("userLogin","--User Not Logged In--$user")
            false
        }
    }


    fun onActivityResultFB(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    interface FacebookListener {
        fun onFacebookSuccess(firebaseUser: FirebaseUser?)
        fun onFacebookCancel()
        fun onFacebookError(error: FacebookException)
        fun onFacebookLogOut(user: FirebaseUser?)
    }
}