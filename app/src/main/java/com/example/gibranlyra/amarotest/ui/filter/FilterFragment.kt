package com.example.gibranlyra.amarotest.ui.filter

import android.app.Dialog
import android.view.View
import android.widget.RelativeLayout
import com.allattentionhere.fabulousfilter.AAH_FabulousFragment
import com.example.gibranlyra.amarotest.R

/**
 * Created by gibranlyra on 27/02/18 for amarotest.
 */
class FilterFragment : AAH_FabulousFragment() {

    companion object {

        fun newInstance(): FilterFragment {
            return FilterFragment()
        }
    }

    override fun setupDialog(dialog: Dialog, style: Int) {
        val contentView = View.inflate(context, R.layout.filter_view, null)
        contentView.findViewById<View>(R.id.btn_close).setOnClickListener { closeFilter("closed") }
        contentView.findViewById<View>(R.id.btn_price).setOnClickListener { closeFilter("price") }
        contentView.findViewById<View>(R.id.btn_deals).setOnClickListener { closeFilter("deals") }
        contentView.findViewById<View>(R.id.btn_no_filter).setOnClickListener { closeFilter("noFilter") }
        val filterContent: RelativeLayout = contentView.findViewById(R.id.filterContent)
        setViewMain(filterContent)
        setMainContentView(contentView)
        super.setupDialog(dialog, style) //call super at last
    }

}
