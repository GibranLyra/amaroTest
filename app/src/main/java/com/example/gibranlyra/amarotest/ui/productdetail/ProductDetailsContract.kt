package com.example.gibranlyra.amarotest.ui.productdetail

import com.example.amaroservice.model.Product
import com.example.gibranlyra.amarotest.ui.base.BaseContractPresenter
import com.example.gibranlyra.amarotest.ui.base.BaseContractView

/**
 * Created by gibranlyra on 26/02/18 for amarotest.
 */
public interface ProductDetailContract {
    interface View : BaseContractView<ProductDetailContract.Presenter> {
        fun showLoading(show: Boolean)
        fun showError(show: Boolean)
        fun showProduct(product: Product)
    }

    interface Presenter : BaseContractPresenter
}
