package com.example.moviedemo.activities

import MainAdapter
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.moviedemo.R
import com.example.moviedemo.databinding.ActivityMainBinding
import com.example.moviedemo.models.Search
import com.example.moviedemo.repository.MainRepository
import com.example.moviedemo.retrofit.RetrofitService
import com.example.moviedemo.viewmodelfactory.MyViewModelFactory
import com.example.moviedemo.viewmodels.MainViewModel
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainViewModel
    private val retrofitService = RetrofitService.getInstance()
    private val adapter = MainAdapter()
    private var movieModalArrayList: ArrayList<Search>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this, MyViewModelFactory(MainRepository(retrofitService))).get(MainViewModel::class.java)

        buildRecyclerView()
    }

    // calling on create option menu
    // layout to inflate our menu file.
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // below line is to get our inflater
        val inflater = menuInflater

        // inside inflater we are inflating our menu file.
        inflater.inflate(R.menu.search_menu, menu)

        // below line is to get our menu item.
        val searchItem = menu.findItem(R.id.actionSearch)

        // getting search view of our item.
        val searchView = searchItem.actionView as SearchView

        // below line is to call set on query text listener method.
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                // inside on query text change method we are
                // calling a method to filter our recycler view.
                filter(newText)
                return false
            }
        })
        return true
    }

    private fun filter(text: String) {
        // creating a new array list to filter our data.
        val filteredList: ArrayList<Search> = ArrayList()
        // running a for loop to compare elements.
        for (item in movieModalArrayList!!) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.title.lowercase(Locale.getDefault())
                    .contains(text.lowercase(Locale.getDefault()))
            ) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredList.add(item)
            }
        }
        if (filteredList.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show()
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            adapter.filterList(filteredList)
        }
    }

    private fun buildRecyclerView() {

        binding.recyclerview.adapter = adapter
        viewModel.movieList.observe(this, Observer {
            Log.d(TAG, "onCreate: $it")
            movieModalArrayList = it.search as ArrayList<Search>
            if (movieModalArrayList != null) {
                adapter.setMovieList(movieModalArrayList!!)
            }
        })
        viewModel.errorMessage.observe(this, Observer {
        })
        viewModel.getAllMovies()
    }
}
