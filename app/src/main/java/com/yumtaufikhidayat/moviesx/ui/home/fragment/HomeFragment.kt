package com.yumtaufikhidayat.moviesx.ui.home.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.yumtaufikhidayat.moviesx.R
import com.yumtaufikhidayat.moviesx.data.NetworkResult
import com.yumtaufikhidayat.moviesx.databinding.FragmentHomeBinding
import com.yumtaufikhidayat.moviesx.model.genres.Genre
import com.yumtaufikhidayat.moviesx.ui.details.fragment.DetailMovieFragment
import com.yumtaufikhidayat.moviesx.ui.home.adapter.LoadMoreAdapter
import com.yumtaufikhidayat.moviesx.ui.home.adapter.MovieAdapter
import com.yumtaufikhidayat.moviesx.ui.home.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel by viewModels<HomeViewModel>()
    private var movieAdapter: MovieAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setMovieListAdapter()
        setObserver()
    }

    private fun setMovieListAdapter() {
        binding.rvHome.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = movieAdapter
        }
    }

    private fun setObserver() {
        homeViewModel.getGenres().observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Loading -> {}
                is NetworkResult.Success -> setMovieListData(it.data.genres)
                is NetworkResult.Error -> Log.e(HOME_TAG, "Error: ${it.error}")
                is NetworkResult.ServerError -> Log.e(HOME_TAG, "Error: ${it.error}")
                is NetworkResult.Unauthorized -> Log.e(HOME_TAG, "Error: ${it.error}")
            }
        }
    }

    private fun setMovieListData(genreList: List<Genre>) {
        binding.apply {
            movieAdapter = MovieAdapter(genreList) {
                val bundle = Bundle().apply {
                    putInt(DetailMovieFragment.EXTRA_ID, it.id)
                    putString(DetailMovieFragment.EXTRA_TITLE, it.title)
                }
                findNavController().navigate(R.id.detailMovieFragment, bundle)
            }

            lifecycleScope.launch {
                homeViewModel.getMovieNowPlaying().collect {
                    movieAdapter?.submitData(viewLifecycleOwner.lifecycle, it)
                }
            }

            movieAdapter?.apply {
                layoutError.apply {
                    addLoadStateListener { loadState ->
                        val loadStateRefresh = loadState.source.refresh
                        shimmerLoading.isVisible = loadStateRefresh is LoadState.Loading
                        rvHome.isVisible = loadStateRefresh is LoadState.NotLoading
                        tvErrorTitle.apply {
                            isVisible = loadStateRefresh is LoadState.Error
                            text = getString(R.string.tvOops)
                            setTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.colorOrange
                                )
                            )
                        }
                        tvError.apply {
                            isVisible = loadStateRefresh is LoadState.Error
                            text = getString(R.string.tvUnableLoadData)
                            setTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.colorOrange
                                )
                            )
                        }
                        btnRetry.isVisible = loadStateRefresh is LoadState.Error

                        if (loadStateRefresh is LoadState.NotLoading
                            && loadState.append.endOfPaginationReached
                            && itemCount < 1
                        ) {
                            rvHome.isVisible = false
                            tvEmptyTitle.isVisible = true
                            tvEmpty.isVisible = true
                        } else {
                            tvEmptyTitle.isVisible = false
                            tvEmpty.isVisible = false
                        }
                    }

                    btnRetry.setOnClickListener {
                        movieAdapter?.retry()
                    }
                }
            }

            rvHome.adapter = movieAdapter?.withLoadStateHeaderAndFooter(
                header = LoadMoreAdapter { movieAdapter?.retry() },
                footer = LoadMoreAdapter { movieAdapter?.retry() }
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val HOME_TAG = "home_tag"
    }
}