package com.celalkorucu.kotlincountries.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.celalkorucu.kotlincountries.adapter2.CountryAdapter
import com.celalkorucu.kotlincountries.ViewModel.FeedViewModel
import com.celalkorucu.kotlincountries.databinding.FragmentFeedBinding


class FeedFragment : Fragment() {

    private  lateinit var binding : FragmentFeedBinding
    private lateinit var viewModel : FeedViewModel
    private val countryAdapter = CountryAdapter(arrayListOf())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = FragmentFeedBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[FeedViewModel::class.java]
        viewModel.refreshData()

        binding.countryListRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.countryListRecyclerView.adapter = countryAdapter


        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.countryListRecyclerView.visibility = View.GONE
            binding.countryErrorText.visibility = View.GONE
            binding.countryLoadingProgressBar.visibility = View.VISIBLE
            viewModel.refreshFromAPI()
            binding.swipeRefreshLayout.isRefreshing = false
        }

        observeLiveData()


    }

    private fun observeLiveData(){
        viewModel.countries.observe(viewLifecycleOwner, Observer { countries ->
            countries?.let {
                binding.countryListRecyclerView.visibility = View.VISIBLE
                countryAdapter.updateCountryList(countries)
            }
        })

        viewModel.countryError.observe(viewLifecycleOwner, Observer { error ->

            error?.let {
                if(it){
                    //Hata varsa
                    binding.countryErrorText.visibility = View.VISIBLE
                    binding.countryListRecyclerView.visibility = View.GONE
                }else{
                    //Hata yok
                    binding.countryErrorText.visibility = View.GONE
                }
            }
        })

        viewModel.countryLoading.observe(viewLifecycleOwner , Observer{loading ->

            loading?.let {
                if(it){
                    binding.countryLoadingProgressBar.visibility = View.VISIBLE
                    binding.countryListRecyclerView.visibility = View.GONE
                    binding.countryErrorText.visibility = View.GONE
                }else{
                    binding.countryLoadingProgressBar.visibility = View.INVISIBLE
                }
            }
        })
    }
}