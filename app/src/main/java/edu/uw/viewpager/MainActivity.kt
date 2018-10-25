package edu.uw.viewpager

import android.os.Bundle
import android.support.v4.app.*
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.EditText
import android.widget.Button



class MainActivity : AppCompatActivity(), MovieListFragment.OnMovieSelectedListener, SearchFragment.OnSearchListener {

    var searchFragment: SearchFragment? = null
    var listFragment: MovieListFragment? = null
    var detailsFragment: DetailFragment? = null
    private lateinit var movieAdapter: PagerAdapter
    private lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchFragment = SearchFragment.newInstance()

        viewPager = findViewById(R.id.movies)

        movieAdapter = MoviePagerAdapter(supportFragmentManager)

        viewPager.adapter = movieAdapter

    }


    override fun onMovieSelected(movie: Movie) {
        val fragment = DetailFragment.newInstance(movie)
        movieAdapter.notifyDataSetChanged()
    }

    override fun onSearchSubmitted(searchTerm: String) {
        listFragment = MovieListFragment.newInstance(searchTerm)
        movieAdapter.notifyDataSetChanged()
        viewPager.currentItem = 1
    }

    companion object {
        private val TAG = "MainActivity"
        val MOVIE_LIST_FRAGMENT_TAG = "MoviesListFragment"
        val MOVIE_DETAIL_FRAGMENT_TAG = "DetailFragment"
    }

    private inner class MoviePagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

        override fun getCount(): Int {
            if (listFragment == null) {
                return 1
            } else if (detailsFragment == null) {
                return 2
            } else {
                return 3
            }
        }

        override fun getItem(position: Int): Fragment? {
            if (position == 0) {
                return searchFragment
            } else if (position == 1) {
                return listFragment
            } else {
                return detailsFragment
            }
        }

        override fun getItemPosition(`object`: Any?): Int {
            return PagerAdapter.POSITION_NONE
        }
    }
}
