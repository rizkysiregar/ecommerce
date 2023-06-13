package com.rizkysiregar.ecommerce.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class QueryProductModel(
    var search: String? = null,
    var brand: String? = null,
    var lowest: Int? = null,
    var highest: Int? = null,
    var sort: String? = null,
):Parcelable
