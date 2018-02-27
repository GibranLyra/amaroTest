package com.example.gibranlyra.amarotest.ui.about

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gibranlyra.amarotest.R
import com.example.gibranlyra.amarotest.ui.GlideApp
import kotlinx.android.synthetic.main.fragment_about.*

/**
 * Created by gibranlyra on 27/02/18 for amarotest.
 */
const val ABOUT_IMAGE_URL = "https://avatars0.githubusercontent.com/u/5739609?s=460&v=4"

class AboutFragment : Fragment() {

    companion object {
        fun newInstance(): AboutFragment = AboutFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        GlideApp.with(this)
                .load(ABOUT_IMAGE_URL)
                .centerCrop()
                .into(aboutImage)
    }
}