package com.example.gibranlyra.amarotest.ui.productdetail

import android.app.Activity

import android.content.Context
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.amaroservice.model.Product
import com.example.gibranlyra.amarotest.R
import com.example.gibranlyra.amarotest.ui.replaceFragmentInActivity
import com.example.gibranlyra.amarotest.ui.setupActionBar
import org.jetbrains.anko.intentFor
/**
 * Created by gibranlyra on 26/02/18 for amarotest.
 */
class ProductDetailActivity : AppCompatActivity() {

    private var product: Product? = null
    private var productSizes: ArrayList<String>? = null

    companion object {
        fun createIntent(context: Context, product: Product, producSizes: ArrayList<String>, transitionView: View) {
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(context as Activity, transitionView,
                    "product_image_transition")
            context.startActivity(context.intentFor<ProductDetailActivity>()
                    .putExtra(EXTRA_PRODUCT, product)
                    .putExtra(EXTRA_PRODUCT_SIZES, producSizes),
                    options.toBundle())
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)
        setupActionBar(R.id.toolbar) {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
        initData()
        openProductDetailFragment()
    }

    private fun initData() {
        intent.extras.getString(EXTRA_PRODUCT).let {
            product = intent.extras.getParcelable(EXTRA_PRODUCT)
            productSizes = intent.extras.getStringArrayList(EXTRA_PRODUCT_SIZES)
        }
    }


    private fun openProductDetailFragment() {
        product?.let {
            val fragment = supportFragmentManager.findFragmentById(R.id.contentFrame)
                    as ProductDetailFragment? ?: ProductDetailFragment.newInstance(it, productSizes).also {
                replaceFragmentInActivity(it, R.id.contentFrame)
            }
            ProductDetailPresenter(fragment)
        } ?: run {
            throw RuntimeException("Product cannot be null.")
        }
    }
}
