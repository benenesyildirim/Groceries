package com.benenesyildirim.grocerylist.presentation.listing

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.benenesyildirim.grocerylist.R
import com.benenesyildirim.grocerylist.common.Constants.PRODUCT_ID
import com.benenesyildirim.grocerylist.common.ItemDecorationGridLayout
import com.benenesyildirim.grocerylist.common.Resource
import com.benenesyildirim.grocerylist.data.remote.dto.ProductDto
import com.benenesyildirim.grocerylist.databinding.FragmentGroceryListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GroceryListFragment : Fragment() {
    private lateinit var binding: FragmentGroceryListBinding
    private val viewModel: GroceryListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGroceryListBinding.inflate(layoutInflater)

        observeProducts()

        return binding.root
    }

    private fun observeProducts() {
        with(viewModel) {
            productsLiveData.observe(viewLifecycleOwner) { response ->

                when (response) {
                    is Resource.Loading -> {
                        if (response.data != null)
                            binding.listingLoading.visibility = GONE
                        else
                            binding.listingLoading.visibility = VISIBLE
                    }

                    is Resource.Success -> {
                        setProductList(response)

                        binding.listingLoading.visibility = GONE
                    }

                    is Resource.Error -> {
                        Toast.makeText(
                            context,
                            response.message ?: getString(R.string.went_wrong),
                            Toast.LENGTH_SHORT
                        ).show()

                        if (response.data!!.isNotEmpty())
                            binding.listingLoading.visibility = GONE
                        else {
                            Handler(Looper.getMainLooper()).postDelayed({
                                requireActivity().finish()
                            }, 4500)
                        }
                    }
                }

            }
        }
    }

    private fun setProductList(response: Resource<List<ProductDto>>) {
        binding.productList.addItemDecoration(ItemDecorationGridLayout(20))

        binding.productList.adapter = GroceryListAdapter(response.data!!) {
            val bundle = bundleOf(PRODUCT_ID to it.product_id)
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_groceryListFragment_to_groceryDetailFragment, bundle)
        }
    }
}