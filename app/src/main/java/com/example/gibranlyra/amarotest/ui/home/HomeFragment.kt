package com.example.gibranlyra.amarotest.ui.home

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.allattentionhere.fabulousfilter.AAH_FabulousFragment
import com.example.amaroservice.model.Product
import com.example.gibranlyra.amarotest.R
import com.example.gibranlyra.amarotest.ui.base.showSnackBar
import com.example.gibranlyra.amarotest.ui.filter.FilterFragment
import com.example.gibranlyra.amarotest.ui.productdetail.ProductDetailActivity
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * Created by gibranlyra on 26/02/18 for amarotest.
 */
private const val PRODUCTS_RESULT = "productsResult"
private const val HAS_LOADED = "hasLoaded"

class HomeFragment : Fragment(), HomeContract.View, AAH_FabulousFragment.Callbacks {
    private lateinit var presenter: HomeContract.Presenter

    private var hasLoaded = false

    private var products: ArrayList<Product>? = null
    companion object {

        fun newInstance(): HomeFragment = HomeFragment()
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState != null) {
            hasLoaded = savedInstanceState.getBoolean(HAS_LOADED, false)
            when (hasLoaded) {
                true -> {
                    products = savedInstanceState.getParcelableArrayList(PRODUCTS_RESULT)
                    products?.let { showProducts(it) }
                }
            }
        }
        swipeRefreshLayout.setOnRefreshListener {
            productsRecycler.adapter?.let {
                (it as ProductAdapter).clear()
            }
            presenter.loadProducts()
        }
        setupFilterButton()
    }

    private fun setupFilterButton() {
        val filterFragment = FilterFragment.newInstance()
        filterFragment.setParentFab(filterButton)
        filterButton.setOnClickListener({
            filterFragment.setCallbacks(this@HomeFragment)
            filterFragment.show(activity?.supportFragmentManager, filterFragment.getTag())
        })
    }

    override fun onResume() {
        super.onResume()
        if (!hasLoaded) {
            presenter.loadProducts()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(PRODUCTS_RESULT, products)
        outState.putBoolean(HAS_LOADED, hasLoaded)
    }

    override fun isActive(): Boolean {
        return isAdded
    }

    override fun onResult(result: Any?) {
        when(result as String) {
            "price" -> presenter.orderByPrice()
            "deals" -> presenter.onlyDeals()
            "noFilter" -> presenter.loadProducts()
        }
    }

    override fun setPresenter(presenter: HomeContract.Presenter) {
        this.presenter = presenter
    }

    override fun showLoading(show: Boolean) {
        when (show) {
            true -> {
                loadingProgressBar?.show()
                swipeRefreshLayout.isRefreshing = true
            }
            else -> {
                loadingProgressBar?.hide()
                swipeRefreshLayout?.isRefreshing = false
            }
        }
    }

    override fun showError(show: Boolean) {
        when (show) {
            true -> view?.showSnackBar(getString(R.string.generic_error), Snackbar.LENGTH_LONG,
                    getString(R.string.try_again), { presenter.loadProducts() })
        }
    }

    override fun showProducts(products: ArrayList<Product>) {
        productsRecycler.adapter?.let {
            (it as ProductAdapter).clear()
            it.add(products)
        } ?: run {
            hasLoaded = true
            setupRecycler(products)
        }
    }

    private fun setupRecycler(products: ArrayList<Product>) {
        this.products = products
        val linearLayoutManager = LinearLayoutManager(context)
        productsRecycler.layoutManager = linearLayoutManager
        productsRecycler.adapter = ProductAdapter(products) { product, view ->
            val productSizes = ArrayList<String>()
            product.sizes?.forEach {
                when(it.isAvailable) {
                    true -> it.size?.let { size -> productSizes.add(size) }
                }
            }
            context?.let { context -> ProductDetailActivity.createIntent(context, product, productSizes, view) }

        }
        productsRecycler.setHasFixedSize(true)
    }
}