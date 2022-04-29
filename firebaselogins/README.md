# FirebaseLogins


##GettingStarted
## This AAR file contains Three Firebase authentication classes.
## Firebase Google Authentication
## Firebase Phone Authentication
## Firebase Facebook Authentication

## Initialization of both classes are can be done in application class.

## Declaration of Variable
```
    lateinit var firebaseGoogleAuth: FirebaseGoogleAuth
    lateinit var firebasePhoneAuth: FirebasePhoneAuth
    lateinit var firebaseFacebookAuth: FirebaseFacebookAuth
```

## In onCreateMethod
```
  firebaseGoogleAuth = FirebaseGoogleAuth(this)
  firebasePhoneAuth = FirebasePhoneAuth(this)
  firebaseFacebookAuth = FirebaseFacebookAuth(this)
```

##Firebase Google Authentication
Google Authentication contain having define below methods.
# connectGoogle(applicationContext:Context)
This Method use for The connect the Google.
Note:- When use This Method Please your firebase web_client_id store in values of string.
       When use define below methods concurrently make sure connectGoogle method Accessible both
# signInGoogle(signInContext: Activity)
This Method use for the Sign in process Of Google Pass the reference of your current Activity
like this
```
    firebaseGoogleAuth?.signInGoogle(this)
```
# signOutGoogle()
This Method use for the process of SignOut of Google
# onActivityResult(requestCode: Int, data: Intent?,googleListener: GoogleListener)
This Method generally pass on it onActivity result in Activity Class & Also Implement the Listener Of This Class
like this
```
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        firebaseGoogleAuth?.onActivityResult(requestCode, data,this)
    }
```
# googleSignOrNot():Boolean
This Method use for check the user will already sign in or not

##Listener Of Google Firebase Authentication
FirebaseGoogleAuth.GoogleListener

Implement this Listener Get Four Listener Methods
# onGoogleSuccess(user: FirebaseUser?)
This method return which one user sign in Google. & get FirebaseUser Data like email,DisplayName,Others. 
# onGoogleLogout(user: FirebaseUser?)
This method return which one user sign out Google. & also get the which user logout get the details.
# onGoogleFailure(exception: Exception?)
This method return showing error when process of google fail.
# onGoogleCancel()
This method return when prompt of the Google & Accidentally or purposely cancel show the msg  

*-*-*-*-*-*-*-*

##Firebase Phone Authentication
Phone Authentication contain having below methods.
# phoneLogin(phoneNumber: String):Boolean
This method use for the get the number through process of Phone Authentication & get the verification code in your device.
Also When user get empty number pass in this show the message of ```Enter The Number``` & 
Along the pass number with country code ```+9194xxxxxxxx```   
# resendLogin(number: String):Boolean
This method use for the number through process of get the second time verification code if you don't get the first time verification code in your device.
Same as PhoneLogin : Also When user get empty number pass in this show the message of ```Enter The Number``` &
Along the pass number with country code ```+9194xxxxxxxx```
# checkVerification(number: String,phoneListener: PhoneListener):Boolean
This method use for after verify number received OTP pass in this method & Verify the Authentication Process.
when user with out pass otp initialize this process get the message ```enter the otp```

##Listener Of Phone Firebase Authentication
FirebasePhoneAuth.PhoneListener

Implement this Listener Get Two Listener Methods
# onAuthSuccess()
This method return when user phone authentication successfully authorized.
# onAuthFailure()
This method return user phone authentication process fail to authorized exception will be throw.

*-*-*-*-*-*-*-*

##Firebase Facebook Authentication
Facebook Authentication contain having below methods.
# facebookLogin(loginContext: Activity, facebookListener: FacebookListener?)
This method use for initialize the whole process of facebook authentication. 
pass reference of your current activity & implement the Listener of this class & get the callbacks process
like this
```
    firebaseFacebookAuth?.facebookLogin(this,this)
```
# handleFacebookRemoveToken(facebookListener: FacebookListener?)
This method use for the Sign out of the facebook authentication process & Implement this listener.

# alreadySignOrNot(): Boolean
This method use for check the user of already sign in or not in facebook authentication.

# onActivityResultFB(requestCode: Int, resultCode: Int, data: Intent?)
This Method generally pass on it onActivity result in your Activity Class 
like this
```
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        firebaseFacebookAuth?.onActivityResultFB(requestCode, resultCode, data)
    }
```

##Listener Of Facebook Firebase Authentication
FirebaseFacebookAuth.FacebookListener

# onFacebookSuccess(firebaseUser: FirebaseUser?)
This method returns success call when user successfully authorized & get the details of User.
# onFacebookCancel()
This method returns show the message when process Accidentally or Purposely cancel by User.
# onFacebookError(error: FacebookException)
This method returns show the error of when process failure
# onFacebookLogOut(user: FirebaseUser?)
This method returns when user logout firebase authentication process. 





