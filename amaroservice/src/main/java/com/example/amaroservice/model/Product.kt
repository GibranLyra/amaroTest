package com.example.amaroservice.model

data class Product(var name: String? = null,
                   var style: String? = null,
                   var codeColor: String? = null,
                   var colorSlug: String? = null,
                   var color: String? = null,
                   var isOnSale: Boolean = false,
                   var regularPrice: String? = null,
                   var actualPrice: String? = null,
                   var discountPercentage: String? = null,
                   var installments: String? = null,
                   var image: String? = null,
                   var sizes: MutableList<Size>? = null)
