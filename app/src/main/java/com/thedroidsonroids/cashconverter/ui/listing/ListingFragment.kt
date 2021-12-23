package com.thedroidsonroids.cashconverter.ui.listing

import android.os.Bundle
import android.provider.Telephony
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.thedroidsonroids.cashconverter.R
import com.thedroidsonroids.cashconverter.core.extension.gone
import com.thedroidsonroids.cashconverter.core.resource.Resource
import com.thedroidsonroids.cashconverter.data.model.Rate
import com.thedroidsonroids.cashconverter.databinding.FragmentListingBinding
import com.thedroidsonroids.cashconverter.ui.listing.ListingFragmentVM.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class ListingFragment : Fragment(R.layout.fragment_listing), ListingAdapter.Companion.ListingAdapterListener {

    private val viewModel: ListingFragmentVM by viewModels()
    private var _binding: FragmentListingBinding? = null
    private val binding get() = _binding!!
    private val listingAdapter = ListingAdapter(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentListingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
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
        initTablesObserver()
    }

    private fun initTablesObserver() {
        viewModel.tables.observe(viewLifecycleOwner) {
            when(it) {
                is Resource.Success -> {
                    hideProgressBar()
                    val rates = it.data?.getOrNull(0)?.rates
                    if (rates.isNullOrEmpty()) Toast.makeText(requireContext(), getString(R.string.no_internet_access), Toast.LENGTH_LONG).show()
                    listingAdapter.submitList(rates)
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_LONG).show()
                    hideProgressBar()
                    Log.d("TAG", "ERROR: ${it.error} ")
                }
                is Resource.Loading -> {
                    Toast.makeText(requireContext(), "LOADING", Toast.LENGTH_LONG).show()
                    Log.d("TAG", "LOADING ")
                }
            }
        }
    }

    private fun hideProgressBar() {
        binding.progressBar.gone()
    }

    override fun onItemClicked(rate: Rate) {
        val action = ListingFragmentDirections.actionListingFragmentToDetailFragment(rate)
        findNavController().navigate(action)
    }
}