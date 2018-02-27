package com.example.gibranlyra.amarotest.ui.productdetail

/**
 * Created by gibranlyra on 26/02/18 for amarotest.
 */
class ProductDetailPresenter(view: ProductDetailContract.View) : ProductDetailContract.Presenter {

    init {
        view.setPresenter(this)
    }

    override fun subscribe() {
        //UNUSED
    }

    override fun unsubscribe() {
        //UNUSED
    }
}
