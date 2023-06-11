package com.example.movieapp.feature.home.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.movieapp.databinding.FragmentHomeBinding
import com.example.movieapp.feature.home.view.adapter.HomeCarouselAdapter
import com.example.movieapp.feature.home.viewmodel.HomeViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        observeMoviesData()

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun observeMoviesData() {
        homeViewModel.movies.observe(viewLifecycleOwner){
            //Manipulate the list to make the required list based on genres
            val adapter: HomeCarouselAdapter = HomeCarouselAdapter(it)
            _binding!!.ParentRecyclerView.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}