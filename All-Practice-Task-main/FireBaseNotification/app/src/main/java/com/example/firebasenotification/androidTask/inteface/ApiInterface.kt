package com.example.firebasenotification.androidTask.inteface

import okhttp3.ResponseBody
import retrofit2.http.GET

interface ApiInterface {


    @GET("search?term=jack+johnson&limit=50")
    fun Data():retrofit2.Call<ResponseBody>
}