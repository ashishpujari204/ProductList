package com.ashish.ashishproductlist.rest

import com.ashish.ashishproductlist.rest.Status.SUCCESS
import com.ashish.ashishproductlist.rest.Status.LOADING
import com.ashish.ashishproductlist.rest.Status.ERROR

data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T): Resource<T> =
            Resource(status = SUCCESS, data = data, message = null)

        fun <T> error(data: T?, message: String): Resource<T> =
            Resource(status = ERROR, data = data, message = message)

        fun <T> loading(data: T?): Resource<T> =
            Resource(status = LOADING, data = data, message = null)
    }
}