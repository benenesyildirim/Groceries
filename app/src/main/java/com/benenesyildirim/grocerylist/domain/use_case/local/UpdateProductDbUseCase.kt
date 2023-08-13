package com.benenesyildirim.grocerylist.domain.use_case.local

import com.benenesyildirim.grocerylist.data.local.entity.ProductEntity
import com.benenesyildirim.grocerylist.domain.repository.ProductRepository
import javax.inject.Inject

class UpdateProductDbUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend fun updateProduct(product: ProductEntity) = repository.insertProductDetail(product)
}