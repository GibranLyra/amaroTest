package com.example.gibranlyra.amarotest

import br.com.net.nowonline.presentation.util.schedulers.ImmediateSchedulerProvider
import com.example.gibranlyra.amarotest.ui.home.HomeContract
import com.example.gibranlyra.amarotest.ui.home.HomePresenter
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/**
 * Created by gibranlyra on 27/02/18 for amarotest.
 */
class HomePresenterUnitTest {
    @Mock private lateinit var productApi: ProductMockedService
    @Mock private lateinit var viewContract: HomeContract.View

    private lateinit var schedulerProvider: ImmediateSchedulerProvider
    private lateinit var presenterContract: HomeContract.Presenter

    @Before
    fun setupHomePresenter() {
        MockitoAnnotations.initMocks(this)
        // Make the sure that all schedulers are immediate.
        schedulerProvider = ImmediateSchedulerProvider()

        // Get a reference to the class under test
        presenterContract = HomePresenter(productApi, viewContract, schedulerProvider)

        // The presenterContract won't update the viewContract unless it's active.
        Mockito.`when`(viewContract.isActive()).thenReturn(true)
    }

    @Test
    @Throws(Exception::class)
    fun loadProductsAndLoadIntoView_Success() {
        Mockito.`when`(productApi.getProducts())
                .thenReturn(Observable.just(ProductMockedService.PRODUCTS))
        presenterContract.loadProducts()

        val inOrder = Mockito.inOrder(viewContract)

        inOrder.verify(viewContract).setPresenter(presenterContract)
        inOrder.verify(viewContract).showLoading(true)
        inOrder.verify(viewContract).showError(false)
        inOrder.verify(viewContract).showProducts(ProductMockedService.PRODUCTS)
        inOrder.verify(viewContract).showLoading(false)
        inOrder.verify(viewContract, Mockito.never()).showError(true)
    }

    @Test
    @Throws(Exception::class)
    fun loadProductsAndLoadIntoView_Error() {
        Mockito.`when`(productApi.getProducts())
                .thenReturn(Observable.error(Exception("This is a error test.")))
        presenterContract.loadProducts()

        val inOrder = Mockito.inOrder(viewContract)
        inOrder.verify(viewContract).setPresenter(presenterContract)
        inOrder.verify(viewContract).showLoading(true)
        inOrder.verify(viewContract).showError(false)
        inOrder.verify(viewContract).showLoading(false)
        inOrder.verify(viewContract).showError(true)
        Mockito.verify(viewContract, Mockito.never()).showProducts(ProductMockedService.PRODUCTS)
    }
}