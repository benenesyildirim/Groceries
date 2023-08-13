package com.benenesyildirim.grocerylist.domain.use_case.remote

import com.benenesyildirim.grocerylist.domain.repository.ProductRepository
import javax.inject.Inject

class GetProductUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend fun getProduct(id: String) = repository.getProductDetail(id)
}