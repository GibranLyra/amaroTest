package com.example.gibranlyra.amarotest.ui.home

import android.os.Bundle
import android.support.annotation.IdRes
import android.support.annotation.VisibleForTesting
import android.support.test.espresso.IdlingResource
import android.support.v7.app.AppCompatActivity
import br.com.net.nowonline.presentation.util.schedulers.SchedulerProvider
import com.example.amaroservice.product.ProductApi
import com.example.gibranlyra.amarotest.R
import com.example.gibranlyra.amarotest.ui.about.AboutFragment
import com.example.gibranlyra.amarotest.ui.replaceFragmentInActivity
import com.example.gibranlyra.amarotest.ui.setupActionBar
import com.example.gibranlyra.amarotest.util.tests.EspressoIdlingResource
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setupActionBar(R.id.toolbar) {
            setDisplayHomeAsUpEnabled(false)
        }
        bottomNavigation.setOnNavigationItemSelectedListener {
            changeFragment(it.itemId)
            return@setOnNavigationItemSelectedListener true
        }
        openHomeFragment()
    }

    private fun openHomeFragment() {
        val homeId = "home"
        var fragment: HomeFragment? = supportFragmentManager.findFragmentByTag(homeId) as HomeFragment?
        if (fragment == null) {
            // Create the fragment
            fragment = HomeFragment.newInstance()
            replaceFragmentInActivity(fragment, R.id.contentFrame, homeId)
        }
        HomePresenter(ProductApi, fragment, SchedulerProvider)
    }

    private fun openAboutFragment() {
        replaceFragmentInActivity(AboutFragment.newInstance(), R.id.contentFrame)
    }


    private fun changeFragment(@IdRes itemId: Int) {
        when (itemId) {
            R.id.action_home -> openHomeFragment()
            R.id.action_about -> openAboutFragment()
        }
    }

    @VisibleForTesting
    fun getCountingIdlingResource(): IdlingResource {
        return EspressoIdlingResource.idlingResource
    }
}
