package com.example.gibranlyra.amarotest.ui.productdetail

import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.amaroservice.model.Product
import com.example.gibranlyra.amarotest.R
import com.example.gibranlyra.amarotest.ui.GlideApp
import com.example.gibranlyra.amarotest.ui.base.showSnackBar
import kotlinx.android.synthetic.main.fragment_product_detail.*


/**
 * Created by gibranlyra on 26/02/18 for amarotest.
 */


internal const val EXTRA_PRODUCT = "EXTRA_PRODUCT"
internal const val EXTRA_PRODUCT_SIZES = "EXTRA_PRODUCT_SIZES"
private const val PRODUCT_RESULT = "productResult"
private const val HAS_LOADED = "hasLoaded"

class ProductDetailFragment : Fragment(), ProductDetailContract.View {
    private lateinit var presenter: ProductDetailContract.Presenter

    private lateinit var product: Product

    private var productId: Product? = null
    private var productSizes: ArrayList<String>? = null

    private var hasLoaded = false

    companion object {
        fun newInstance(product: Product, productSizes: ArrayList<String>?): ProductDetailFragment {
            val fragment = ProductDetailFragment()
            val bundle = Bundle()
            bundle.putParcelable(EXTRA_PRODUCT, product)
            bundle.putStringArrayList(EXTRA_PRODUCT_SIZES, productSizes)
            fragment.arguments = bundle
            return fragment
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        productId = arguments?.getParcelable(EXTRA_PRODUCT)
        productSizes = arguments?.getStringArrayList(EXTRA_PRODUCT_SIZES)
        return inflater.inflate(R.layout.fragment_product_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState != null) {
            hasLoaded = savedInstanceState.getBoolean(HAS_LOADED, false)
            when (hasLoaded) {
                true -> {
                    product = savedInstanceState.getParcelable(PRODUCT_RESULT)
                    showProduct(product)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (!hasLoaded) {
            productId?.let { presenter.loadProduct(it) }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(PRODUCT_RESULT, product)
        outState.putBoolean(HAS_LOADED, hasLoaded)
    }

    override fun isActive(): Boolean {
        return isAdded
    }

    override fun setPresenter(presenter: ProductDetailContract.Presenter) {
        this.presenter = presenter
    }

    override fun showError(show: Boolean) {
        //todo setup error
        when (show) {
            true -> {
                view?.showSnackBar(getString(R.string.generic_error), Snackbar.LENGTH_INDEFINITE,
                        getString(R.string.try_again), { productId?.let { presenter.loadProduct(it) } })
                errorView.visibility = View.VISIBLE
            }
            else -> errorView.visibility = View.GONE
        }
    }

    override fun showProduct(product: Product) {
        hasLoaded = true
        this.product = product
        productPrice.text = product.regularPrice
        when (product.isOnSale) {
            true -> {
                productPromotionPrice.visibility = View.VISIBLE
                context?.let {
                    productPromotionPrice.text = String.format(
                            it.resources.getString(R.string.product_promotion_price_text), product.actualPrice)
                }
                productPrice.paintFlags = productPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                productPrice.setTextColor(Color.RED)
            }
        }
        productSizes?.forEach {
            when (it) {
                "P" -> pSize.visibility = View.VISIBLE
                "M" -> mSize.visibility = View.VISIBLE
                "G" -> gSize.visibility = View.VISIBLE
                "GG" -> ggSize.visibility = View.VISIBLE
            }
        }
        GlideApp.with(this)
                .load(product.image)
                .placeholder(R.drawable.ic_shopping_cart)
                .into(productImage)
    }
}
