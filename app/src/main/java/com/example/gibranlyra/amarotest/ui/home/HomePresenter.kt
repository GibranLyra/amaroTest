package com.example.gibranlyra.amarotest.ui.home

import br.com.net.nowonline.presentation.util.schedulers.BaseSchedulerProvider
import com.example.amaroservice.model.Product
import com.example.amaroservice.product.ProductDataSource
import com.example.gibranlyra.amarotest.util.ObserverHelper
import com.example.gibranlyra.amarotest.util.tests.EspressoIdlingResource
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import timber.log.Timber

/**
 * Created by gibranlyra on 26/02/18 for amarotest.
 */
class HomePresenter(private val productsDataSource: ProductDataSource,
                    private val view: HomeContract.View,
                    private val schedulerProvider: BaseSchedulerProvider) : HomeContract.Presenter {
    private var productsRequest: Disposable? = null

    init {
        view.setPresenter(this)
    }

    override fun subscribe() {
        //UNUSED
    }

    override fun unsubscribe() {
        //Dispose request to avoid memory leaks
        productsRequest?.let { ObserverHelper.safelyDispose(it) }
    }

    override fun loadProducts() {
        view.showLoading(true)
        // The network request might be handled in a different thread so make sure Espresso knows
        // that the app is busy until the response is handled.
        EspressoIdlingResource.increment() // App is busy until further notice
        view.showError(false)
        productsRequest = getProductsRequest()
                .subscribe({ view.showProducts(it) }, {
                    view.showError(true)
                })
    }

    override fun onlyDeals() {
        view.showLoading(true)
        getProductsRequest()
                .subscribe({
                    val filteredList = it.filter { product -> product.isOnSale }
                    view.showProducts(filteredList as ArrayList<Product>)
                }, {
                    Timber.e(it, "onlyDeals: %s", it.message)
                    view.showError(true)
                })
    }

    override fun orderByPrice() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun getProductsRequest(): Observable<ArrayList<Product>> {
        return productsDataSource.getProducts()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnTerminate({
                    view.showLoading(false)
                    if (!EspressoIdlingResource.idlingResource.isIdleNow) {
                        EspressoIdlingResource.decrement() // Set app as idle.
                    }
                })
    }
}
