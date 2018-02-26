package com.example.amaroservice.product

import com.example.amaroservice.model.Product
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * Created by gibranlyra on 26/02/18 for amarotest.
 */
internal interface ProductService {
    /*Code from mocky.io*/
    @GET("59b6a65a0f0000e90471257d")
    fun getProducts(): Observable<List<Product>>
}