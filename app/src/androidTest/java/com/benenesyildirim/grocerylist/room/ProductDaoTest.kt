package com.benenesyildirim.grocerylist.room

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.benenesyildirim.grocerylist.data.local.ProductDao
import com.benenesyildirim.grocerylist.data.local.ProductsDB
import com.benenesyildirim.grocerylist.data.local.entity.ProductEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProductDaoTest {

    private lateinit var productDao: ProductDao
    private lateinit var database: ProductsDB

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, ProductsDB::class.java)
            .allowMainThreadQueries().build()
        productDao = database.dao
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertAndGetProductsDB() = runBlocking {
        val products = listOf(
            ProductEntity("1", "Product 1", 10, "image1.jpg"),
            ProductEntity("2", "Product 2", 20, "image2.jpg"),
            ProductEntity("3", "Product 3", 30, "image3.jpg")
        )

        productDao.insertProducts(products)

        val insertedProducts = productDao.getProducts()
        assert(products.size == insertedProducts.size)
        assert(insertedProducts.containsAll(products))
    }

    @Test
    fun insertAndGetProductDetailIntoDB() = runBlocking {
        val product = ProductEntity("1", "Product 1", 10, "image1.jpg", "description")

        productDao.insertProductDetail(product)

        val insertedProduct = productDao.getProduct("1")
        assert(insertedProduct == product)
    }
}