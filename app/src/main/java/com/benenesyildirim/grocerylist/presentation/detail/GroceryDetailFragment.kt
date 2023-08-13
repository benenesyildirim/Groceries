package com.benenesyildirim.grocerylist.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.benenesyildirim.grocerylist.R
import com.benenesyildirim.grocerylist.common.Resource
import com.benenesyildirim.grocerylist.databinding.FragmentGroceryDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GroceryDetailFragment : Fragment() {
    private val viewModel: GroceryDetailViewModel by viewModels()
    private lateinit var binding: FragmentGroceryDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGroceryDetailBinding.inflate(layoutInflater)

        observeProduct()

        return binding.root
    }

    private fun observeProduct() {
        with(viewModel) {
            productLiveData.observe(viewLifecycleOwner) { response ->
                if (response.data != null)
                    binding.product = response.data

                when (response) {
                    is Resource.Loading -> {
                        if (response.data != null)
                            binding.detailLoading.visibility = GONE
                        else
                            binding.detailLoading.visibility = VISIBLE
                    }

                    is Resource.Success -> {
                        binding.detailLoading.visibility = GONE
                    }

                    is Resource.Error -> {
                        Toast.makeText(
                            context,
                            response.message ?: getString(R.string.went_wrong),
                            Toast.LENGTH_SHORT
                        ).show()
                        binding.detailLoading.visibility = GONE
                    }
                }

            }
        }
    }
}