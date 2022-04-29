package com.example.firebaselogin

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.firebaselogin.databinding.ActivityMainBinding
import com.facebook.FacebookException
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.ups.firebaselib.FirebaseFacebookAuth.FacebookListener
import com.ups.firebaselib.FirebaseGoogleAuth.GoogleListener
import com.ups.firebaselib.FirebasePhoneAuth.PhoneListener
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

@SuppressLint("UnspecifiedImmutableFlag")
class MainActivity : AppCompatActivity(), FacebookListener, GoogleListener,
    PhoneListener {
//    private var firebaseGoogleAuth: FirebaseGoogleAuth? = null
//    private var firebasePhoneAuth: FirebasePhoneAuth? = null
//    private var firebaseFacebookAuth: FirebaseFacebookAuth? = null

    private var resultGoogle = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        (application as MyApplication).firebaseGoogleAuth.onActivityResult(result.resultCode,result.data,this)
    }
var resendToken:PhoneAuthProvider.ForceResendingToken?=null
var storedVerificationId:String=""
    private val callBack=object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            (application as MyApplication).firebasePhoneAuth.signInWithPhoneAuthCredential(credential,this@MainActivity)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            Toast.makeText(this@MainActivity, "${e.message}", Toast.LENGTH_LONG).show()
            if (e is FirebaseAuthInvalidCredentialsException) {
                Log.e("Verification", "--Invalid--")
            } else {
                Log.e("Verification", "--sms limit exceeded")
            }
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            binding.oneNumber.visibility = View.VISIBLE
            binding.resendVerification.visibility = View.VISIBLE
            binding.loginBtn.setText(R.string.login_verify)
            Log.e("TAG", "onCodeSent:$verificationId")
            storedVerificationId = verificationId
            resendToken = token
        }
    }

    private lateinit var binding: ActivityMainBinding
    private var number = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        printKeyHash()
        if (isOnline()) {
            checkGoogleSignOrNot()
            checkFacebookSignOrNot()
        } else {
            Toast.makeText(this, "Please check your Internet connection!!", Toast.LENGTH_SHORT)
                .show()
        }

        //Google Firebase Authentication
        googleSignIn()

        //Phone Firebase Authentication
        phoneSignIn()

        //Facebook Firebase Authentication
        facebookSignIn()

    }
    private fun checkFacebookSignOrNot() {
        if ((application as MyApplication).firebaseFacebookAuth.alreadySignOrNot())
        {
            binding.btnFSign.setText(R.string.facebook_sign_out)
            Toast.makeText(this, "AlReady Signed In Facebook by User", Toast.LENGTH_SHORT).show()
        }
        else
        {
            binding.btnFSign.setText(R.string.facebook_sign_in)
            Toast.makeText(this, "No One Sign In Facebook by User", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkGoogleSignOrNot() {
        if ((application as MyApplication).firebaseGoogleAuth.googleSignOrNot()) {
            Toast.makeText(this, "AlReady Signed In Google by User", Toast.LENGTH_SHORT)
                .show()
            binding.btnSign.setText(R.string.g_sign_out)
        } else {
            binding.btnSign.setText(R.string.g_sign_in)
            Toast.makeText(this, "No One Sign In Google by User", Toast.LENGTH_SHORT).show()
        }
    }

    private fun facebookSignIn() {
        binding.btnFSign.setOnClickListener {
            if (isOnline()) {
                if (binding.btnFSign.text.toString() == this.getString(R.string.facebook_sign_in)) {
                    (application as MyApplication).firebaseFacebookAuth.facebookLogin(this,this)
                } else {
                    (application as MyApplication).firebaseFacebookAuth.handleFacebookRemoveToken(this)
                }
            } else {
                Toast.makeText(this, "Please Check Your Internet Connection!!", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun phoneSignIn() {
        binding.loginBtn.setOnClickListener {

            if (isOnline()) {
                number = binding.phoneNumber.text.toString().trim()
                if (binding.loginBtn.text.toString() == this.getString(R.string.login)) {
                    (application as MyApplication).firebasePhoneAuth.phoneLogin(number, callBack)
                } else {
                        (application as MyApplication).firebasePhoneAuth.checkVerification(binding.oneNumber.text.toString(),storedVerificationId,this)
                }
            } else {
                Toast.makeText(this, "Please Check Your Internet Connection!!", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        binding.resendVerification.setOnClickListener {
            number = binding.phoneNumber.text.toString().trim()
            resendToken?.let { it1 ->
                (application as MyApplication).firebasePhoneAuth.resendVerificationCode(number,
                    it1,callBack)
            }
        }
    }

    private fun googleSignIn() {
        binding.btnSign.setOnClickListener {
            if (isOnline()) {

                if (binding.btnSign.text.toString() == this.getString(R.string.g_sign_in)) {
//                  (application as MyApplication).firebaseGoogleAuth.connectGoogle(applicationContext)
                    (application as MyApplication).firebaseGoogleAuth.signInGoogle(this,resultGoogle)
                } else {
                    (application as MyApplication).firebaseGoogleAuth.signOutGoogle(this)
                }

            } else {
                Toast.makeText(this, "Please Check Your Internet Connection!!", Toast.LENGTH_SHORT)
                    .show()
            }

        }
    }

    private fun printKeyHash() {
        try {
            val info = packageManager.getPackageInfo(
                "com.example.firebaselogin",
                PackageManager.GET_SIGNATURES
            )
            for (i in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(i.toByteArray())
                Log.e("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            }
        } catch (e: PackageManager.NameNotFoundException) {
            Log.e("error", e.message.toString())
        } catch (e: NoSuchAlgorithmException) {
            Log.e("error", e.message.toString())
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        (application as MyApplication).firebaseFacebookAuth.onActivityResultFB(requestCode, resultCode, data)
    }


    private fun isOnline(): Boolean {
        val connectivityManager =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            } else {
                TODO("VERSION.SDK_INT < M")
            }
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }

    //Facebook Listener
    override fun onFacebookSuccess(firebaseUser: FirebaseUser?) {
        binding.btnFSign.text = "${firebaseUser?.email}"
        Toast.makeText(this, "Successfully Facebook Sign by $firebaseUser", Toast.LENGTH_SHORT).show()
    }

    override fun onFacebookCancel() {
        Toast.makeText(this, "Cancel by User", Toast.LENGTH_SHORT).show()
    }

    override fun onFacebookError(error: FacebookException) {
        Toast.makeText(this, "${error.message}", Toast.LENGTH_SHORT).show()
    }

    override fun onFacebookLogOut(user: FirebaseUser?) {
        binding.btnFSign.setText(R.string.facebook_sign_in)
        Toast.makeText(this, "Facebook Logout by This ${user?.email}", Toast.LENGTH_SHORT).show()
    }

    //Google Listener
    override fun onGoogleSuccess(user: FirebaseUser?) {
        binding.btnSign.text = "${user?.email}"
        Toast.makeText(this, "Successfully Google Sign by $user", Toast.LENGTH_SHORT).show()
    }

    override fun onGoogleLogout(user: FirebaseUser?) {
        binding.btnSign.setText(R.string.g_sign_in)
        Toast.makeText(this, "Google Logout by This ${user?.email}", Toast.LENGTH_SHORT).show()

    }

    override fun onGoogleFailure(exception: Exception?) {
        Toast.makeText(this, "${exception?.message}", Toast.LENGTH_SHORT).show()
    }

    override fun onGoogleCancel() {
        Toast.makeText(this, "Cancel by User", Toast.LENGTH_SHORT).show()
    }

    //Phone Listener
    override fun onAuthSuccess() {
        binding.oneNumber.visibility = View.GONE
        binding.loginBtn.setText(R.string.login)
        binding.resendVerification.visibility = View.GONE
        Toast.makeText(this, "Phone Authentication Successful", Toast.LENGTH_SHORT).show()
    }

    override fun onAuthFailure() {
        binding.oneNumber.error = "Please Enter Valid Otp!!"
        Toast.makeText(this, "Phone Authentication UnSuccessful", Toast.LENGTH_SHORT).show()
    }
}