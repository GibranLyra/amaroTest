package com.example.amaroservice.product

import com.example.amaroservice.model.Product
import io.reactivex.Observable

/**
 * Created by gibranlyra on 26/02/18 for amarotest.
 */
interface ProductDataSource {
    fun getProducts(): Observable<MutableList<Product>>
}