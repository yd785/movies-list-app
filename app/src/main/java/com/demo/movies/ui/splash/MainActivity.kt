package com.demo.movies.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer

import com.demo.movies.R
import com.demo.movies.databinding.ActivityMainBinding
import com.demo.movies.ui.home.HomeActivity
import com.demo.movies.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //val binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()
        setupObservers()

    }

    private fun setupObservers() {
        viewModel.loadingStatus.observe(this, Observer {
            when (it) {
                Resource.Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                }

                Resource.Status.ERROR ->
                    Toast.makeText(this, R.string.loading_fail_msg, Toast.LENGTH_SHORT).show()

                Resource.Status.LOADING ->
                    progressBar.visibility = View.VISIBLE
            }
        })
    }
}