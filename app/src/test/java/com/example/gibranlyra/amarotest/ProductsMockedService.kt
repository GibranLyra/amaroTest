package com.example.gibranlyra.amarotest

import com.example.amaroservice.AmaroApiModule
import com.example.amaroservice.model.Product
import com.example.amaroservice.product.ProductDataSource
import com.google.gson.reflect.TypeToken
import io.reactivex.Observable
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * Created by gibranlyra on 26/02/18 for amarotest.
 */
object ProductMockedService : ProductDataSource {
    internal val PRODUCTS: ArrayList<Product>

    init {
        val gson = AmaroApiModule.getGsonBuilder()
        val productsRaw = javaClass.classLoader.getResourceAsStream("productsMockedResponse.json")
        val productsResponseJson = BufferedReader(InputStreamReader(productsRaw))
        PRODUCTS = gson.fromJson(productsResponseJson, object : TypeToken<ArrayList<Product>>() {}.type)
    }

    override fun getProducts(): Observable<ArrayList<Product>> {
        return Observable.just(PRODUCTS)
    }
}