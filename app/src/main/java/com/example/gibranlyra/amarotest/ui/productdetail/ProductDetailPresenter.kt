package com.example.gibranlyra.amarotest.ui.productdetail

import com.example.amaroservice.model.Product

/**
 * Created by gibranlyra on 26/02/18 for amarotest.
 */
class ProductDetailPresenter(val view: ProductDetailContract.View) : ProductDetailContract.Presenter {
    init {
        view.setPresenter(this)
    }

    override fun subscribe() {
        //UNUSED
    }

    override fun unsubscribe() {
        //UNUSED
    }

    override fun loadProduct(product: Product) {
        view.showProduct(product)
    }
}
