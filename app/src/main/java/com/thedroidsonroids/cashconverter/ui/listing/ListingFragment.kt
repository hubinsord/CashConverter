package com.thedroidsonroids.cashconverter.ui.listing

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.thedroidsonroids.cashconverter.R
import com.thedroidsonroids.cashconverter.databinding.FragmentListingBinding
import com.thedroidsonroids.cashconverter.ui.listing.ListingFragmentVM.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class ListingFragment : Fragment(R.layout.fragment_listing) {

    private val viewModel: ListingFragmentVM by viewModels()
    private var _binding: FragmentListingBinding? = null
    private val binding get() = _binding!!
    private val listingAdapter = ListingAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentListingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        viewModel.getTableC()
        initViews()
        initObservers()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initViews() {
        binding.rcvCurrencies.apply {
            adapter = listingAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun initObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.table.collect {
                when (it) {
                    is TableEvent.Success -> {
                        binding.progressBar.visibility = View.GONE
                        listingAdapter.submitList(it.data.rates)
                        Toast.makeText(requireContext(), it.data.rates[0].currency, Toast.LENGTH_LONG).show()
                        Log.d("TAG", "Success: ${it.data.rates[0].currency} ")
                    }
                    is TableEvent.Loading -> {
                        Toast.makeText(requireContext(), "LOADING", Toast.LENGTH_LONG).show()
                        Log.d("TAG", "LOADING ")
                    }
                    is TableEvent.Failure -> {
                        Toast.makeText(requireContext(), it.errorText, Toast.LENGTH_LONG).show()
                        Log.d("TAG", "ERROR: ${it.errorText} ")
                    }
                    is TableEvent.Empty -> {
                        Toast.makeText(requireContext(), "EMPTY", Toast.LENGTH_LONG).show()
                        Log.d("TAG", "EMPTY")
                    }
                }
            }
        }
    }
}