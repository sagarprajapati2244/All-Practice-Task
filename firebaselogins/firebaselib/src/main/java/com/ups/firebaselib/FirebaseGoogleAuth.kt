package com.ups.firebaselib

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import java.lang.Exception

class FirebaseGoogleAuth(private val context: Context) {
    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var googleSignInClient: GoogleSignInClient

    companion object {
        const val RC_SIGN_IN = 1
        const val TAG = "FirebaseGoogleAuth"
    }

    private fun connectGoogle(applicationContext: Context) {

        val gsm = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.resources.getString(R.string.web_client_id)) //Kindly Take A String Of Your Firebase Google Authentication Web Client Id In Resource String
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(applicationContext, gsm)


    }

    fun signInGoogle(activity: Activity,resultGoogle: ActivityResultLauncher<Intent>) {
        connectGoogle(activity)
        if(::googleSignInClient.isInitialized) {
            val intent = googleSignInClient.signInIntent
            resultGoogle.launch(intent)
        }
    }

    fun signOutGoogle(googleListener: GoogleListener)
    {
        val user = FirebaseAuth.getInstance().currentUser
        firebaseAuth.signOut()
        if(::googleSignInClient.isInitialized) {
            googleSignInClient.signOut().addOnCompleteListener {
                googleListener.onGoogleLogout(user)
            }
        }
    }

    fun onActivityResult(resultCode: Int, data: Intent?,googleListener: GoogleListener) {
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (resultCode == RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                account.idToken?.let { firebaseAuthWithGoogle(it,googleListener) }
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
                googleListener.onGoogleCancel()
            }
        }
        else
            googleListener.onGoogleCancel()
    }

    fun googleSignOrNot():Boolean
    {
        val user = firebaseAuth.currentUser
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
    private fun firebaseAuthWithGoogle(idToken: String, googleListener: GoogleListener) {
        val authentication = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(authentication)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.d(TAG, "signInWithCredential:success")
                    googleListener.onGoogleSuccess(it.result.user)
                } else {
                    googleListener.onGoogleFailure(it.exception)
                    Log.d(TAG, "signInWithCredential:failure${it.exception}")
                }
            }
            .addOnFailureListener {
                googleListener.onGoogleFailure(it)
            }
    }


    interface GoogleListener{
        fun onGoogleSuccess(user: FirebaseUser?)
        fun onGoogleLogout(user: FirebaseUser?)
        fun onGoogleFailure(exception: Exception?)
        fun onGoogleCancel()
    }


}