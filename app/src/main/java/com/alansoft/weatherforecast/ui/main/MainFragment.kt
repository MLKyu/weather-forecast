package com.alansoft.weatherforecast.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alansoft.weatherforecast.R
import com.alansoft.weatherforecast.databinding.MainFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {
    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var binding: MainFragmentBinding
    private val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var weatherAdapter: WeatherAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)
        with(binding) {
            lifecycleOwner = viewLifecycleOwner
            viewModel = viewModel
            adapter = weatherAdapter
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            swipeRefresh.setOnRefreshListener(this@MainFragment)
        }

        viewModel.loadWeather()

        viewModel.result.observe(viewLifecycleOwner) {
            binding.adapter?.submitList(it.toList())
            binding.adapter?.notifyDataSetChanged()
        }
    }

    override fun onRefresh() {
        viewModel.loadWeather()

        binding.swipeRefresh.isRefreshing = false
    }
}