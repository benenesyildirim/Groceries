package com.benenesyildirim.grocerylist.presentation.listing

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.benenesyildirim.grocerylist.data.remote.dto.ProductDto
import com.benenesyildirim.grocerylist.databinding.GroceryItemDesignBinding

class GroceryListAdapter(
    private val products: List<ProductDto>, val listener: (ProductDto) -> Unit
) : RecyclerView.Adapter<GroceryListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = GroceryItemDesignBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(products[position])

    override fun getItemCount(): Int = products.size

    inner class ViewHolder(private val binding: GroceryItemDesignBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: ProductDto) {
            binding.apply {
                this.product = product
                executePendingBindings()
                binding.root.setOnClickListener { listener(product) }
            }
        }
    }
}