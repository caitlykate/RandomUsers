package com.caitlykate.randomusersdagger2

import android.os.Bundle
import android.util.Log
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.caitlykate.randomusersdagger2.adapter.RandomUserAdapter
import com.caitlykate.randomusersdagger2.interfaces.RandomUsersApi
import com.caitlykate.randomusersdagger2.model.ApiResponse
import com.caitlykate.randomusersdagger2.model.Result
import com.google.gson.GsonBuilder
import com.jakewharton.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import timber.log.Timber.DebugTree
import java.io.File
import java.util.concurrent.TimeUnit
/*
Retrofit — для вызовов API
GsonBuilder и Gson — для работы с JSON
HttpLoggingInterceptor  — для логирования сетевых операций
OkHttpClient — клиент для Retrofit
Picasso — работа с изображениями в Adapter
*/
class MainActivity : AppCompatActivity() {

    lateinit var retrofit: Retrofit
    lateinit var recyclerView: RecyclerView
    lateinit var picasso: Picasso
    lateinit var mAdapter: RandomUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        beforeDagger2();
        populateUsers()
    }

    private fun init() {
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun beforeDagger2() {
        val gsonBuilder = GsonBuilder()
        val gson = gsonBuilder.create()
        Timber.plant(DebugTree())
        val cacheFile = File(this.cacheDir, "HttpCache")
        cacheFile.mkdirs()
        val cache = Cache(cacheFile, 10 * 1000 * 1000) //10 MB
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient
            .Builder()
            .cache(cache)
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .build()
        val okHttpDownloader = OkHttp3Downloader(okHttpClient)
        picasso = Picasso.Builder(this).downloader(okHttpDownloader).build()
        mAdapter = RandomUserAdapter(picasso)
        recyclerView.adapter = mAdapter
        retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://randomuser.me/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }


    private fun populateUsers() {
        val randomUsersCall = retrofit.create(RandomUsersApi::class.java).getRandomUsers(30)
        Log.d("MyLog", "0")
        //val randomUsersCall = getRandomUserService().getRandomUsers(10)
        randomUsersCall!!.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, @NonNull response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    Log.d("MyLog", "1")
                    response.body()?.let {
                        mAdapter.updateAdapter(it.results as List<Result>)
                        Log.d("MyLog", "${it.results}")
                    }
                    //recyclerView.adapter = mAdapter
                } else Log.d("MyLog", "2")
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Log.d("MyLog", "3")
                Log.d("MyLog", "$t")
                //Log.d("MyLog", "${response.body()?.results}")
                Timber.i(t.message)
            }
        })
    }

}