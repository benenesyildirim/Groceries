package com.benenesyildirim.grocerylist.room

import com.benenesyildirim.grocerylist.data.local.entity.ProductEntity
import com.benenesyildirim.grocerylist.domain.repository.ProductRepository
import com.benenesyildirim.grocerylist.domain.use_case.local.GetDbProductUseCase
import com.benenesyildirim.grocerylist.domain.use_case.local.GetDbProductsUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class DbProductsUseCaseTest {

    @Mock
    private lateinit var productRepository: ProductRepository

    @InjectMocks
    private lateinit var getDbProductsUseCase: GetDbProductsUseCase

    @InjectMocks
    private lateinit var getDbProductUseCase: GetDbProductUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun getProductsWhenDatabaseIsEmpty() = runTest {
        whenever(productRepository.getProductsDB()).thenReturn(emptyList())

        val result = getDbProductsUseCase.getProducts()
        assertEquals(emptyList(), result)
        verify(productRepository, times(1)).getProductsDB()
    }

    @Test
    fun getProductsFromDatabase() = runTest {
        val productEntity1 = ProductEntity("1", "Product 1", 10, "image1.jpg", "Description 1")
        val productEntity2 = ProductEntity("2", "Product 2", 20, "image2.jpg", "Description 2")
        val productEntity3 = ProductEntity("3", "Product 3", 30, "image3.jpg", "Description 3")

        val productList = listOf(productEntity1, productEntity2, productEntity3)

        whenever(productRepository.getProductsDB()).thenReturn(productList)

        val result = getDbProductsUseCase.getProducts()

        assertEquals(productList, result)
    }

    @Test
    fun getProductDetail() = runTest {
        val banana = ProductEntity("1", "Banana", 10, "image1.jpg", "Description 1")

        whenever(productRepository.getProductDB("1")).thenReturn(banana)

        val result = getDbProductUseCase.getProduct("1")

        assertEquals(banana, result)
    }
}