package com.benenesyildirim.grocerylist.retrofit

import com.benenesyildirim.grocerylist.data.remote.dto.ProductDetailDto
import com.benenesyildirim.grocerylist.data.remote.dto.ProductDto
import com.benenesyildirim.grocerylist.data.remote.dto.ProductResponseDto
import com.benenesyildirim.grocerylist.domain.repository.ProductRepository
import com.benenesyildirim.grocerylist.domain.use_case.remote.GetProductUseCase
import com.benenesyildirim.grocerylist.domain.use_case.remote.GetProductsUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever
import retrofit2.Response

class RemoteProductsUseCase {
    @Mock
    private lateinit var productRepository: ProductRepository

    @InjectMocks
    private lateinit var getProductsUseCase: GetProductsUseCase

    @InjectMocks
    private lateinit var getProductUseCase: GetProductUseCase


    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun getProductFromRemote() = runTest {
        val productEntity1 = ProductDto("1", "Product 1", 10, "image1.jpg")
        val productEntity2 = ProductDto("2", "Product 2", 20, "image2.jpg")
        val productEntity3 = ProductDto("3", "Product 3", 30, "image3.jpg")

        val productList = listOf(productEntity1, productEntity2, productEntity3)

        whenever(productRepository.getProducts()).thenReturn(
            Response.success(
                ProductResponseDto(
                    productList
                )
            )
        )

        val result = getProductsUseCase.getProducts().body()?.products

        assert(productList == result)
    }

    @Test
    fun getProductDetail() = runTest {
        val banana = ProductDetailDto("1", "Banana", 10, "image1.jpg", "Description 1")

        whenever(productRepository.getProductDetail("1")).thenReturn(Response.success(banana))

        val result = getProductUseCase.getProduct("1").body()

        assert(banana == result)
    }
}