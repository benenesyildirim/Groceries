package com.benenesyildirim.grocerylist.domain.use_case.remote

import com.benenesyildirim.grocerylist.domain.repository.ProductRepository
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend fun getProducts() = repository.getProducts()
}