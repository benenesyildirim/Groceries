package com.benenesyildirim.grocerylist.data

import com.benenesyildirim.grocerylist.data.remote.dto.ProductDetailDto
import com.benenesyildirim.grocerylist.data.remote.dto.ProductResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductApi {
    @GET("/developer-application-test/cart/list")
    suspend fun getProducts(): Response<ProductResponseDto>

    @GET("/developer-application-test/cart/{product_id}/detail")
    suspend fun getProductDetail(@Path("product_id") id: String): Response<ProductDetailDto>
}