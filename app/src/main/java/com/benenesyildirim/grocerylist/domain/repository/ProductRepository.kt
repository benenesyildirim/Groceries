package com.benenesyildirim.grocerylist.domain.repository

import com.benenesyildirim.grocerylist.data.local.entity.ProductEntity
import com.benenesyildirim.grocerylist.data.remote.dto.ProductDetailDto
import com.benenesyildirim.grocerylist.data.remote.dto.ProductResponseDto
import retrofit2.Response

interface ProductRepository {
    //Remote
    suspend fun getProducts(): Response<ProductResponseDto>

    suspend fun getProductDetail(id: String): Response<ProductDetailDto>

    //Local
    suspend fun insertProducts(products: List<ProductEntity>)

    suspend fun insertProductDetail(product: ProductEntity)

    suspend fun getProductDB(id: String): ProductEntity

    suspend fun getProductsDB(): List<ProductEntity>
}