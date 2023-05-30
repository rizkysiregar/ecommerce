package com.rizkysiregar.ecommerce.ui.store

import com.rizkysiregar.ecommerce.data.model.QueryProductModel

interface DataPassed {
    fun onDataPassed(data: QueryProductModel)
}