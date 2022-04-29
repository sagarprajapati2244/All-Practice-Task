# FirebaseNotification



## Getting started

This AAR file contain One of the Firebase Notification class.


## Initialization of both classes are can be done in application class.

## Declaration of Variable
```
    lateinit var firebaseNotification: FirebaseNotification
```

You can pass the channel name of yours in this class
```
 const val channelName = "com.example.firebaselogin"
```

## In onCreateMethod
```
   firebaseNotification = FirebaseNotification(channelName)
```

# Firebase Messaging Utils

Firebase Notification contain some of the methods define below.
# fun simpleNotification(context: Context,title: String,message: String,notificationId: Int,pendingIntent: PendingIntent,drawableImage: Int)
This method use for pass the simple notification from user inputs.
like this
```
firebaseNotification?.simpleNotification(this,notifyTitle,notifyMessage,100,pendingIntent,R.drawable.img)
```

# fun showImageNotification(context: Context,title: String,message: String,notificationId: Int,pendingIntent: PendingIntent,image: Int)
This method use for pass the notification of collapse and expand large and small image icon of your drawable image
Also same as simpleNotification

# fun showImageUrlNotification(context: Context,title: String,message: String,notificationId: Int,pendingIntent: PendingIntent,image: String,drawableImage:Int)
This method use for pass the notification with your title , messages and also pass the your url of image to shown large and small .


## Get Token
# fun getToken()
This method use for the get your device token

## Get Notification direct from firebase console
This method generally pass in your firebase messaging service
# fun generateNotification(context: Context,title: String,body:String,image: Uri?) 
```
 override fun onMessageReceived(p0: RemoteMessage) {
  firebaseNotification?.generateNotification(this, p0.notification!!.title!!,p0.notification!!.body!!,p0.notification!!.imageUrl)
 }
```
Get The Notification.
 