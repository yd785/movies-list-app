package com.demo.movies.ui.moviedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.demo.movies.data.entities.Movie
import com.demo.movies.databinding.MovieDetailFragmentBinding
import com.demo.movies.utils.Resource
import com.demo.movies.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private var binding: MovieDetailFragmentBinding by autoCleared()
    private val viewModel: MovieDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MovieDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getInt("id")?.let { viewModel.start(it) }
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.movie.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    bindMovie(it.data!!)
                    binding.progressBar.visibility = View.GONE
                    //binding.visibility = View.VISIBLE
                }

                Resource.Status.ERROR ->
                    Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()

                Resource.Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    //binding.characterCl.visibility = View.GONE
                }
            }
        })
    }

    private fun bindMovie(movie: Movie) {
        binding.movieTitle.text = movie.title
        binding.movieRating.text = movie.rating
        binding.movieReleaseYear.text = movie.releaseYear.toString()
        Glide.with(binding.root)
            .load(movie.image)
            .into(binding.movieImage)
        binding.movieGenre.text = movie.genre.toString()
    }
}
