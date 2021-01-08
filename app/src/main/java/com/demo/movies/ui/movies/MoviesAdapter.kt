package com.demo.movies.ui.movies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.demo.movies.data.entities.Movie
import com.demo.movies.databinding.MovieItemBinding

class MoviesAdapter(private val listener: MovieItemClickListener) :
    RecyclerView.Adapter<MovieViewHolder>() {

    private val movies = ArrayList<Movie>()

    interface MovieItemClickListener {
        fun onClickedMovie(movieId: Int)
    }

    fun setMovies(items: ArrayList<Movie>) {
        this.movies.clear()
        this.movies.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding: MovieItemBinding = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) = holder.bind(movies[position])

    override fun getItemCount(): Int = movies.size
}

class MovieViewHolder(private val itemBinding: MovieItemBinding,
                      private val listener: MoviesAdapter.MovieItemClickListener
) : RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener {

    private lateinit var movie: Movie

    init {
        itemBinding.root.setOnClickListener(this)
    }

    fun bind(item: Movie) {
        this.movie = item
        itemBinding.movieTitle.text = item.title
        itemBinding.movieReleaseYear.text = item.releaseYear.toString()
        Glide.with(itemBinding.root)
            .load(item.image)
            .into(itemBinding.movieImage)
    }

    override fun onClick(v: View?) {
        listener.onClickedMovie(movie.id)
    }

}