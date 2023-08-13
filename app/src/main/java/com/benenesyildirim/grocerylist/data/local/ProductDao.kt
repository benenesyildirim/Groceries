package com.benenesyildirim.grocerylist.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.benenesyildirim.grocerylist.data.local.entity.ProductEntity

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<ProductEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProductDetail(product: ProductEntity)

    @Query("SELECT * FROM productEntity WHERE product_id == :id")
    suspend fun getProduct(id: String): ProductEntity

    @Query("SELECT * FROM productEntity ORDER BY product_id ASC")
    suspend fun getProducts(): List<ProductEntity>
}