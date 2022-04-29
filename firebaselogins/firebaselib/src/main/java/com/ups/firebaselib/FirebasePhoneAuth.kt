package com.ups.firebaselib

import android.app.Activity
import android.content.Context
import android.widget.Toast
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit

class FirebasePhoneAuth(val context: Context) {
    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private var activity: Activity = Activity()
    private var phoneListener:PhoneListener?=null



    fun phoneLogin(phoneNumber: String,callBacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks):Boolean {

        return if (phoneNumber.isNotEmpty()) {
            sendVerificationCode(phoneNumber,callBacks)
            true
        } else {
            Toast.makeText(context, "Enter Mobile Number", Toast.LENGTH_SHORT).show()
            false
        }
    }


    fun checkVerification(number: String,storedVerificationId:String,phoneListener: PhoneListener):Boolean {
        this.phoneListener = phoneListener
        return if (number.isNotEmpty()) {
            val credential: PhoneAuthCredential = PhoneAuthProvider.getCredential(
                storedVerificationId, number)
            signInWithPhoneAuthCredential(credential,phoneListener)
            true
        } else {
            Toast.makeText(context, "Enter OTP", Toast.LENGTH_SHORT).show()
            false
        }
    }

    fun signInWithPhoneAuthCredential(
        credential: PhoneAuthCredential,
        phoneListener: PhoneListener?
    ) {
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    phoneListener?.onAuthSuccess()
                } else {
                    if (it.exception is FirebaseAuthInvalidCredentialsException) {
                        phoneListener?.onAuthFailure()
                    } else {
                        phoneListener?.onAuthFailure()
                    }
                }
            }
    }


    private fun sendVerificationCode(
        number: String,
        callBacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    ) {
        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(number) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(activity) // Activity (for callback binding)
            .setCallbacks(callBacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun resendVerificationCode(phone: String,resendToken:PhoneAuthProvider.ForceResendingToken,callBacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks) {
        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(phone) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(activity) // Activity (for callback binding)
            .setCallbacks(callBacks) // OnVerificationStateChangedCallbacks
            .setForceResendingToken(resendToken)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    interface PhoneListener{
        fun onAuthSuccess()
        fun onAuthFailure()
    }
}