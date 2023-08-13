package com.benenesyildirim.grocerylist.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benenesyildirim.grocerylist.common.Constants.PRODUCT_ID
import com.benenesyildirim.grocerylist.common.Resource
import com.benenesyildirim.grocerylist.data.remote.dto.ProductDetailDto
import com.benenesyildirim.grocerylist.domain.use_case.local.GetDbProductUseCase
import com.benenesyildirim.grocerylist.domain.use_case.local.UpdateProductDbUseCase
import com.benenesyildirim.grocerylist.domain.use_case.remote.GetProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class GroceryDetailViewModel @Inject constructor(
    private val getDbProductUseCase: GetDbProductUseCase,
    private val getProductUseCase: GetProductUseCase,
    private val updateProductDbUseCase: UpdateProductDbUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    init {
        getProduct(savedStateHandle.get<String>(PRODUCT_ID)!!)
    }

    private val _productLiveData = MutableLiveData<Resource<ProductDetailDto>>()
    val productLiveData: LiveData<Resource<ProductDetailDto>> get() = _productLiveData

    private fun getProduct(id: String) = viewModelScope.launch {
        getDbProductUseCase.getProduct(id).toProductDetail().let { dbProduct ->
            _productLiveData?.postValue(Resource.Loading(null))

            try {
                getProductUseCase.getProduct(id).let {
                    updateProductDbUseCase.updateProduct(it.body()!!.toProductEntity())
                }
            } catch (e: HttpException) {//Invalid Response
                _productLiveData.postValue(
                    Resource.Error(
                        e.localizedMessage ?: "An unexpected error occurred",
                        dbProduct
                    )
                )
            } catch (e: IOException) {//Server,connection problems
                _productLiveData.postValue(
                    Resource.Error(
                        "Couldn't reach server. Check your internet connection.",
                        dbProduct
                    )
                )
            }
        }

        getDbProductUseCase.getProduct(id).toProductDetail().let { dbProduct ->
            _productLiveData.postValue(Resource.Success(dbProduct))
        }
    }
}