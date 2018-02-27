package com.example.gibranlyra.amarotest.ui.home

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.amaroservice.model.Product
import com.example.gibranlyra.amarotest.R
import com.example.gibranlyra.amarotest.ui.base.showSnackBar
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * Created by gibranlyra on 26/02/18 for amarotest.
 */
private const val PRODUCTS_RESULT = "productsResult"
private const val HAS_LOADED = "hasLoaded"

class HomeFragment : Fragment(), HomeContract.View {
    private lateinit var presenter: HomeContract.Presenter

    private var hasLoaded = false
    private var products: MutableList<Product>? = null

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
                    //todo fix parceable
//                    products = savedInstanceState.getParcelableArrayList(PRODUCTS_RESULT)
//                    products?.let { showGists(it) }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (!hasLoaded) {
            presenter.loadProducts()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        //todo fix parceable
//        outState.putParcelableArrayList(PRODUCTS_RESULT, products)
        outState.putBoolean(HAS_LOADED, hasLoaded)
    }

    override fun isActive(): Boolean {
        return isAdded
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

    override fun showProducts(products: MutableList<Product>) {
        productsRecycler.adapter?.let {
            (it as ProductAdapter).add(products as ArrayList<Product>)
        } ?: run {
            hasLoaded = true
            setupRecycler(products)
        }
    }

    private fun setupRecycler(products: MutableList<Product>) {
        this.products = products
        val linearLayoutManager = LinearLayoutManager(context)
        productsRecycler.layoutManager = linearLayoutManager
        productsRecycler.adapter = ProductAdapter(products) { product, view ->
            //todo implement details fragment
//            context?.let { context -> GistDetailActivity.createIntent(context, gist.id, view) }
        }
        productsRecycler.setHasFixedSize(true)
    }
}