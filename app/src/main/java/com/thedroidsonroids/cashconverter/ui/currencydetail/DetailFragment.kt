package com.thedroidsonroids.cashconverter.ui.currencydetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.thedroidsonroids.cashconverter.R
import com.thedroidsonroids.cashconverter.core.extension.gone
import com.thedroidsonroids.cashconverter.core.extension.show
import com.thedroidsonroids.cashconverter.databinding.FragmentDetailBinding
import kotlinx.coroutines.flow.collect

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailVM by viewModels()
    private val args by navArgs<DetailFragmentArgs>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        viewModel.loadData(args.rate)
        initListeners()
        initObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initListeners() {
        binding.btnConvertFromPln.setOnClickListener {
            viewModel.btnConvertFromPlnClicked()
        }
        binding.btnConvertToPln.setOnClickListener {
            viewModel.btnConvertToPlnClicked()
        }
    }

    private fun initObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.conversion.collect { event ->
                when (event) {
                    is DetailVM.CurrencyEvent.Success -> {
                        hideProgressbar()
                    }
                    is DetailVM.CurrencyEvent.Failure -> {
                        hideProgressbar()
                        Toast.makeText(requireContext(), event.errorText, Toast.LENGTH_LONG).show()
                    }
                    is DetailVM.CurrencyEvent.Loading -> {
                        binding.progressBar.show()
                    }
                    is DetailVM.CurrencyEvent.Empty -> {
                        hideProgressbar()
                    }
                }
            }
        }
    }

    private fun hideProgressbar() {
        binding.progressBar.gone()
    }
}