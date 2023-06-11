package com.example.movieapp.data.network

import com.example.movieapp.util.Constants
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ServiceGenerator {
    const val BASE_URL = "https://imdb-top-100-movies1.p.rapidapi.com/"
    fun create(): MovieAppApi {
        val retrofit: Retrofit =  Retrofit.Builder().baseUrl(BASE_URL)
            .client(getHttpClient())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
        return retrofit.create(MovieAppApi::class.java)
    }

    private fun getHttpClient(): OkHttpClient {
        val headers = mutableMapOf<String,String>().apply {
            put("X-RapidAPI-Key","97389f396cmsh0fc0daa0674b65ap171d17jsnaf505dbe6a79")
            put("X-RapidAPI-Host","imdb-top-100-movies1.p.rapidapi.com")
        }
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
        httpClient.connectTimeout(Constants.HTTP_CONNECTION_TIMEOUT, TimeUnit.SECONDS)
        httpClient.readTimeout(Constants.HTTP_READ_TIMEOUT, TimeUnit.SECONDS)
        httpClient.writeTimeout(Constants.HTTP_WRITE_TIMEOUT, TimeUnit.SECONDS)
        httpClient.addInterceptor(logging)
        if (headers.isNotEmpty()) {
            httpClient.addInterceptor(Interceptor { chain ->
                val original = chain.request()

                val request = original.newBuilder()
                for ((key, value) in headers) {
                    request.header(key, value)
                }
                request.method(original.method, original.body)
                chain.proceed(request.build())
            })
        }
        return httpClient.build()
    }
}