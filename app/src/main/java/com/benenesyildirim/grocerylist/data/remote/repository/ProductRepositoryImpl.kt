package com.benenesyildirim.grocerylist.data.remote.repository

import com.benenesyildirim.grocerylist.data.ProductApi
import com.benenesyildirim.grocerylist.data.local.ProductDao
import com.benenesyildirim.grocerylist.data.local.entity.ProductEntity
import com.benenesyildirim.grocerylist.data.remote.dto.ProductResponseDto
import com.benenesyildirim.grocerylist.domain.repository.ProductRepository
import retrofit2.Response
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val api: ProductApi,
    private val dao: ProductDao
) : ProductRepository {
    //Remote
    override suspend fun getProducts(): Response<ProductResponseDto> = api.getProducts()

    override suspend fun getProductDetail(id: String) = api.getProductDetail(id)

    //Local
    override suspend fun insertProducts(products: List<ProductEntity>) = dao.insertProducts(products)

    override suspend fun insertProductDetail(product: ProductEntity) = dao.insertProductDetail(product)

    override suspend fun getProductDB(id: String) = dao.getProduct(id)

    override suspend fun getProductsDB() = dao.getProducts()
}