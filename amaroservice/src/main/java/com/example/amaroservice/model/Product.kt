package com.example.amaroservice.model

import android.os.Parcel
import android.os.Parcelable

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
                   var sizes: ArrayList<Size>? = null) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readByte() != 0.toByte(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(style)
        parcel.writeString(codeColor)
        parcel.writeString(colorSlug)
        parcel.writeString(color)
        parcel.writeByte(if (isOnSale) 1 else 0)
        parcel.writeString(regularPrice)
        parcel.writeString(actualPrice)
        parcel.writeString(discountPercentage)
        parcel.writeString(installments)
        parcel.writeString(image)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(parcel)
        }

        override fun newArray(size: Int): Array<Product?> {
            return arrayOfNulls(size)
        }
    }
}