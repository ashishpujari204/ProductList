package com.ashish.ashishproductlist.ui.productlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.ashish.ashishproductlist.rest.RepositoryImplementation
import com.ashish.ashishproductlist.rest.Resource
import kotlinx.coroutines.Dispatchers

class ProductListViewModel(
    private val repositoryImplementation: RepositoryImplementation
) : ViewModel() {
    fun getProductList() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val result = repositoryImplementation.getProducts()
            emit(Resource.success(data = result))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}
