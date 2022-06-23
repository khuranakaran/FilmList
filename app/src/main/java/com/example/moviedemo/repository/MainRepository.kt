package com.example.moviedemo.repository

import com.example.moviedemo.retrofit.RetrofitService

class MainRepository constructor(private val retrofitService: RetrofitService) {
    fun getAllMovies() = retrofitService.getAllMovies()
}