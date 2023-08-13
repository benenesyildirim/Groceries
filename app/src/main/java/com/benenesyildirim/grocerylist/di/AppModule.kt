package com.benenesyildirim.grocerylist.di

import android.app.Application
import androidx.room.Room
import com.benenesyildirim.grocerylist.common.Constants.BASE_URL
import com.benenesyildirim.grocerylist.common.Constants.DB_NAME
import com.benenesyildirim.grocerylist.data.ProductApi
import com.benenesyildirim.grocerylist.data.local.ProductsDB
import com.benenesyildirim.grocerylist.data.remote.repository.ProductRepositoryImpl
import com.benenesyildirim.grocerylist.domain.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApi(): ProductApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductApi::class.java)
    }

    @Provides
    @Singleton
    fun provideProductDatabase(app: Application): ProductsDB {
        return Room.databaseBuilder(app, ProductsDB::class.java, DB_NAME).build()
    }

    @Provides
    @Singleton
    fun provideProductRepository(api: ProductApi, db: ProductsDB): ProductRepository {
        return ProductRepositoryImpl(api, db.dao)
    }
}