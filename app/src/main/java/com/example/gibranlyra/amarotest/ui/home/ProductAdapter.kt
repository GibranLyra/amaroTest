package com.example.gibranlyra.amarotest.ui.home

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.example.amaroservice.model.Product
import com.example.gibranlyra.amarotest.R
import com.example.gibranlyra.amarotest.ui.GlideApp
import com.example.gibranlyra.amarotest.ui.base.inflate
import kotlinx.android.synthetic.main.product_item.view.*

/**
 * Created by gibranlyra on 26/02/18 for amarotest.
 */
class ProductAdapter(private val items: MutableList<Product>,
                     private val listener: (Product, View) -> Unit) : RecyclerView.Adapter<ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ProductViewHolder(parent.inflate(R.layout.product_item))

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) = holder.bind(items[position], listener)

    override fun getItemCount() = items.size

    internal fun add(products: ArrayList<Product>) {
        val adapterSize = items.size
        items.addAll(products)
        notifyItemRangeInserted(adapterSize, products.size)
    }

    internal fun clear() {
        items.clear()
        notifyDataSetChanged()
    }
}

class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(item: Product, listener: (Product, View) -> Unit) = with(itemView) {
        productName.text = item.name
        productPrice.text = String.format(context.resources.getString(R.string.product_price_text), item.actualPrice)
        GlideApp.with(this)
                .load(item.image)
                .centerCrop()
                .placeholder(R.drawable.ic_shopping_cart)
                .into(productImage)
        setOnClickListener { listener(item, productImage) }
    }
}