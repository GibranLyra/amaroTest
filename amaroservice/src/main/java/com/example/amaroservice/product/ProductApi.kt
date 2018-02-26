package com.example.amaroservice.product

import com.example.amaroservice.AmaroApiModule
import com.example.amaroservice.model.Product
import io.reactivex.Observable
import timber.log.Timber

/**
 * Created by gibranlyra on 26/02/18 for amarotest.
 */
object ProductApi : ProductDataSource {

    private val productService: ProductService

    init {
        val retrofit = AmaroApiModule.retrofit
        productService = retrofit.create(ProductService::class.java)
    }

    override fun getProducts(): Observable<MutableList<Product>> {
        return productService.getProducts()
                .map {
                    return@map it.toMutableList()
                }
                .doOnError { e -> Timber.e(e, "getProducts: %s", e.message) }
    }

}