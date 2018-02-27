package com.example.gibranlyra.amarotest.ui.home

import com.example.amaroservice.model.Product
import com.example.gibranlyra.amarotest.ui.base.BaseContractPresenter
import com.example.gibranlyra.amarotest.ui.base.BaseContractView

/**
 * Created by gibranlyra on 26/02/18 for amarotest.
 */
public interface HomeContract {
    interface View : BaseContractView<Presenter> {
        fun showLoading(show: Boolean)
        fun showError(show: Boolean)
        fun showProducts(products: MutableList<Product>)
    }

    interface Presenter : BaseContractPresenter {
        fun loadProducts()
    }
}
