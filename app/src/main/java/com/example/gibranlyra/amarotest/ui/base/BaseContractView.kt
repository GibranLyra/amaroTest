package com.example.gibranlyra.amarotest.ui.base

interface BaseContractView<in T> {

    fun isActive(): Boolean

    fun setPresenter(presenter: T)

}
