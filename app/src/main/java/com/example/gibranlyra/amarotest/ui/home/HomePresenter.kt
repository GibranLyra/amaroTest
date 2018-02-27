package com.example.gibranlyra.amarotest.ui.home

import br.com.net.nowonline.presentation.util.schedulers.BaseSchedulerProvider
import com.example.amaroservice.product.ProductDataSource
import gibran.com.br.gistconsumer.util.ObserverHelper
import gibran.com.br.gistconsumer.util.tests.EspressoIdlingResource
import io.reactivex.disposables.Disposable

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
        productsRequest = productsDataSource.getProducts()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnTerminate({
                    view.showLoading(false)
                    if (!EspressoIdlingResource.idlingResource.isIdleNow) {
                        EspressoIdlingResource.decrement() // Set app as idle.
                    }
                })
                .subscribe({ view.showProducts(it) },
                        {
                            view.showError(true)
                        })
    }
}