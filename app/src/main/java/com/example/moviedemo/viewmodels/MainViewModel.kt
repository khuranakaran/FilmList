package com.example.moviedemo.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviedemo.models.MovieList
import com.example.moviedemo.repository.MainRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel constructor(private val repository: MainRepository)  : ViewModel() {
    
    val movieList = MutableLiveData<MovieList>()
    val errorMessage = MutableLiveData<String>()
    
    fun getAllMovies() {
        val response = repository.getAllMovies()
        response.enqueue(object : Callback<MovieList> {
            override fun onResponse(call: Call<MovieList>, response: Response<MovieList>) {
                movieList.postValue(response.body())
            }
            override fun onFailure(call: Call<MovieList>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }
}